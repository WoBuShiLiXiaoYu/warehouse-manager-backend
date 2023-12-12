package com.work.warehousemanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.WarehouseConstants;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.dto.user.CurrentUser;
import com.work.warehousemanager.entity.BuyList;
import com.work.warehousemanager.entity.InStore;
import com.work.warehousemanager.mapper.InStoreMapper;
import com.work.warehousemanager.service.BuyListService;
import com.work.warehousemanager.mapper.BuyListMapper;
import com.work.warehousemanager.utils.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author 胡国海
* @description 针对表【buy_list(采购单)】的数据库操作Service实现
* @createDate 2023-12-06 19:59:36
*/
@Service
public class BuyListServiceImpl extends ServiceImpl<BuyListMapper, BuyList>
    implements BuyListService{

    @Resource
    private BuyListMapper buyListMapper;
    @Resource
    private InStoreMapper inStoreMapper;

    @Resource
    private TokenUtils tokenUtils;

    @Override
    public Result savePurchase(BuyList buyList) {
        // 设置参数
        //buyList.setFactBuyNum(buyList.getBuyNum());
        buyList.setBuyTime(new Date());
        buyList.setIsIn(WarehouseConstants.PRODUCT_NOT_IN);

        // 添加
        int insert = buyListMapper.insert(buyList);
        if (insert != 1) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "添加采购单失败！");
        }
        return Result.ok("添加采购单成功！");

    }

    @Override
    public Result getPageList(Page page, BuyList buyList) {

        // 按条件查询总行数
        Integer count = buyListMapper.selectCountByCriteria(buyList);

        // 按条件分页查询
        List<BuyList> list = buyListMapper.selectListByCriteria(page, buyList);

        // 组装 page
        page.setTotalNum(count);
        page.setResultList(list);
        return Result.ok(page);
    }

    @Override
    public Result removeBuyListById(Integer buyId) {

        int i = buyListMapper.deleteById(buyId);
        if (i != 1) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "删除采购单失败！");
        }
        return Result.ok("删除采购单成功！");
    }

    @Override
    public Result updatePurchase(BuyList buyList) {

        int update = buyListMapper.updateById(buyList);
        if (update != 1) {
            return Result.err(Result.CODE_ERR_UNLOGINED, "修改采购单失败！");
        }
        return Result.ok("修改采购单成功！");
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public Result purchaseToInStore(BuyList buyList, String token) {

        // 解析 token
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);

        // 将采购单 is_in 修改为 1
        buyList.setIsIn(WarehouseConstants.PRODUCT_IN);
        int i = buyListMapper.updateById(buyList);
        if (i == 1) {
            // 生成入库单，入库单 is_in 为 0
            InStore inStore = new InStore();
            inStore.setCreateBy(currentUser.getUserId());
            inStore.setInNum(buyList.getFactBuyNum());
            inStore.setStoreId(buyList.getStoreId());
            inStore.setCreateTime(new Date());
            inStore.setProductId(buyList.getProductId());
            inStore.setIsIn(WarehouseConstants.PRODUCT_NOT_IN);
            int insert = inStoreMapper.insert(inStore);
            if (insert == 1) {
                return Result.ok("生成入库表成功！");
            }
        }
        return Result.err(Result.CODE_ERR_UNLOGINED, "生成入库表失败！");
    }
}




