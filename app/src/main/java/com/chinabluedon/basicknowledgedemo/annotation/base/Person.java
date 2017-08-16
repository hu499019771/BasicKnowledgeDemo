package com.chinabluedon.basicknowledgedemo.annotation.base;

/**
 * @author 胡腾
 * @time 2017/8/16  20:42
 * @desc ${TODD}
 */
@Description("i am class annotation")
public abstract class Person {

    /**
     * 名称
     *
     * @return 返回为年龄, 类型为字符串
     */
    @Description("i am method annotation")
    public abstract String name();

    /**
     * 年龄
     *
     * @return 返回年龄, 类型为int
     */
    public abstract int age();

    /**
     * 已过时方法,唱歌
     */
    @Deprecated
    public abstract void sing();
}
