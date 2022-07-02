package com.oldtree.convenientdao.core;

import java.lang.annotation.*;

/**
 * 申明主键的列名
 * 如果你需要自增，请把autoincrement设为true
 * 如果你不允许主键不给值就能插入（通常为自增id下使用）保持canNull为true即可，如果不允许为null，则设为false，
 * @ClassName PoJoClassHandle
 * @Author oldTree
 * @Date 2022/4/21
 * @Version 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Pk {
    boolean autoincrement() default false;
    boolean canNull() default true;
}
