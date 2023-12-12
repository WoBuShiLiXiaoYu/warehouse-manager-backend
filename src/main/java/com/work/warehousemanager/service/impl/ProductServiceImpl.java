package com.work.warehousemanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.WarehouseConstants;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.dto.user.CurrentUser;
import com.work.warehousemanager.entity.Product;
import com.work.warehousemanager.mapper.ProductMapper;
import com.work.warehousemanager.service.ProductService;
import com.work.warehousemanager.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author 胡国海
* @description 针对表【product(商品表)】的数据库操作Service实现
* @createDate 2023-12-05 18:33:18
*/
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
    implements ProductService{

    @Resource
    private ProductMapper productMapper;
    @Resource
    private TokenUtils tokenUtils;

    @Value("${file.access-path}")
    private String fileAccessPath;

    @Override
    public Result productPageList(Product product, Page page) {
        // 按条件查询商品集合总行数
        Integer count = productMapper.getProductCountByCriteria(product);

        // 按条件分页查询商品集合
        List<Product> productList = productMapper.getProductListByCriteria(product, page);

        // 组装 page
        page.setTotalNum(count);
        page.setResultList(productList);

        return Result.ok(page);
    }

    @Override
    public Result saveProduct(Product product, String token) {

        // 查询是否有相同编号
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_num", product.getProductNum());
        Product selectOne = productMapper.selectOne(queryWrapper);
        if (selectOne != null) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "存在相同产品编号，添加失败！");
        }
        // 解析 token
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        // 设置 product
        product.setCreateBy(currentUser.getUserId());
        String img = fileAccessPath + product.getImgs();
        product.setImgs(img);
        product.setUpDownState(WarehouseConstants.PRODUCT_STATE_DOWN);
        product.setCreateTime(new Date());

        // 添加
        int insert = productMapper.insert(product);
        if (insert != 1) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "添加失败！");
        }
        return Result.ok("添加成功！");
    }

    @Override
    public Result updateProductState(Product product) {
        int i = productMapper.updateById(product);
        if (i != 1) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "修改商品状态失败！");
        }
        return Result.ok("修改商品状态成功！");

    }

    @Override
    public Result deleteProductList(List<Integer> ids) {

        int i = productMapper.deleteBatchIds(ids);
        if (i != ids.size()) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "删除商品失败！");
        }
        return Result.ok("删除商品成功！");
    }

    @Override
    public Result updateProduct(Product product, String token) {

        // 判断修改商品编号与原商品是否一致
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_num", product.getProductNum());
        Product one = productMapper.selectOne(queryWrapper);
        if (one != null && !one.getProductId().equals(product.getProductId())) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "修改商品型号已存在！");
        }
        // 判断图片是否修改
        if (!product.getImgs().contains(fileAccessPath)) {
            product.setImgs(fileAccessPath + product.getImgs());
        }
        // 修改
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        product.setUpdateTime(new Date());
        product.setUpdateBy(currentUser.getUserId());
        UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("product_id", product.getProductId());
        int update = productMapper.update(product, updateWrapper);
        if (update <= 0 ) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "修改失败！");
        }
        return Result.ok("修改成功！");
    }
}




