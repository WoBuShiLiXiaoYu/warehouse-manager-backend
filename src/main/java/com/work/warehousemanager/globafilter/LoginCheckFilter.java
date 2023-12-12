package com.work.warehousemanager.globafilter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.work.warehousemanager.common.Result;
import com.work.warehousemanager.constants.RedisConstants;
import com.work.warehousemanager.constants.WarehouseConstants;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class LoginCheckFilter implements Filter {

    private static final List<String> WHITE_LIST = Arrays.asList("/login", "/captcha/captchaImage", "/logout", "/product/img-upload");

    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        // 获取 request、response 对象
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String servletPath = request.getServletPath();
        // 判断是否为白名单，是则返回
        if (WHITE_LIST.contains(servletPath) || servletPath.contains("/img/upload")) {
            filterChain.doFilter(request, response);
            return;
        }
        // 获取请求头判断是否有 token 和 token 是否有效
        String token = request.getHeader(WarehouseConstants.HEADER_TOKEN_NAME);
        System.out.println(!StrUtil.isBlank(token));
        if (!StrUtil.isBlank(token) && stringRedisTemplate.hasKey(RedisConstants.LOGIN_TOKEN_KEY + token)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 拦截
        Result result = Result.err(Result.CODE_ERR_UNLOGINED, "您尚未登录！");
        String jsonStr = JSONUtil.toJsonStr(result);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(jsonStr);
        out.flush();
        out.close();
    }

    public LoginCheckFilter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }
}
