package com.work.warehousemanager.service;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.entity.Unit;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 胡国海
* @description 针对表【unit(规格单位表)】的数据库操作Service
* @createDate 2023-12-05 18:19:57
*/
public interface UnitService extends IService<Unit> {

    /**
     * 查询所有规格单位集合
     * @return
     */
    Result getUnitList();
}
