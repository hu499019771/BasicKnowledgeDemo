package com.chinabluedon.basicknowledgedemo.annotation.project;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 胡腾
 * @time 2017/8/16  22:04
 * @desc ${TODD}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {

    String value();//表名
}
