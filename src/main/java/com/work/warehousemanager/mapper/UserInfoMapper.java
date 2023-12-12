package com.work.warehousemanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 胡国海
* @description 针对表【user_info(用户表)】的数据库操作Mapper
* @createDate 2023-12-01 17:41:32
* @Entity com.work.warehousemanager.entity.UserInfo
*/
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    Integer selectRowCount(@Param("user") UserInfo user);

    List<UserInfo> selectUserListForPage(@Param("page") Page page, @Param("user") UserInfo user);



}




