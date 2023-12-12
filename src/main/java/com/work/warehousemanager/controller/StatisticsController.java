package com.work.warehousemanager.controller;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.service.StoreService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Resource
    private StoreService storeService;

    @RequestMapping("/store-invent")
    public Result storeInvent() {
        Result result = storeService.storeService();
        return result;
    }
}
