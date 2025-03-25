package com.stackoak.stackoak.application.actors.lucene;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FullTextField {
    boolean store() default true; // 是否存储字段值
    boolean highlight() default false; // 是否高亮
}
