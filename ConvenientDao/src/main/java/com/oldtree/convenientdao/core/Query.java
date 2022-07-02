package com.oldtree.convenientdao.core;

import java.lang.annotation.*;

/**
 * 用于方法
 * value属性是关于查询的sql语句
 * @ClassName Query
 * @Author oldTree
 * @Date 2022/6/20
 * @Version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Query {
    String value();
}
