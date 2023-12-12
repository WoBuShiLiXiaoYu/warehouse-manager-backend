
package com.work.warehousemanager.config;


import com.work.warehousemanager.globafilter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import javax.servlet.Filter;


@Configuration
public class ServletConfig {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        // 创建 FilterRegistrationBean 对象
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        // 创建自定义过滤器对象
        LoginCheckFilter loginCheckFilter = new LoginCheckFilter(stringRedisTemplate);
        filterFilterRegistrationBean.setFilter(loginCheckFilter);
        // 添加拦截请求
        filterFilterRegistrationBean.addUrlPatterns("/*");
        return filterFilterRegistrationBean;
    }

}

