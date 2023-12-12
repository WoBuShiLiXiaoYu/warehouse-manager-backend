package com.work.warehousemanager.service;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 胡国海
* @description 针对表【product(商品表)】的数据库操作Service
* @createDate 2023-12-05 18:33:18
*/
public interface ProductService extends IService<Product> {

    /**
     * 按条件分页查询商品集合
     * @return
     */
    Result productPageList(Product product, Page page);

    /**
     * 添加商品
     * @param product
     * @param token
     * @return
     */
    Result saveProduct(Product product, String token);

    /**
     * 修改商品上下架状态
     * @param product
     * @return
     */
    Result updateProductState(Product product);

    /**
     * 根据 ids 批量删除商品
     * @param ids
     * @return
     */
    Result deleteProductList(List<Integer> ids);

    /**
     * 修改商品信息
     * @param product
     * @return
     */
    Result updateProduct(Product product, String token);
}
