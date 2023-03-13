package com.yongoe.ecy.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

/**
 * 静态资源映射
 * 解决跨域
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private AuthInterceptor authInterceptor;

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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String fileSavePath = System.getProperty("user.dir");
        registry.addResourceHandler("/file/**")
                .addResourceLocations("file:" + fileSavePath + "/ecy-file/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //静态资源不拦截
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(
                "/login", "/logout", "/forget", "/register", "/captcha/**",
                "/swagger**/**", "/webjars/**", "/v3/**", "/doc.html");
        registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns(
                "/login", "/logout", "/forget", "/register", "/captcha/**",
                "/swagger**/**", "/webjars/**", "/v3/**", "/doc.html");
    }
//    不好使
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        //添加映射路径
//        registry.addMapping("/*")
//                .allowCredentials(true)
//                .allowedOriginPatterns("*")
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedHeaders("*")
//                .exposedHeaders("*");
//    }
}