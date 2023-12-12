package com.work.warehousemanager.service;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.InStore;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 胡国海
* @description 针对表【in_store(入库单)】的数据库操作Service
* @createDate 2023-12-07 11:13:08
*/
public interface InStoreService extends IService<InStore> {

    /**
     *  按条件进行分页查询，并统计总行数
     * @param page
     * @param inStore
     * @return
     */
    Result inStorePageList(Page page, InStore inStore);

    /**
     * 确认入库
     * @param inStore
     * @return
     */
    Result confirm(InStore inStore);
}
