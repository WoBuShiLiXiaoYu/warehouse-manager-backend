package com.work.warehousemanager.service;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.entity.Brand;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 胡国海
* @description 针对表【brand(品牌)】的数据库操作Service
* @createDate 2023-12-05 18:19:08
*/
public interface BrandService extends IService<Brand> {

    /**
     * 查询品牌表集合
     * @return
     */
    Result getBrandList();
}
