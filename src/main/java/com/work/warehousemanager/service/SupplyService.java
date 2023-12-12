package com.work.warehousemanager.service;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.entity.Supply;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 胡国海
* @description 针对表【supply(供货商)】的数据库操作Service
* @createDate 2023-12-05 18:19:47
*/
public interface SupplyService extends IService<Supply> {

    /**
     * 查询所有供货商集合
     * @return
     */
    Result getSupplyList();
}
