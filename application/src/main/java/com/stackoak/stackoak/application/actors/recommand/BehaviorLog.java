package com.stackoak.stackoak.application.actors.recommand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BehaviorLog {
    String businessId(); // 业务ID
    BehaviorType type();          // 行为类型
}
