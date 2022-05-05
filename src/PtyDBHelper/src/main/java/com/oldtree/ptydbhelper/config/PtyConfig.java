package com.oldtree.ptydbhelper.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置注解
 *
 * @ClassName PtyConfig
 * @Author oldTree
 * @Date 2022/4/28
 * @Version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PtyConfig {
    String dbName() default "ptySqlite.db";
    int dbVersion() default 1;
    Class[] pojoClassArray() default {};
}
