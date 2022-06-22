package com.oldtree.convenientdao.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO 通过value的sql语句执行返回一个Cursor游标
 * @ClassName Execute
 * @Author oldTree
 * @Date 2022/6/20
 * @Version 1.0.5
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Execute {
    String value();
}
