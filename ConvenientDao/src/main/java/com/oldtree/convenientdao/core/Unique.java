package com.oldtree.convenientdao.core;

import java.lang.annotation.*;

/**
 * 约束注解，表示当前属性只允许唯一值出现，本注解不能跟@Pk注解搭配使用
 * 可以用于除了@Pk注解的多处使用，并且可以指定约束名，后续根据自己需要
 * 进行对表结构的操作！
 * @ClassName Unique
 * @Author oldTree
 * @Date 2022/4/24
 * @Version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Unique {
    String constraintName() default "";
}
