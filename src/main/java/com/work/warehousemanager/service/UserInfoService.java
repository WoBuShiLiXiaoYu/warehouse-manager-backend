package com.work.warehousemanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.dto.user.LoginUser;
import com.work.warehousemanager.entity.UserInfo;

import java.util.List;

/**
* @author 胡国海
* @description 针对表【user_info(用户表)】的数据库操作Service
* @createDate 2023-12-01 17:41:32
*/
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 用户登录
     * @param loginUser
     * @return
     */
    Result loginUser(LoginUser loginUser);

    /**
     * 解析 token 获取用户信息
     * @param token
     * @return
     */
    Result currUser(String token);

    /**
     * 分页查询
     * @param page
     * @param user
     * @return
     */
    Page queryUserListForPage(Page page, UserInfo user);

    /**
     * 添加用户
     * @param userInfo
     * @return
     */
    Result saveUser(UserInfo userInfo, String token);

    /**
     * 分配用户权限
     * @param userInfo
     * @return
     */
    Result setUserState(UserInfo userInfo);


    /**
     * 批量删除用户
     * @param userIdList
     * @return
     */
    Result deleteUserList(List<Integer> userIdList);

    /**
     * 根据用户 id 修改用户昵称
     * @return
     * @param user
     * @param token
     */
    Result updateUserByUid(UserInfo user, String token);

    /**
     *  根据用户 id 重置密码 123456(加密加盐)
     * @return
     * @param userId
     */
    Result setNewPwd(Integer userId);
}
