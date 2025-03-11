package com.stackoak.stackoak.application.actors.cut;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(FieldFilters.class)
public @interface FieldFilter {

    /**
     * 需要过滤的类型
     */
    Class<?> type();

    /**
     * 只包含的字段
     */
    String[] include() default {};

    /**
     * 排除的字段
     */
    String[] exclude() default {};
}
