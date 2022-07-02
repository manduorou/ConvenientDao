package com.oldtree.convenientdao.core;

import java.lang.annotation.*;

/**
 * TODO 通过value的sql语句执行返回一个Cursor游标
 * @ClassName Execute
 * @Author oldTree
 * @Date 2022/6/20
 * @Version 1.0.5
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Execute {
    String value();
}
