package com.oldtree.convenientdao.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 本注解表示，被本注解注释的属性，不能出现null值的情况，如果出现则会抛出异常
 * 本注解通常加上@Default使用,具体查看{@linkplain com.oldtree.convenientdao.core.Default}
 * @ClassName NotNull
 * @Author oldTree
 * @Date 2022/4/20
 * @Version 1.0.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotNull {
}
