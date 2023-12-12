package com.work.warehousemanager.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.RedisConstants;
import com.work.warehousemanager.entity.ProductType;
import com.work.warehousemanager.entity.Supply;
import com.work.warehousemanager.service.SupplyService;
import com.work.warehousemanager.mapper.SupplyMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* @author 胡国海
* @description 针对表【supply(供货商)】的数据库操作Service实现
* @createDate 2023-12-05 18:19:47
*/
@Service
public class SupplyServiceImpl extends ServiceImpl<SupplyMapper, Supply>
    implements SupplyService{

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private SupplyMapper supplyMapper;

    @Override
    public Result getSupplyList() {
        // 从缓存中获取
        String jsonStr = stringRedisTemplate.opsForValue().get(RedisConstants.ALL_SUPPLY_KEY);
        if (!StrUtil.isBlank(jsonStr)) {
            return Result.ok(JSONUtil.toList(jsonStr, Supply.class));
        }
        // 查询数据库
        List<Supply> supplyList = supplyMapper.selectList(null);
        // 向 redis 中缓存
        stringRedisTemplate.opsForValue().set(RedisConstants.ALL_SUPPLY_KEY,
                JSONUtil.toJsonStr(supplyList),
                RedisConstants.AUTH_TTL,
                TimeUnit.SECONDS);
        return Result.ok(supplyList);

    }
}




