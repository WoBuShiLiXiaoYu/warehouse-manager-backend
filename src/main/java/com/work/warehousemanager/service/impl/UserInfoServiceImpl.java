package com.work.warehousemanager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.RedisConstants;
import com.work.warehousemanager.constants.WarehouseConstants;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.dto.user.CurrentUser;
import com.work.warehousemanager.dto.user.LoginUser;
import com.work.warehousemanager.entity.UserInfo;
import com.work.warehousemanager.mapper.RoleMapper;
import com.work.warehousemanager.mapper.UserInfoMapper;
import com.work.warehousemanager.service.UserInfoService;
import com.work.warehousemanager.utils.DigestUtil;
import com.work.warehousemanager.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author 胡国海
* @description 针对表【user_info(用户表)】的数据库操作Service实现
* @createDate 2023-12-01 17:41:32
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private TokenUtils tokenUtils;

    @Value("${config.password-salt}")
    private String passwordSalt;

    @Override
    public Result loginUser(LoginUser loginUser) {

        String userCode = loginUser.getUserCode();
        String userPwd = loginUser.getUserPwd();
        String code = loginUser.getVerificationCode();
        if (StrUtil.isEmpty(userCode) || StrUtil.isEmpty(userPwd) || StrUtil.isEmpty(code)) {
            return Result.err(Result.BAD_REQUEST, "请求参数错误！");
        }
        // 判断验证码是否存在
        Boolean hasKey = stringRedisTemplate.hasKey(RedisConstants.LOGIN_CODE_KEY + code);
        if (!hasKey) {
            return Result.err(Result.BAD_REQUEST, "请求参数错误！验证码不存在！");
        }
        // 获取用户信息
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_Code", userCode);
        UserInfo user = userInfoMapper.selectOne(queryWrapper);
        // 判断用户是否存在
        if (user == null) {
            return Result.err(Result.BAD_REQUEST, "请求参数错误！用户不存在！");
        }
        if(!user.getUserState().equals(WarehouseConstants.USER_STATE_PASS)) {
            return Result.err(Result.BAD_REQUEST, "用户状态未审核！");
        }
        // 判断密码是否正确
        String password = userPwd + passwordSalt;
        String newPassword = DigestUtil.hmacSign(password);
        if (!newPassword.equals(user.getUserPwd())) {
            return Result.err(Result.BAD_REQUEST, "用户密码错误！");
        }
        // 获取参数设置 jwt 载体
        CurrentUser currentUser = new CurrentUser(user.getUserId(), user.getUserCode(), user.getUserName());
        // 生成 jwt token，并保存到 redis
        String token = tokenUtils.loginSign(currentUser, newPassword);
        return Result.ok("登录成功！", token);
    }

    @Override
    public Result currUser(String token) {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        return Result.ok(currentUser);
    }

    public Page queryUserListForPage(Page page, UserInfo user) {
        // 按条件查询查询用户列表总行数
        Integer count = userInfoMapper.selectRowCount(user);
        // 按条件分页查询用户列表
        List<UserInfo> userInfoList = userInfoMapper.selectUserListForPage(page, user);
        // 组装 page
        page.setTotalNum(count);
        page.setResultList(userInfoList);
        return page;
    }

    @Override
    public Result saveUser(UserInfo userInfo, String token) {
        // 获取 userId
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();
        userInfo.setCreateBy(userId);
        userInfo.setCreateTime(new Date());
        userInfo.setIsDelete(WarehouseConstants.LOGIC_CODE);
        userInfo.setUserState(WarehouseConstants.USER_STATE_NOT_PASS);
        // 查询用户账号是否存在
        UserInfo user = userInfoMapper.selectById(userInfo.getUserCode());
        if (user != null) {
            return Result.err(Result.BAD_REQUEST, "用户账号已存在！");
        }
        // 对密码加盐
        String password = userInfo.getUserPwd() + passwordSalt;
        String newPassword = DigestUtil.hmacSign(password);
        userInfo.setUserPwd(newPassword);

        // 保存用户信息
        int insert = userInfoMapper.insert(userInfo);
        if (insert != 1) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "添加用户失败！");
        }

        return Result.ok("添加用户成功！");
    }

    @Override
    public Result setUserState(UserInfo userInfo) {
        int update = userInfoMapper.updateById(userInfo);
        if (update != 1) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "用户分配权限失败！");
        }
        return Result.ok("用户分配权限成功！");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Result deleteUserList(List<Integer> userIdList) {
        int i = userInfoMapper.deleteBatchIds(userIdList);
        System.out.println("i = " + i);
        if (i == 0) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "删除失败！");
        }
        return Result.ok("删除成功！");
    }

    @Override
    public Result updateUserByUid(UserInfo user, String token) {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getUserId());
        user.setCreateTime(new Date());
        user.setCreateBy(currentUser.getUserId());
        int i = userInfoMapper.update(user, queryWrapper);
        if (i != 1) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "修改失败！");
        }
        return Result.ok("修改成功！");
    }

    @Override
    public Result setNewPwd(Integer userId) {
        // 获取新密码
        String Password = WarehouseConstants.ORIGINAL_PASSWORD + passwordSalt;
        String newPwd = DigestUtil.hmacSign(Password);
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserPwd(newPwd);
        int i = userInfoMapper.update(userInfo, queryWrapper);
        if (i != 1) {
            return Result.err(Result.CODE_ERR_UNLOGINED,"重置密码失败！");
        }
        return Result.ok("重置密码完成！");
    }


}




