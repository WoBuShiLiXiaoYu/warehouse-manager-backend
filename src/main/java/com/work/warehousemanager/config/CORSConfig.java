package com.work.warehousemanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("==========addCorsMappings==========");

        // addMapping：处理请求地址，拦截这些地址，使用跨域处理
        registry.addMapping("/**")
                // allowedOriginPatterns：可跨域的域名
                .allowedOrigins("*")
                //allowedMethods：请求方式
                .allowedMethods("POST", "GET", "HEAD", "PUT", "DELETE", "OPTIONS")
                // allowCredentials：是否发送 cookie
                .allowCredentials(true)
                // maxAge：最大时间
                .maxAge(3600)
                // allowedHeaders：支持跨域的请求头
                .allowedHeaders("*");

    }
}
