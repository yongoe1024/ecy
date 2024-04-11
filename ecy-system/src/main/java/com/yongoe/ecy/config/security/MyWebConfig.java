package com.yongoe.ecy.config.security;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

/**
 * 拦截器、添加自定义视图解析器、配置资源处理器等
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Value("${ecy.file-save-path}")
    private String fileSavePath;
    @Resource
    private LoginInterceptor loginInterceptor;
    @Resource
    private AuthInterceptor authInterceptor;


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/index.html");
    }

    /**
     * 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] p = new String[]{
                "/", "/login", "/oauth/**", "/captcha/**",
                "/forget", "/register",
                "/file/**",
                "/webjars/**", "/v3/**", "/doc.html",
                "/index.html**", "/static/**", "/favicon.ico"
        };
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(p);
        registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns(p);
    }

    /**
     * 跨域
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //1,允许任何来源
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        //2,允许任何请求头
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        //3,允许任何方法
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        //4,允许凭证
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    /**
     * 本地文件映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = "file:" + fileSavePath;
        registry.addResourceHandler("/file/**")
                .addResourceLocations(path + "/");
    }

}