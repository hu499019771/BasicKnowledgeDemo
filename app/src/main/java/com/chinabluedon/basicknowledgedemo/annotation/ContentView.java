package com.chinabluedon.basicknowledgedemo.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ht
 * @time 2017/8/16  17:38
 * @desc ${TODD}
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentView {
    int id();//布局xml文件的id
}
