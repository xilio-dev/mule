package com.stackoak.stackoak.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * API接口前缀自动配置
 */
public class AutoApiPrefixMapping extends RequestMappingHandlerMapping {
    @Value("${stackoak.api.portal.prefix}")
    private String portalPrefix;
    @Value("")
    private String adminPrefix;
    @Value("${stackoak.api.portal.package}")
    private String portalPackage;
    @Value("${stackoak.api.admin.package}")
    private String adminPackage;

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo mappingInfo = super.getMappingForMethod(method, handlerType);
        if (Objects.nonNull(mappingInfo)) {
            String packageName = handlerType.getPackageName();
            if (adminPackage.equals(packageName)) {
                return RequestMappingInfo.paths(adminPrefix).build().combine(mappingInfo);
            }
            if (portalPackage.equals(packageName)) {
                return RequestMappingInfo.paths(portalPrefix).build().combine(mappingInfo);
            }
            return mappingInfo;
        }
        return mappingInfo;
    }
}
