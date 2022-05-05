package com.oldtree.ptydbhelper.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 详解.
 *
 * @ClassName Unique
 * @Author oldTree
 * @Date 2022/4/24
 * @Version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Unique {
    String constraintName() default "";
}
