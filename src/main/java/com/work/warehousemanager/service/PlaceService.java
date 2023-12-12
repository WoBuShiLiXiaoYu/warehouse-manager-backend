package com.work.warehousemanager.service;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.entity.Place;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 胡国海
* @description 针对表【place(产地)】的数据库操作Service
* @createDate 2023-12-05 18:20:12
*/
public interface PlaceService extends IService<Place> {

    /**
     * 查询所有产地集合
     * @return
     */
    Result getPlaceList();
}
