package com.oldtree.ptydbhelper.utils;

import com.oldtree.ptydbhelper.core.PoJo;

/**
 * 工具类.
 *
 * @ClassName TableUtils
 * @Author oldTree
 * @Date 2022/4/30
 * @Version 1.0
 */
public class TableUtils {
    /**
     * 获取表名
     * @param cls
     * @return
     */
    public static String getTableName(Class<?> cls){
        PoJo poJo = cls.getDeclaredAnnotation(PoJo.class);
        String value = poJo.value();
        if("".equals(value)){
            value = cls.getSimpleName();
        }
        return value;
    }
}

