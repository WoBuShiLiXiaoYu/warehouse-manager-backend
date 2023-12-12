package com.work.warehousemanager.service;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;


/**
* @author 胡国海
* @description 针对表【role(角色表)】的数据库操作Service
* @createDate 2023-12-04 16:18:24
*/
public interface RoleService extends IService<Role> {

    /**
     * 查询所有角色列表
     * @return
     */
    Result queryRoleList();

    /**
     * 根据用户 id 查询用户角色关系
     * @param userId
     * @return
     */
    Result queryUserRoleList(Integer userId);


    /**
     * 按条件分页查询角色信息
     * @param page
     * @param role
     * @return
     */
    Result queryRolePageList(Page page, Role role);

    /**
     * 添加角色信息
     * @param role
     * @param token
     * @return
     */
    Result saveRole(Role role, String token);

    /**
     * 修改角色状态
     * @param role
     * @return
     */
    Result setRoleState(Role role);

    /**
     * 根据 id 删除角色，并清除角色对应关系
     * @param roleId
     * @return
     */
    Result deleteRoleById(Integer roleId);

    /**
     * 根据角色 Id 修改角色描述
     * @param role
     * @param token
     * @return
     */
    Result setRoleDesc(Role role, String token);

}
