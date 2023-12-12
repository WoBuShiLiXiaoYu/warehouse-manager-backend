package com.work.warehousemanager.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.RedisConstants;
import com.work.warehousemanager.constants.WarehouseConstants;
import com.work.warehousemanager.entity.ProductType;
import com.work.warehousemanager.service.ProductTypeService;
import com.work.warehousemanager.mapper.ProductTypeMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* @author 胡国海
* @description 针对表【product_type(商品分类表)】的数据库操作Service实现
* @createDate 2023-12-05 18:20:26
*/
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType>
    implements ProductTypeService{

    @Resource
    private ProductTypeMapper productTypeMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result toProductCategoryTree() {
        // 先从缓存中查询
        String jsonStr = stringRedisTemplate.opsForValue().get(RedisConstants.TREE_PRODUCT_ALL_KEY);
        if (!StrUtil.isBlank(jsonStr)) {
            return Result.ok(JSONUtil.toList(jsonStr, ProductType.class));
        }
        // 查询数据库
        List<ProductType> productTypeList = productTypeMapper.selectList(null);
        // 转换为商品分类树
        List<ProductType> toTree =  this.allListToTree(productTypeList, WarehouseConstants.PARENT_ID);
        // 向 redis 中缓存
        stringRedisTemplate.opsForValue().set(RedisConstants.TREE_PRODUCT_ALL_KEY,
                JSONUtil.toJsonStr(toTree),
                RedisConstants.AUTH_TTL,
                TimeUnit.SECONDS);
        return Result.ok(toTree);

    }

    @Override
    public Result checkTypeCode(String typeCode) {
        QueryWrapper<ProductType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type_code", typeCode);
        ProductType productType = productTypeMapper.selectOne(queryWrapper);
        return Result.ok(productType == null);
    }

    @Override
    public Result saveProductType(ProductType productType) {

        // 删除缓存
        stringRedisTemplate.delete(RedisConstants.TREE_PRODUCT_ALL_KEY);

        // 添加
        int insert = productTypeMapper.insert(productType);
        if (insert != 1) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "添加商品类型失败！");
        }
        return Result.ok("添加商品类型成功！");
    }

    @Override
    public Result removeProductTypeById(Integer typeId) {

        // 删除缓存
        stringRedisTemplate.delete(RedisConstants.TREE_PRODUCT_ALL_KEY);
        // 删除数据库记录
        QueryWrapper<ProductType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type_id", typeId)
                    .or()
                    .eq("parent_id", typeId);
        int delete = productTypeMapper.delete(queryWrapper);
        if (delete == 0) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "删除失败！");
        }
        return Result.ok("删除成功！");
    }

    @Override
    public Result updateProductType(ProductType productType) {

        // 判断修改的分类是否存在
        QueryWrapper<ProductType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type_name", productType.getTypeName());
        ProductType one = productTypeMapper.selectOne(queryWrapper);
        if (one != null && !one.getTypeId().equals(productType.getTypeId())) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "商品分类已存在！");
        }

        // 删除缓存
        stringRedisTemplate.delete(RedisConstants.TREE_PRODUCT_ALL_KEY);
        // 修改
        int i = productTypeMapper.updateById(productType);
        if (i != 1) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "修改商品分类失败！");
        }
        return Result.ok("修改商品分类成功！");
    }

    private List<ProductType> allListToTree(List<ProductType> list, Integer parentId) {
        // 创建一级集合
        List<ProductType> firstLevelList = new ArrayList<>();
        // 遍历
        for (ProductType productType : list) {
            // 判断当前元素是否是父级
            if (productType.getParentId().equals(parentId)) {
                firstLevelList.add(productType);
            }
        }
        // 找出下一级
        for (ProductType firstProductType : firstLevelList) {
            // 当前元素成为父级
            List<ProductType> secondLevelList = allListToTree(list, firstProductType.getTypeId());
            firstProductType.setChildProductCategory(secondLevelList);
        }
        return firstLevelList;
    }
}




