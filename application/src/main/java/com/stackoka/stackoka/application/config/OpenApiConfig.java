package com.stackoka.stackoka.application.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    /**
     * 默认 API 分组，包含前端和后端接口
     */
    @Bean
    public GroupedOpenApi defaultApi() {
        return GroupedOpenApi.builder()
                .group("default")  // 分组名称
                .packagesToScan("com.stackoka.stackoka.application.controller") // 匹配路径，可以根据实际情况调整
                .build();
    }

    /**
     * 前端 API 分组，只包含前端接口
     */
    @Bean
    public GroupedOpenApi webApi() {
        return GroupedOpenApi.builder()
                .group("前端接口")  // 分组名称
               // .pathsToMatch("/api/web/**")  // 前端接口路径
                .packagesToScan("com.stackoka.stackoka.application.controller")
                .build();
    }

//    /**
//     * 后端 API 分组，只包含后端接口
//     */
//    @Bean
//    public GroupedOpenApi adminApi() {
//        return GroupedOpenApi.builder()
//                .group("后端接口")  // 分组名称
//                .pathsToMatch("/api/admin/**")  // 后端接口路径
//                .build();
//    }

    /**
     * 定制全局信息
     */
    @Bean
    public OpenApiCustomizer customOpenApiCustomizer() {
        return openApi -> openApi.info(
                new Info()
                        .title("API 文档")
                        .description("API 文档描述")
                        .version("1.0.0")
                        .contact(new Contact().name("API Team").email("stackoak@163.com"))
        );
    }
}
