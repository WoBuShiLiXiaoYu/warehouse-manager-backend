package com.work.warehousemanager.mapper;

import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 胡国海
* @description 针对表【role(角色表)】的数据库操作Mapper
* @createDate 2023-12-04 16:18:24
* @Entity com.work.warehousemanager.entity.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户 id 查询用户角色关系
     * @param userId
     * @return
     */

    List<Role> selectUserRole(@Param("userId") Integer userId);

    /**
     * 按条件查询总行数
     * @param role
     * @return
     */
    int selectRowConut(@Param("role") Role role);

    /**
     * 按条件分页查询
     * @param page
     * @param role
     * @return
     */
    List<Role> selectUserListForPage(@Param("page") Page page, @Param("role") Role role);
}




