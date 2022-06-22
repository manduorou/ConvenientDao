package com.oldtree.convenientdao.async;

import com.sun.istack.internal.NotNull;
import org.junit.Test;

/**
 * 详细介绍类的情况.
 *
 * @ClassName OtherTest
 * @Author oldTree
 * @Date 2022/6/20
 * @Version 1.0
 */
public class OtherTest {


    @Test
    public void test1() throws ClassNotFoundException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Class<?> aClass = classLoader.loadClass("com.oldtree.convenientdao.core.Dao");
        System.out.println(aClass.getName());
    }


}

