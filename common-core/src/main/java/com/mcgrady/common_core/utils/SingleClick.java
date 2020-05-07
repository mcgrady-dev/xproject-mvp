package com.mcgrady.common_core.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防止按钮连续点击的注解
 * Created by mcgrady on 2020/4/29.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SingleClick {

    //点击间隔时间
    long value() default 1500;
}
