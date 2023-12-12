package com.work.warehousemanager.controller;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.WarehouseConstants;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.BuyList;
import com.work.warehousemanager.entity.OutStore;
import com.work.warehousemanager.service.BuyListService;
import com.work.warehousemanager.service.OutStoreService;
import com.work.warehousemanager.service.StoreService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Resource
    private BuyListService buyListService;
    @Resource
    private StoreService storeService;

    @RequestMapping("/in-warehouse-record-add")
    public Result purchaseToInStore(@RequestBody BuyList buyList,
                                    @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        Result result = buyListService.purchaseToInStore(buyList, token);
        return result;
    }

    @RequestMapping("/purchase-update")
    public Result updatePurchase(@RequestBody BuyList buyList) {
        Result result = buyListService.updatePurchase(buyList);
        return result;
    }

    @RequestMapping("/purchase-delete/{buyId}")
    public Result deletePurchase(@PathVariable("buyId") Integer buyId) {
        Result result = buyListService.removeBuyListById(buyId);
        return result;
    }

    @RequestMapping("/purchase-page-list")
    public Result getPurchasePageList(Page page, BuyList buyList) {
        Result result = buyListService.getPageList(page, buyList);
        return result;
    }

    @RequestMapping("/store-list")
    public Result getStoreList() {
        Result result = storeService.getStoreList();
        return result;
    }

    @RequestMapping("/purchase-add")
    public Result addPurchase(@RequestBody BuyList buyList) {
        Result result = buyListService.savePurchase(buyList);
        return result;
    }
}
