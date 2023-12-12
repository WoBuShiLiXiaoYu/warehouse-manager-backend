package com.work.warehousemanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 胡国海
* @description 针对表【product(商品表)】的数据库操作Mapper
* @createDate 2023-12-05 18:36:10
* @Entity generator.domain.Product
*/
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 按条件查询商品总行数
     * @param product
     * @return
     */
    Integer getProductCountByCriteria(@Param("product") Product product);

    /**
     * 按条件分页查询商品集合
     * @param product
     * @param page
     * @return
     */
    List<Product> getProductListByCriteria(@Param("product") Product product, @Param("page") Page page);
}




