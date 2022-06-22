package com.oldtree.convenientdao.core;

import java.lang.annotation.*;

/**
 * 表示是一个dao的注解
 * values的值为false时，默认不支持(伪)热部署
 * 将values的值改为true时，则支持(伪)热部署
 * @ClassName Dao
 * @Author oldTree
 * @Date 2022/6/20
 * @Version 1.0.5
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Dao {
    boolean value() default false;
}
