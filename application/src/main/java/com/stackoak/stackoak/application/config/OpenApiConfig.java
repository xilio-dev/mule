package com.stackoak.stackoak.application.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi commonApi() {
        return GroupedOpenApi.builder()
                .group("通用接口")
                // .pathsToMatch(portal_api_prefix)
                .packagesToScan("com.stackoak.stackoak.application.controller.common")
                .build();
    }
    @Bean
    public GroupedOpenApi portalApi() {
        return GroupedOpenApi.builder()
                .group("门户接口")
               // .pathsToMatch(portal_api_prefix)
                .packagesToScan("com.stackoak.stackoak.application.controller.portal")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("后端接口")
                .packagesToScan("com.stackoak.stackoak.application.controller.admin")
               // .pathsToMatch(admin_api_prefix)
                .build();
    }

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
