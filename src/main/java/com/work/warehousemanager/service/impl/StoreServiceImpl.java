package com.work.warehousemanager.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.RedisConstants;
import com.work.warehousemanager.dto.page.Page;
import com.work.warehousemanager.entity.Store;
import com.work.warehousemanager.entity.Unit;
import com.work.warehousemanager.service.StoreService;
import com.work.warehousemanager.mapper.StoreMapper;
import com.work.warehousemanager.vo.StoreCountVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* @author 胡国海
* @description 针对表【store(仓库表)】的数据库操作Service实现
* @createDate 2023-12-05 18:19:36
*/
@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store>
    implements StoreService{

    @Resource
    private StoreMapper storeMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result getStoreList() {
        // 从缓存中获取
        String jsonStr = stringRedisTemplate.opsForValue().get(RedisConstants.ALL_STORE_KEY);
        if (!StrUtil.isBlank(jsonStr)) {
            return Result.ok(JSONUtil.toList(jsonStr, Store.class));
        }
        List<Store> storeList = storeMapper.selectList(null);
        // 向 redis 中缓存
        stringRedisTemplate.opsForValue().set(RedisConstants.ALL_STORE_KEY,
                JSONUtil.toJsonStr(storeList),
                RedisConstants.AUTH_TTL,
                TimeUnit.SECONDS);
        return Result.ok(storeList);
    }

    @Override
    public Result storeService() {

        List<StoreCountVo> list = storeMapper.selectCountVo();
        return Result.ok(list);
    }

    @Override
    public Result getStorePageList(Page page, Store store) {

        // 按条件查询总行数
        Integer count = storeMapper.selectCountByCriteria(store);

        // 按条件分页查询
        List<Store> list = storeMapper.selectListByCriteria(page, store);

        // 组装
        page.setTotalNum(count);
        page.setResultList(list);

        return Result.ok(page);
    }

    @Override
    public List<Store> selectCountByCriteria(Page page, Store store) {
        // 按条件查询
        List<Store> storeList = storeMapper.selectListByCriteria(page, store);
        return storeList;
    }
}




