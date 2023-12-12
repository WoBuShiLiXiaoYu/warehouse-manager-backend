package com.work.warehousemanager.service;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.dto.assignrole.AssignAuthDto;
import com.work.warehousemanager.entity.RoleAuth;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 胡国海
* @description 针对表【role_auth(角色权限表)】的数据库操作Service
* @createDate 2023-12-05 11:22:40
*/
public interface RoleAuthService extends IService<RoleAuth> {

    /**
     * 根据 roleId 查询角色权限关系
     * @param roleId
     * @return
     */
    Result getRoleAuthById(Integer roleId);

    /**
     * 设置角色与权限关系
     * @param roleAuth
     * @return
     */
    Result setAuthGrant(AssignAuthDto roleAuth);
}
