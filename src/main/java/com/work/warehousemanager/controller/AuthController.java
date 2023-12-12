package com.work.warehousemanager.controller;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.service.AuthInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthInfoService authInfoService;

    @RequestMapping("/auth-tree")
    public Result allAuthTree() {
        Result result = authInfoService.allAuthTree();
        return result;
    }

}
