package com.work.warehousemanager.service;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.Store;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 胡国海
* @description 针对表【store(仓库表)】的数据库操作Service
* @createDate 2023-12-05 18:19:37
*/
public interface StoreService extends IService<Store> {

    /**
     * 查询仓库表集合
     * @return
     */
    Result getStoreList();

    /**
     * 查询仓库下商品数量
     * @return
     */
    Result storeService();

    /**
     * 按条件分页查询
     * @param page
     * @param store
     * @return
     */
    Result getStorePageList(Page page, Store store);

    List<Store> selectCountByCriteria(Page page, Store store);
}
