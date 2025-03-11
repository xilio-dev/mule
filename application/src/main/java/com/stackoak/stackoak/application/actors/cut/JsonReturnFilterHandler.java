package com.stackoak.stackoak.application.actors.cut;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Objects;

@SuppressWarnings("all")
public class JsonReturnFilterHandler implements HandlerMethodReturnValueHandler {

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), FieldFilters.class)
                || returnType.hasMethodAnnotation(FieldFilters.class)
                || AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), FieldFilter.class)
                || returnType.hasMethodAnnotation(FieldFilter.class);
    }

    @Override
    public void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);/*最后处理类*/
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        Annotation[] annotations = returnType.getMethodAnnotations();
        ResponseJsonFilterSerializer jsonFilterSerializer = new ResponseJsonFilterSerializer();
        Arrays.asList(annotations).forEach(annotation -> {
            if (annotation instanceof FieldFilter json) {
                jsonFilterSerializer.filter(json.type(), json.include(), json.exclude());
            } else if (annotation instanceof FieldFilters) {
                jsonFilterSerializer.filter((FieldFilters) annotation);
            }

        });

        Objects.requireNonNull(response).setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String json = jsonFilterSerializer.toJson(returnValue);
        response.getWriter().write(json);
    }
}
