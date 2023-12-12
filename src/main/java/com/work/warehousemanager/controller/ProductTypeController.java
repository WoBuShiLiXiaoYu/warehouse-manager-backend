package com.work.warehousemanager.controller;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.entity.ProductType;
import com.work.warehousemanager.service.ProductTypeService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/productCategory")
public class ProductTypeController {
    @Resource
    private ProductTypeService productTypeService;

    @RequestMapping("/type-update")
    public Result updateProductType(@RequestBody ProductType productType) {
        Result result = productTypeService.updateProductType(productType);
        return result;
    }

    @RequestMapping("/type-delete/{typeId}")
    public Result deleteProductType(@PathVariable("typeId") Integer typeId) {
        Result result = productTypeService.removeProductTypeById(typeId);
        return result;
    }

    @RequestMapping("/type-add")
    public Result addProductType(@RequestBody ProductType productType) {
        Result result = productTypeService.saveProductType(productType);
        return result;
    }

    @RequestMapping("/verify-type-code")
    public Result checkTypeCode(String typeCode) {
        Result result = productTypeService.checkTypeCode(typeCode);
        return result;
    }

    @RequestMapping("/product-category-tree")
    public Result toProductCategoryTree() {
        Result result = productTypeService.toProductCategoryTree();
        return result;
    }
}
