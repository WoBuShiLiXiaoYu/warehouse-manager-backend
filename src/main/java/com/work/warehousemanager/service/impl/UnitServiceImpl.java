package com.work.warehousemanager.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.RedisConstants;
import com.work.warehousemanager.entity.ProductType;
import com.work.warehousemanager.entity.Supply;
import com.work.warehousemanager.entity.Unit;
import com.work.warehousemanager.service.UnitService;
import com.work.warehousemanager.mapper.UnitMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* @author 胡国海
* @description 针对表【unit(规格单位表)】的数据库操作Service实现
* @createDate 2023-12-05 18:19:57
*/
@Service
public class UnitServiceImpl extends ServiceImpl<UnitMapper, Unit>
    implements UnitService{

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private UnitMapper unitMapper;

    @Override
    public Result getUnitList() {

        // 从缓存中获取
        String jsonStr = stringRedisTemplate.opsForValue().get(RedisConstants.ALL_UNIT_KEY);
        if (!StrUtil.isBlank(jsonStr)) {
            return Result.ok(JSONUtil.toList(jsonStr, Unit.class));
        }
        // 查询数据库
        List<Unit> unitList = unitMapper.selectList(null);
        // 向 redis 中缓存
        stringRedisTemplate.opsForValue().set(RedisConstants.ALL_UNIT_KEY,
                JSONUtil.toJsonStr(unitList),
                RedisConstants.AUTH_TTL,
                TimeUnit.SECONDS);
        return Result.ok(unitList);
    }
}




