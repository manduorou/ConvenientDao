package com.oldtree.convenientdao.core;

import java.lang.annotation.*;

/**
 * 加上Ignore后即忽视模型的字段
 * @ClassName Ignore
 * @Author oldTree
 * @Date 2022/6/18
 * @Version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Ignore {
}
