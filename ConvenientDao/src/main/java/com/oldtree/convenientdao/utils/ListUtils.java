package com.oldtree.convenientdao.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 详细介绍类的情况.
 *
 * @ClassName ListUtils
 * @Author oldTree
 * @Date 2022/6/20
 * @Version 1.0.0.5
 */
public class ListUtils {
    private static Integer MAX_SEND = 25000;

    /**
     * 分割list集合
     * @param list
     * @return
     */
    public static List<List>  cutList(List list){
        int size = list.size();
        if(size<=6250){
            MAX_SEND = 1562;
        }else if(size<=25000){
            MAX_SEND = 6250;
        }else if(size<=100000){
            MAX_SEND = 25000;
        }else{
            MAX_SEND = 25000;
        }
        return listSplit(list);
    }
    private static List<List> listSplit(List list){
        int limit = (list.size() + MAX_SEND - 1) / MAX_SEND;
        //使用流遍历操作
        List<List> lists = new ArrayList<>();
        Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
            lists.add((List) list.stream().skip(i * MAX_SEND).limit(MAX_SEND).collect(Collectors.toList()));
        });
        return lists;
    }


}

