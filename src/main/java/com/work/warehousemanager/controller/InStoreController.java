package com.work.warehousemanager.controller;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.InStore;
import com.work.warehousemanager.service.InStoreService;
import com.work.warehousemanager.service.StoreService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/instore")
public class InStoreController {

    @Resource
    private InStoreService inStoreService;
    @Resource
    private StoreService storeService;

    @RequestMapping("/instore-confirm")
    public Result inStoreConfirm(@RequestBody InStore inStore) {
        Result result = inStoreService.confirm(inStore);
        return result;
    }

    @RequestMapping("/instore-page-list")
    public Result inStorePageList(Page page, InStore inStore) {
        Result result = inStoreService.inStorePageList(page, inStore);
        return result;
    }

    @RequestMapping("/store-list")
    public Result getStoreList() {
        Result result = storeService.getStoreList();
        return result;
    }
}
