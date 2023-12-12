package com.work.warehousemanager.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.Store;
import com.work.warehousemanager.service.StoreService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Resource
    private StoreService storeService;
    @Value("${file.download-path}")
    private String downloadPath;

    @RequestMapping("/exportTable")
    public Result exportTable(Page page, Store store) throws IOException {
        List<Store> storeList = storeService.selectCountByCriteria(page, store);

        ExcelWriter writer = ExcelUtil.getWriter();
        writer.write(storeList, true);
        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //response.setContentType("application/octet-stream;charset=utf-8");
        //response.setHeader("Content-Disposition","attachment;filename=store.xls");
        //ServletOutputStream out=response.getOutputStream();
        Class<? extends Store> aClass = storeList.get(0).getClass();
        String fileName = aClass.getSimpleName();
        downloadPath = downloadPath + fileName +".xls";

        FileOutputStream out = new FileOutputStream(downloadPath);

        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
        System.out.println("操作完成！");
        return Result.ok(storeList);
    }

    @RequestMapping("/store-page-list")
    public Result getStorePageList(Page page, Store store) {
        Result result = storeService.getStorePageList(page, store);
        return result;
    }
}
