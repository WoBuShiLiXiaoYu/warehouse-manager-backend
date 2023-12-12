/*
package com.work.warehousemanager.globafilter;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.WarehouseConstants;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LoginGlobalFilter implements GlobalFilter {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final List<String> WHITE_LIST = Arrays.asList("/login", "/captcha/captchaImage");
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求对象和响应对象
        ServerHttpRequest req = exchange.getRequest();
        ServerHttpResponse resp = exchange.getResponse();
        HttpServletRequest request = (HttpServletRequest) req;

        // 白名单直接放行
        String servletPath = request.getServletPath();
        if (WHITE_LIST.contains(servletPath)) {
            return chain.filter(exchange);
        }
        // 判断请求是否携带 token 和 token 是否有效
        String token = request.getHeader(WarehouseConstants.HEADER_TOKEN_NAME);
        if (!StrUtil.isBlank(token) && stringRedisTemplate.hasKey(token)) {
            return chain.filter(exchange);
        }
        // 未携带 token 拦截
        resp.getHeaders().set("content-type", "application/json;charset=utf-8");
        Map<String, Object> map = new HashMap<>();
        map.put("code", Result.CODE_ERR_UNLOGINED);
        map.put("msg", "您尚未登录！");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = new byte[0];
        try {
            bytes = objectMapper.writeValueAsBytes(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        DataBuffer buffer = resp.bufferFactory().wrap(bytes);
        return resp.writeWith(Mono.just(buffer));
    }
}
*/
