package com.work.warehousemanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.WarehouseConstants;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.InStore;
import com.work.warehousemanager.entity.Product;
import com.work.warehousemanager.mapper.ProductMapper;
import com.work.warehousemanager.service.InStoreService;
import com.work.warehousemanager.mapper.InStoreMapper;
import com.work.warehousemanager.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 胡国海
* @description 针对表【in_store(入库单)】的数据库操作Service实现
* @createDate 2023-12-07 11:13:08
*/
@Service
public class InStoreServiceImpl extends ServiceImpl<InStoreMapper, InStore>
    implements InStoreService{

    @Resource
    private InStoreMapper inStoreMapper;
    @Resource
    private ProductMapper productMapper;

    @Override
    public Result inStorePageList(Page page, InStore inStore) {

        // 统计总行数
        Integer count = inStoreMapper.selectCountByCriteria(inStore);

        // 按条件分页查询
        List<InStore> list = inStoreMapper.selectListByCriteria(page, inStore);

        // 组装
        page.setTotalNum(count);
        page.setResultList(list);

        return Result.ok(page);

    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public Result confirm(InStore inStore) {

        // 修改入库单信息
        inStore.setIsIn(WarehouseConstants.PRODUCT_IN);
        int update = inStoreMapper.updateById(inStore);
        if (update == 1) {
            // 修改商品库存
            QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("product_id", inStore.getProductId());
            Product product = productMapper.selectOne(queryWrapper);
            product.setProductInvent(product.getProductInvent() + inStore.getInNum());
            int i = productMapper.updateById(product);
            if (i == 1) {
                return Result.ok("入库成功！");
            }
        }
        return Result.err(Result.CODE_ERR_UNLOGINED, "入库失败！");
    }
}




