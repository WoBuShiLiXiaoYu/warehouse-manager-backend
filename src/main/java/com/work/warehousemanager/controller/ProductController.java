package com.work.warehousemanager.controller;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.WarehouseConstants;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.Product;
import com.work.warehousemanager.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private StoreService storeService;
    @Resource
    private BrandService brandService;
    @Resource
    private ProductService productService;
    @Resource
    private ProductTypeService productTypeService;
    @Resource
    private SupplyService supplyService;
    @Resource
    private PlaceService placeService;
    @Resource
    private UnitService unitService;
    @Value("${file.upload-path}")
    private String uploadPath;

    @RequestMapping("/product-update")
    public Result updateProduct(@RequestBody Product product,
                                @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        Result result = productService.updateProduct(product, token);
        return result;
    }

    @RequestMapping("/product-delete/{id}")
    public Result deleteProductById(@PathVariable("id") Integer id) {
        Result result = productService.deleteProductList(Arrays.asList(id));
        return result;
    }

    @RequestMapping("/product-list-delete")
    public Result deleteProductList(@RequestBody List<Integer> ids) {
        Result result = productService.deleteProductList(ids);
        return result;

    }

    @RequestMapping("/state-change")
    public Result updateProductState(@RequestBody Product product) {
        Result result = productService.updateProductState(product);
        return result;
    }

    @RequestMapping("/product-add")
    public Result addProduct(@RequestBody Product product,
                             @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
       Result result = productService.saveProduct(product, token);
       return result;

    }

    @RequestMapping("/img-upload")
    public Result imgUpload(MultipartFile file) {

        try {
            // 获取图片上传路径的 File 对象
            File uploadPathFile = ResourceUtils.getFile(this.uploadPath);
            // 获取图片上传路径的磁盘路径
            String uploadPathFileAbsolutePath = uploadPathFile.getAbsolutePath();
            // 获取上传图片的名称
            String filename = file.getOriginalFilename();
            // 保存的磁盘路径
            String path = uploadPathFileAbsolutePath + "\\" + filename;
            // 上传
            file.transferTo(new File(path));
            // 成功
            return Result.ok("图片上传成功！");
        } catch (Exception e) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "图片上传失败！");
        }

    }

    @RequestMapping("/unit-list")
    public Result getUnitList() {
        Result result = unitService.getUnitList();
        return result;
    }

    @RequestMapping("/place-list")
    public Result getPlaceList() {
        Result result = placeService.getPlaceList();
        return result;
    }

    @RequestMapping("/supply-list")
    public Result getSupplyList() {
        Result result = supplyService.getSupplyList();
        return result;
    }

    @RequestMapping("category-tree")
    public Result toProductCategoryTree() {
        Result result = productTypeService.toProductCategoryTree();
        return result;
    }

    @RequestMapping("/product-page-list")
    public Result productPageList(Product product, Page page) {
        Result result = productService.productPageList(product, page);
        return result;
    }

    @RequestMapping("/brand-list")
    public Result getBrandList() {
        Result result = brandService.getBrandList();
        return result;
    }

    @RequestMapping("/store-list")
    public Result getStoreList() {
        Result result = storeService.getStoreList();
        return result;
    }
}
