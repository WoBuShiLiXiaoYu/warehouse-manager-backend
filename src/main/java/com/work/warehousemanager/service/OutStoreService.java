package com.work.warehousemanager.service;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.OutStore;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 胡国海
* @description 针对表【out_store(出库单)】的数据库操作Service
* @createDate 2023-12-06 20:38:44
*/
public interface OutStoreService extends IService<OutStore> {

    /**
     * 添加出库单
     * @param outStore
     * @param token
     * @return
     */
    Result saveOutStore(OutStore outStore, String token);

    /**
     * 按条件分页查询
     * @param page
     * @param outStore
     * @return
     */
    Result outStorePageList(Page page, OutStore outStore);

    /**
     * 确认出库
     * @param outStore
     * @return
     */
    Result outStoreConfirm(OutStore outStore);
}
