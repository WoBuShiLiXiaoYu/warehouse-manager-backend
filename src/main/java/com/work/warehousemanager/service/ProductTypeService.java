package com.work.warehousemanager.service;

import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.entity.ProductType;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 胡国海
* @description 针对表【product_type(商品分类表)】的数据库操作Service
* @createDate 2023-12-05 18:20:26
*/
public interface ProductTypeService extends IService<ProductType> {

    /**
     * 查询所有商品分类并转换为树
     * @return
     */
    Result toProductCategoryTree();

    /**
     * 检查编码是否存在
     * @param typeCode
     * @return
     */
    Result checkTypeCode(String typeCode);

    /**
     * 添加商品类型
     * @param productType
     * @return
     */
    Result saveProductType(ProductType productType);

    /**
     * 根据 typeId 移除商品类型
     * @param typeId
     * @return
     */
    Result removeProductTypeById(Integer typeId);

    /** 修改商品分类
     *
     * @param productType
     * @return
     */
    Result updateProductType(ProductType productType);

}
