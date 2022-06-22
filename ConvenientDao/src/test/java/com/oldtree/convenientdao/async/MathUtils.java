package com.oldtree.convenientdao.async;

import com.oldtree.convenientdao.utils.ListUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 详细介绍类的情况.
 *
 * @ClassName MathUtls
 * @Author oldTree
 * @Date 2022/6/20
 * @Version 1.0
 */
public class MathUtils {
//    10w数据
    @Test
    public void test1(){
        System.out.println(100000/4);
        System.out.println(25000/4);
        System.out.println(6250/4);
        System.out.println(1562);
    }


    @Test
    public void test2(){
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        List<Integer> list1 = list.subList(1, list.size());
        list1.forEach((e)-> System.out.println(e));
    }

    @Test
    public void test3(){
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        List<Integer> list1 = list.subList(1, 2);
        System.out.println(list);
    }

    @Test
    public void test4(){
        List<Object> list = new ArrayList<>();
        for (int i = 0;i<1000;i++){
            list.add("第"+i);
        }
        List<List> split = ListUtils.cutList(list);
        System.out.println(split.size());
        System.out.println(split);
    }

}

