package com.oldtree.ptydbhelper.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 详解.
 *
 * @ClassName NotNull
 * @Author oldTree
 * @Date 2022/4/20
 * @Version 1.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotNull {
}
