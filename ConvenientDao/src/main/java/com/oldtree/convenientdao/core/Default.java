package com.oldtree.convenientdao.core;

import java.lang.annotation.*;

/**
 * 如果你在列名设置了@NotNull {@linkplain com.oldtree.convenientdao.core.NotNull}
 * 前提下，你担心出现空指针异常，可以加上本注解，然后提供一个默认值即可。
 * @ClassName Default
 * @Author oldTree
 * @Date 2022/6/20
 * @Version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Default {
    String value();
}
