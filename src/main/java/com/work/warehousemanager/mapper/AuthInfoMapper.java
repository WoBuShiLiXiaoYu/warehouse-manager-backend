package com.work.warehousemanager.mapper;

import com.work.warehousemanager.entity.AuthInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 胡国海
* @description 针对表【auth_info(权限表)】的数据库操作Mapper
* @createDate 2023-12-02 16:08:48
* @Entity com.work.warehousemanager.entity.AuthInfo
*/
public interface AuthInfoMapper extends BaseMapper<AuthInfo> {

    /**
     * 根据用户 id 查询用户权限下所有菜单
     * @param userId
     * @return
     */
    List<AuthInfo> findAuthListByUserId(@Param("userId") Integer userId);

}




