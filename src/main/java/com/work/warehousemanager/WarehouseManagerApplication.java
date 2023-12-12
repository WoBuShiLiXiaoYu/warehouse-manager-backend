package com.work.warehousemanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.work.warehousemanager.mapper")
@SpringBootApplication
public class WarehouseManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseManagerApplication.class, args);
    }

}
