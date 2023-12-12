package com.work.warehousemanager.controller;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.WarehouseConstants;
import com.work.warehousemanager.dto.assignrole.AssignAuthDto;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.Role;
import com.work.warehousemanager.entity.RoleAuth;
import com.work.warehousemanager.service.RoleAuthService;
import com.work.warehousemanager.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;
    @Resource
    private RoleAuthService roleAuthService;

    @RequestMapping("/role-update")
    public Result updateRoleById(@RequestBody Role role,
                                 @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        Result result = roleService.setRoleDesc(role, token);
        return result;
    }

    @RequestMapping("/auth-grant")
    public Result setAuthGrant(@RequestBody AssignAuthDto authDto) {
        Result result = roleAuthService.setAuthGrant(authDto);
        return result;
    }

    @RequestMapping("/role-auth")
    public Result getRoleAuth(Integer roleId) {
        Result result = roleAuthService.getRoleAuthById(roleId);
        return result;
    }

    @RequestMapping("/role-delete/{roleId}")
    public Result deleteRole(@PathVariable("roleId") Integer roleId) {
        Result result = roleService.deleteRoleById(roleId);
        return result;
    }

    @RequestMapping("/role-state-update")
    public Result setRoleState(@RequestBody Role role) {
        Result result = roleService.setRoleState(role);
        return result;
    }

    @RequestMapping("/role-add")
    public Result addRole(@RequestBody Role role,
                          @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        Result result = roleService.saveRole(role, token);
        return result;
    }

    @RequestMapping("/role-page-list")
    public Result rolePageList(Page page, Role role) {
        Result result = roleService.queryRolePageList(page, role);
        return result;
    }

    @RequestMapping("/role-list")
    public Result getRoleList() {
        Result result = roleService.queryRoleList();
        return result;
    }
}
