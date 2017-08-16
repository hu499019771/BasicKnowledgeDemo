package com.chinabluedon.basicknowledgedemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ht
 * @time 2017/8/16  17:50
 * @desc ${TODD}
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewInject {

    int id ();//控件id

    boolean clickable () default false;
}
