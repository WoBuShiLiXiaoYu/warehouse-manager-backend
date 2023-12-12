package com.work.warehousemanager.service;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.dto.assignrole.AssignRoleDto;
import com.work.warehousemanager.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 胡国海
* @description 针对表【user_role(用户角色表)】的数据库操作Service
* @createDate 2023-12-04 17:40:32
*/
public interface UserRoleService extends IService<UserRole> {

    /**
     * 修改用户角色
     * @param assignRoleDto
     * @return
     */
    Result assignRole(AssignRoleDto assignRoleDto);
}
