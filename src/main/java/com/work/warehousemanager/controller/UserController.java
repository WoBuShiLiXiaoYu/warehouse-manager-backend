package com.work.warehousemanager.controller;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.WarehouseConstants;
import com.work.warehousemanager.dto.assignrole.AssignRoleDto;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.UserInfo;
import com.work.warehousemanager.service.RoleService;
import com.work.warehousemanager.service.UserInfoService;
import com.work.warehousemanager.service.UserRoleService;
import com.work.warehousemanager.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private RoleService roleService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private TokenUtils tokenUtils;

    @RequestMapping("/updatePwd/{userId}")
    public Result updatePwd(@PathVariable("userId") Integer userId) {
       Result result = userInfoService.setNewPwd(userId);
       return result;
    }

    @RequestMapping("/updateUser")
    public Result updateUser(@RequestBody UserInfo user,
                             @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        Result result = userInfoService.updateUserByUid(user, token);
        return result;
    }

    @RequestMapping("/deleteUser/{userId}")
    public Result deleteByUid(@PathVariable("userId") Integer userId) {
        Result result = deleteUserList(Arrays.asList(userId));
        return result;
    }

    @RequestMapping("/deleteUserList")
    public Result deleteUserList(@RequestBody List<Integer> userIdList) {
        Result result = userInfoService.deleteUserList(userIdList);
        return result;
    }

    @RequestMapping("/assignRole")
    public Result assignRole(@RequestBody AssignRoleDto assignRoleDto) {
        Result result = userRoleService.assignRole(assignRoleDto);
        return result;
    }

    @RequestMapping("/user-role-list/{userId}")
    public Result getUserRoleList(@PathVariable("userId") Integer userId) {
        Result result = roleService.queryUserRoleList(userId);
        return result;
    }

    @RequestMapping("/updateState")
    public Result updateState(@RequestBody UserInfo userInfo) {
        userInfo.setUpdateTime(new Date());
       Result result = userInfoService.setUserState(userInfo);
       return result;
    }

    @RequestMapping("/addUser")
    public Result addUser(@RequestBody UserInfo userInfo,
                          @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        Result result = userInfoService.saveUser(userInfo, token);
        return result;
    }

    @RequestMapping("/user-list")
    public Result userList(Page page, UserInfo user) {
        // 条件查询用户
        page = userInfoService.queryUserListForPage(page, user);
        return Result.ok(page);
    }
}
