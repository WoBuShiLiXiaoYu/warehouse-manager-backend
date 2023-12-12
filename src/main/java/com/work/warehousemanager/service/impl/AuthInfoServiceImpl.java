package com.work.warehousemanager.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.RedisConstants;
import com.work.warehousemanager.constants.WarehouseConstants;
import com.work.warehousemanager.dto.user.CurrentUser;
import com.work.warehousemanager.entity.AuthInfo;
import com.work.warehousemanager.service.AuthInfoService;
import com.work.warehousemanager.mapper.AuthInfoMapper;
import com.work.warehousemanager.utils.TokenUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* @author 胡国海
* @description 针对表【auth_info(权限表)】的数据库操作Service实现
* @createDate 2023-12-02 16:08:48
*/
@Service
public class AuthInfoServiceImpl extends ServiceImpl<AuthInfoMapper, AuthInfo>
    implements AuthInfoService{

    @Resource
    private AuthInfoMapper authInfoMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private TokenUtils tokenUtils;

    @Override
    public Result queryAuthList(String token) {
        // 解析 token 获取用户信息
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();
        // 先判断 redis 中是否有缓存
        String json = stringRedisTemplate.opsForValue().get(RedisConstants.LOGIN_USER_TREE_AUTH_KEY + userId);
        if (!StrUtil.isBlank(json)) {
            // 返回
            List<AuthInfo> authInfos = JSONUtil.toList(json, AuthInfo.class);
            return Result.ok(authInfos);
        }
        // redis 中没有缓存,根据 userId 进行查询
        List<AuthInfo> authInfoList = authInfoMapper.findAuthListByUserId(userId);
        // 将所有菜单 List<AuthInfo> 转换为 菜单树 List<AuthInfo>
        List<AuthInfo> toTree = allListToTree(authInfoList, WarehouseConstants.PARENT_ID);
        // 向 redis 中缓存
        stringRedisTemplate.opsForValue().set(RedisConstants.LOGIN_USER_TREE_AUTH_KEY + userId,
                JSONUtil.toJsonStr(toTree),
                RedisConstants.AUTH_TTL,
                TimeUnit.SECONDS);
        return Result.ok(toTree);
    }

    @Override
    public Result allAuthTree() {
        // 判断是否有缓存
        String json = stringRedisTemplate.opsForValue().get(RedisConstants.TREE_AUTH_ALL_KEY);
        if (!StrUtil.isBlank(json)) {
            // 返回
            List<AuthInfo> authInfos = JSONUtil.toList(json, AuthInfo.class);
            return Result.ok(authInfos);
        }
        QueryWrapper<AuthInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("auth_state", WarehouseConstants.AUTH_STATE_PASS)
                        .ne("auth_type", WarehouseConstants.AUTH_TYPE_BUTTON);
        List<AuthInfo> authInfoList = authInfoMapper.selectList(queryWrapper);
        // 转化为权限树
        List<AuthInfo> toTree = this.allListToTree(authInfoList, WarehouseConstants.PARENT_ID);
        // 向 redis 中缓存
        stringRedisTemplate.opsForValue().set(RedisConstants.TREE_AUTH_ALL_KEY,
                JSONUtil.toJsonStr(toTree),
                RedisConstants.AUTH_TTL,
                TimeUnit.SECONDS);
        return Result.ok(toTree);
    }

    /**
     * 将菜单转换为菜单树的递归算法
     * @param list 菜单集合
     * @param pid 上一级菜单 id
     * @return
     */
    private List<AuthInfo> allListToTree(List<AuthInfo> list, Integer pid) {

        // 当 firstLevelAuthList 大小为 0 时，不会进入增强 for
        ArrayList<AuthInfo> firstLevelAuthList = new ArrayList<>();
        // 遍历传入的集合，找出对应等级菜单
        System.out.println("当前 firstLevelAuthList size = " +firstLevelAuthList.size());
        for (AuthInfo authInfo : list) {
            if (authInfo.getParentId().equals(pid)) {
                firstLevelAuthList.add(authInfo);
                System.out.println("循环内 firstLevelAuthList size = " +firstLevelAuthList.size());
                System.out.println(authInfo);
            }
        }

        // 找出下一级菜单，递归
        for (AuthInfo firstAuth : firstLevelAuthList) {
            // 子菜单的 id 是上一级菜单的 pid
            List<AuthInfo> secondLevelAuthList = allListToTree(list, firstAuth.getAuthId());
            firstAuth.setChildAuth(secondLevelAuthList);
        }
        return firstLevelAuthList;
    }
}




