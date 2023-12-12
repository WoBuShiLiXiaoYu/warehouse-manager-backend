package com.work.warehousemanager.service;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.entity.AuthInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 胡国海
* @description 针对表【auth_info(权限表)】的数据库操作Service
* @createDate 2023-12-02 16:08:48
*/
public interface AuthInfoService extends IService<AuthInfo> {

    /**
     * 查询权限菜单
     * @param token
     * @return
     */
    Result queryAuthList(String token);

    /**
     * 查询所有权限列表，并转换为权限树
     * @return
     */
    Result allAuthTree();
}
