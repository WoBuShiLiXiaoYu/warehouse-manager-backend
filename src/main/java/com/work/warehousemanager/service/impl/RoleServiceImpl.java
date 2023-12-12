package com.work.warehousemanager.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.RedisConstants;
import com.work.warehousemanager.constants.WarehouseConstants;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.dto.user.CurrentUser;
import com.work.warehousemanager.entity.Role;
import com.work.warehousemanager.entity.RoleAuth;
import com.work.warehousemanager.mapper.RoleAuthMapper;
import com.work.warehousemanager.service.RoleService;
import com.work.warehousemanager.mapper.RoleMapper;
import com.work.warehousemanager.utils.TokenUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* @author 胡国海
* @description 针对表【role(角色表)】的数据库操作Service实现
* @createDate 2023-12-04 16:18:24
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleAuthMapper roleAuthMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private TokenUtils tokenUtils;

    @Override
    public Result queryRoleList() {
        // 从缓存中获取角色列表
        String jsonStr = stringRedisTemplate.opsForValue().get(RedisConstants.ALL_ROLE_KEY);
        if (!StrUtil.isBlank(jsonStr)) {
            return Result.ok(JSONUtil.toList(jsonStr, Role.class));
        }
        // 没有缓存则从数据库中获取启用的角色
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_state", WarehouseConstants.ROLE_STATE_PASS);
        List<Role> roles = roleMapper.selectList(queryWrapper);
        // 写入缓存
        stringRedisTemplate.opsForValue().set(RedisConstants.ALL_ROLE_KEY,
                JSONUtil.toJsonStr(roles),
                RedisConstants.AUTH_TTL,
                TimeUnit.SECONDS);
        return Result.ok(roles);
    }

    @Override
    public Result queryUserRoleList(Integer userId) {
        List<Role> list = roleMapper.selectUserRole(userId);
        return Result.ok(list);
    }

    @Override
    public Result queryRolePageList(Page page, Role role) {
        // 按条件查询角色总行数
        int count = roleMapper.selectRowConut(role);
        // 按条件查询角色信息列表
        List<Role> roleInfoList = roleMapper.selectUserListForPage(page, role);

        // 组装 Page 对象
        page.setTotalNum(count);
        page.setResultList(roleInfoList);
        return Result.ok(page);
    }

    @Override
    public Result saveRole(Role role, String token) {
        // 解析 token
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        // 根据角色名，判断角色是否存在
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_name", role.getRoleName())
                .or()
                .eq("role_code", role.getRoleCode());
        Role selectOne = roleMapper.selectOne(queryWrapper);
        if (selectOne != null) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "角色已存在！");
        }
        role.setCreateBy(currentUser.getUserId());
        role.setCreateTime(new Date());
        role.setRoleState(WarehouseConstants.ROLE_STATE_NOT_PASS);
        int insert = roleMapper.insert(role);
        if (insert != 1) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "添加角色失败！");
        }
        return Result.ok("添加角色成功！");
    }

    @Override
    public Result setRoleState(Role role) {
        // 删除 redis 中缓存的角色
        stringRedisTemplate.delete(RedisConstants.ALL_ROLE_KEY);
        int i = roleMapper.updateById(role);
        if (i != 1) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "启用或禁用角色失败！");
        }
        // 重建缓存
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_state", WarehouseConstants.ROLE_STATE_PASS);
        List<Role> roles = roleMapper.selectList(queryWrapper);
        // 写入缓存
        stringRedisTemplate.opsForValue().set(RedisConstants.ALL_ROLE_KEY, JSONUtil.toJsonStr(roles));
        return Result.ok("启用或禁用角色成功！");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Result deleteRoleById(Integer roleId) {
        // 删除 redis 中缓存的角色
        stringRedisTemplate.delete(RedisConstants.ALL_ROLE_KEY);
        // 删除数据库中数据
        int i = roleMapper.deleteById(roleId);
        if (i != 0) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "删除角色失败！");
        }
        // 删除角色权限对应关系
        QueryWrapper<RoleAuth> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        roleAuthMapper.delete(queryWrapper);
        return null;
    }

    @Override
    public Result setRoleDesc(Role role, String token) {
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        role.setUpdateBy(currentUser.getUserId());
        role.setUpdateTime(new Date());
        // 删除缓存中的角色信息
        stringRedisTemplate.delete(RedisConstants.ALL_ROLE_KEY);
        // 修改角色信息
        UpdateWrapper<Role> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("role_desc", role.getRoleDesc())
                .set("update_time", role.getCreateTime())
                .set("update_by", role.getUpdateBy())
                .eq("role_id", role.getUpdateTime());
        int update = roleMapper.update(role, updateWrapper);
        if (update != 1) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "修改失败！");
        }
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_state", WarehouseConstants.ROLE_STATE_PASS);
        List<Role> roles = roleMapper.selectList(queryWrapper);
        // 写入缓存
        stringRedisTemplate.opsForValue().set(RedisConstants.ALL_ROLE_KEY,
                JSONUtil.toJsonStr(roles),
                RedisConstants.AUTH_TTL,
                TimeUnit.SECONDS);
        return Result.ok("修改成功");
    }
}




