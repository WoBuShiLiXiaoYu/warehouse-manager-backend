package com.work.warehousemanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.WarehouseConstants;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.dto.user.CurrentUser;
import com.work.warehousemanager.entity.OutStore;
import com.work.warehousemanager.entity.Product;
import com.work.warehousemanager.mapper.ProductMapper;
import com.work.warehousemanager.service.OutStoreService;
import com.work.warehousemanager.mapper.OutStoreMapper;
import com.work.warehousemanager.utils.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author 胡国海
* @description 针对表【out_store(出库单)】的数据库操作Service实现
* @createDate 2023-12-06 20:38:44
*/
@Service
public class OutStoreServiceImpl extends ServiceImpl<OutStoreMapper, OutStore>
    implements OutStoreService{

    @Resource
    private OutStoreMapper outStoreMapper;
    @Resource
    private ProductMapper productMapper;

    @Resource
    private TokenUtils tokenUtils;

    @Override
    public Result saveOutStore(OutStore outStore, String token) {
        // 解析 token
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        outStore.setCreateBy(currentUser.getUserId());
        outStore.setOutPrice(outStore.getSalePrice());
        outStore.setCreateTime(new Date());
        outStore.setIsOut(WarehouseConstants.PRODUCT_NOT_OUT);

        int insert = outStoreMapper.insert(outStore);
        if (insert != 1) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "添加出库单失败！");
        }
        return Result.ok("添加出库单成功！");
    }

    @Override
    public Result outStorePageList(Page page, OutStore outStore) {

        // 按条件查询总行数
        Integer count = outStoreMapper.selectCountByCriteria(outStore);
        // 按条件分页查询
        List<OutStore> list = outStoreMapper.selectListByCriteria(page, outStore);

        // 组装 page
        page.setResultList(list);
        page.setTotalNum(count);
        return Result.ok(page);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public Result outStoreConfirm(OutStore outStore) {

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", outStore.getProductId());
        Product product = productMapper.selectOne(queryWrapper);
        if (product.getProductInvent() < outStore.getOutNum()) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "商品库存不足！");
        }
        // 修改出库单
        outStore.setIsOut(WarehouseConstants.PRODUCT_IS_OUT);
        int i = outStoreMapper.updateById(outStore);
        if (i == 1) {
            // 修改商品库存
            product.setProductInvent(product.getProductInvent() - outStore.getOutNum());
            queryWrapper.gt("product_invent", 0);
            int update = productMapper.update(product, queryWrapper);
            if (update == 1) {
                return Result.ok("出库成功！");
            }
        }
        return Result.err(Result.CODE_ERR_UNLOGINED, "出库失败!");
    }
}




