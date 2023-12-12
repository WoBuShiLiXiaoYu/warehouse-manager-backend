package com.work.warehousemanager.service.impl;

import com.google.code.kaptcha.Producer;
import com.work.warehousemanager.service.LoginService;
import com.work.warehousemanager.constants.RedisConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource(name = "captchaProducer")
    private Producer producer;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public BufferedImage captchaImage() {
        // 获取验证码图片文件
        String text = producer.createText();
        // 将验证码文件生成验证码图片
        BufferedImage image = producer.createImage(text);
        // 将验证码保存到 Redis 中，并设置过期时间
        stringRedisTemplate.opsForValue().set(RedisConstants.LOGIN_CODE_KEY + text, text, RedisConstants.LOGIN_CODE_TTL, TimeUnit.SECONDS);
        // 返回 BufferedImage 对象
        return image;
    }
}
