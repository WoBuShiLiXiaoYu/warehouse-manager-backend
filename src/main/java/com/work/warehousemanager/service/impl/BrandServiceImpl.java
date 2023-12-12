package com.work.warehousemanager.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.RedisConstants;
import com.work.warehousemanager.entity.Brand;
import com.work.warehousemanager.entity.Store;
import com.work.warehousemanager.service.BrandService;
import com.work.warehousemanager.mapper.BrandMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* @author 胡国海
* @description 针对表【brand(品牌)】的数据库操作Service实现
* @createDate 2023-12-05 18:19:08
*/
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand>
    implements BrandService{

    @Resource
    private BrandMapper brandMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result getBrandList() {
        // 从缓存中获取
        String jsonStr = stringRedisTemplate.opsForValue().get(RedisConstants.ALL_BRAND_KEY);
        if (!StrUtil.isBlank(jsonStr)) {
            return Result.ok(JSONUtil.toList(jsonStr, Brand.class));
        }
        List<Brand> brandList = brandMapper.selectList(null);
        // 向 redis 中缓存
        stringRedisTemplate.opsForValue().set(RedisConstants.ALL_BRAND_KEY,
                JSONUtil.toJsonStr(brandList),
                RedisConstants.AUTH_TTL,
                TimeUnit.SECONDS);
        return Result.ok(brandList);
    }
}




