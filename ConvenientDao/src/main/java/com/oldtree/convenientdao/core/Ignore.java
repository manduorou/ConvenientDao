package com.oldtree.convenientdao.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加上Ignore后即忽视模型的字段
 * @ClassName Ignore
 * @Author oldTree
 * @Date 2022/6/18
 * @Version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Ignore {
}
