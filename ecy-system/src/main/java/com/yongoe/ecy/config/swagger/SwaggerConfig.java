package com.yongoe.ecy.config.swagger;

import cn.hutool.core.util.RandomUtil;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Swagger配置
 *
 * @author yongoe
 * @since 2023/1/1
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
        return openApi -> {

            if (openApi.getTags() != null) {
                openApi.getTags().forEach(tag -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("x-order", RandomUtil.randomInt(0, 100));
                    tag.setExtensions(map);
                });
            }
            if (openApi.getPaths() != null) {
                openApi.addExtension("x-test123", "333");
                openApi.getPaths().addExtension("x-abb", RandomUtil.randomInt(1, 100));
            }
        };
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("springdoc-hello")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                        .title("ecy系统API")
                        .version("1.0")
                        .description("ecy管理系统"))
                .components(new Components()
                        .addSecuritySchemes("Authorization",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("token")
                                        .bearerFormat("")));

    }

}