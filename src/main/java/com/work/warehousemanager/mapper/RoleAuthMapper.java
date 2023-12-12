package com.work.warehousemanager.mapper;

import com.work.warehousemanager.entity.RoleAuth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 胡国海
* @description 针对表【role_auth(角色权限表)】的数据库操作Mapper
* @createDate 2023-12-05 11:22:40
* @Entity com.work.warehousemanager.entity.RoleAuth
*/
public interface RoleAuthMapper extends BaseMapper<RoleAuth> {


    /**
     * 根据 roleId 查询 AuthId
     * @param roleId
     * @return
     */
    /*List<Integer> selectRoleAuthById(Integer roleId);*/
}




