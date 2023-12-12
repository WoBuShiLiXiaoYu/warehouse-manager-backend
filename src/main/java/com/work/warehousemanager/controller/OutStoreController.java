package com.work.warehousemanager.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.WarehouseConstants;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.OutStore;
import com.work.warehousemanager.entity.Store;
import com.work.warehousemanager.service.OutStoreService;
import com.work.warehousemanager.service.StoreService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/outstore")
public class OutStoreController {

    @Resource
    private OutStoreService outStoreService;
    @Resource
    private StoreService storeService;

    @Value("${file.download-path}")
    private String downloadPath;


    @RequestMapping("/outstore-confirm")
    public Result outStoreConfirm(@RequestBody OutStore outStore) {
        Result result = outStoreService.outStoreConfirm(outStore);
        return result;
    }

    @RequestMapping("/outstore-page-list")
    public Result outStorePageList(Page page, OutStore outStore) {
        Result result = outStoreService.outStorePageList(page, outStore);
        return result;
    }

    @RequestMapping("/outstore-add")
    public Result addOutStore(@RequestBody OutStore outStore,
                              @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        Result result = outStoreService.saveOutStore(outStore, token);
        return result;
    }

    @RequestMapping("/store-list")
    public Result getStoreList() {
        Result result = storeService.getStoreList();
        return result;
    }
}
