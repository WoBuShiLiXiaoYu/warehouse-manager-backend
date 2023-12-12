package com.work.warehousemanager.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.RedisConstants;
import com.work.warehousemanager.entity.Place;
import com.work.warehousemanager.entity.ProductType;
import com.work.warehousemanager.entity.Supply;
import com.work.warehousemanager.service.PlaceService;
import com.work.warehousemanager.mapper.PlaceMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* @author 胡国海
* @description 针对表【place(产地)】的数据库操作Service实现
* @createDate 2023-12-05 18:20:12
*/
@Service
public class PlaceServiceImpl extends ServiceImpl<PlaceMapper, Place>
    implements PlaceService{

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private PlaceMapper placeMapper;

    @Override
    public Result getPlaceList() {
        // 从缓存中获取
        String jsonStr = stringRedisTemplate.opsForValue().get(RedisConstants.ALL_PLACE_KEY);
        if (!StrUtil.isBlank(jsonStr)) {
            return Result.ok(JSONUtil.toList(jsonStr, Place.class));
        }
        // 查询数据库
        List<Place> placeList = placeMapper.selectList(null);
        // 向 redis 中缓存
        stringRedisTemplate.opsForValue().set(RedisConstants.ALL_PLACE_KEY,
                JSONUtil.toJsonStr(placeList),
                RedisConstants.AUTH_TTL,
                TimeUnit.SECONDS);
        return Result.ok(placeList);

    }
}




