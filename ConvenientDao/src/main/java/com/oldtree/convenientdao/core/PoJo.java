package com.oldtree.convenientdao.core;

import java.lang.annotation.*;

/**
 * 如果你在类型名上面加入了@PoJo注解，则表示在数据库映射建立一个同名的表。
 * 当然你也可以给value一个属性，表示当前模型对应在数据库内部的表名
 * @ClassName Default
 * @Author oldTree
 * @Date 2022/6/20
 * @Version 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface PoJo {
    String tableName() default "";
}
