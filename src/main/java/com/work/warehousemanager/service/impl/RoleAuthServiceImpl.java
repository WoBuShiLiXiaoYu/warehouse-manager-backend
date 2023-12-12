package com.work.warehousemanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.dto.assignrole.AssignAuthDto;
import com.work.warehousemanager.entity.RoleAuth;
import com.work.warehousemanager.service.RoleAuthService;
import com.work.warehousemanager.mapper.RoleAuthMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author 胡国海
* @description 针对表【role_auth(角色权限表)】的数据库操作Service实现
* @createDate 2023-12-05 11:22:40
*/
@Service
public class RoleAuthServiceImpl extends ServiceImpl<RoleAuthMapper, RoleAuth>
    implements RoleAuthService{

    @Resource
    private RoleAuthMapper roleAuthMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result getRoleAuthById(Integer roleId) {
        QueryWrapper<RoleAuth> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        List<RoleAuth> roleAuthList = roleAuthMapper.selectList(queryWrapper);
        ArrayList<Integer> authIdList = new ArrayList<>();
        for (RoleAuth roleAuth : roleAuthList) {
            authIdList.add(roleAuth.getAuthId());
        }
        return Result.ok(authIdList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result setAuthGrant(AssignAuthDto roleAuth) {
        // 先删除原来的角色权限关系
        QueryWrapper<RoleAuth> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleAuth.getRoleId());
        roleAuthMapper.delete(queryWrapper);
        // 创建角色权限关系
        List<Integer> authIds = roleAuth.getAuthIds();
        Integer roleId = roleAuth.getRoleId();
        int count = 0;
        for (Integer authId : authIds) {
            RoleAuth ra = new RoleAuth();
            ra.setAuthId(authId);
            ra.setRoleId(roleId);
            int insert = roleAuthMapper.insert(ra);
            if (insert == 1) {
                count++;
            }
        }
        if (count != authIds.size()) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "分配权限失败！");
        }
        return Result.ok("分配权限成功！");
    }
}




