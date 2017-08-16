package com.chinabluedon.basicknowledgedemo.annotation.project;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 胡腾
 * @time 2017/8/16  22:05
 * @desc ${TODD}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    String value();//字段名称
}
