package com.oldtree.convenientdao.utils;

import com.oldtree.convenientdao.core.PoJo;
import com.oldtree.convenientdao.exception.PoJoException;

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
     *
     * @param cls
     * @return
     */
    public static String getTableName(Class<?> cls) throws PoJoException {
        PoJo poJo = cls.getDeclaredAnnotation(PoJo.class);
        if (null == poJo) {
            throw new PoJoException("无法建立，请传入一个被@PoJo注解的类", cls);
        }
        String value = poJo.tableName();
        if ("".equals(value)) {
            value = cls.getSimpleName();
            return value;
        }
        return value;
    }
}

