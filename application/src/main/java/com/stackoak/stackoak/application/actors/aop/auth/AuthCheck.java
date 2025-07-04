package com.stackoak.stackoak.application.actors.aop.auth;

import com.stackoak.stackoak.common.data.article.UserRoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    /**
     *
     *
     * @return
     */
    UserRoleEnum[] roles() default {};

}

