package com.work.warehousemanager.service;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.BuyList;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 胡国海
* @description 针对表【buy_list(采购单)】的数据库操作Service
* @createDate 2023-12-06 19:59:36
*/
public interface BuyListService extends IService<BuyList> {

    /**
     * 添加采购单
     * @param buyList
     * @return
     */
    Result savePurchase(BuyList buyList);

    /**
     * 按条件分页查询
     * @param page
     * @param buyList
     * @return
     */
    Result getPageList(Page page, BuyList buyList);

    /**
     * 根据 Id 删除采购单
     * @param buyId
     * @return
     */
    Result removeBuyListById(Integer buyId);

    /**
     * 根据 Id 修改采购单
     * @param buyList
     * @return
     */
    Result updatePurchase(BuyList buyList);

    /**
     * 生成入库单
     * @param buyList
     * @param token
     * @return
     */
    Result purchaseToInStore(BuyList buyList, String token);
}
