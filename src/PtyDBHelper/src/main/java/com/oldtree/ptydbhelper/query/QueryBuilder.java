package com.oldtree.ptydbhelper.query;


import com.oldtree.ptydbhelper.config.SqlColumnTypesMapper;
import com.oldtree.ptydbhelper.core.PoJoClassHandler;
import com.oldtree.ptydbhelper.property.Property;
import com.oldtree.ptydbhelper.utils.TableUtils;

import java.util.List;

/**
 * 详细介绍类的情况.
 *
 * @ClassName QueryBuilder
 * @Author oldTree
 * @Date 2022/4/22
 * @Version 1.0
 */
public class QueryBuilder<T> {
    public QueryBuilder() {
    }
    /**
     * 建立对应class的query
     * 只建立select column[0], column[1], column[2]....from *
     * 后面根据操作处理
     * @param cls
     * @return
     */
    public static Query build(Class cls){
        PoJoClassHandler poJoClassHandler = new PoJoClassHandler();
        List<Property> list = poJoClassHandler.createAllProperties(cls);
        StringBuffer head =new StringBuffer();
        StringBuffer constraint = new StringBuffer();
        StringBuffer conditions = new StringBuffer();
        StringBuffer end = new StringBuffer();
        head.append(SqlColumnTypesMapper.sqlMapper.get("select")).append(" ");
        for (Property property : list) {
            head.append(" ").append(property.columnName).append(",");
        }
        int indexOf = head.lastIndexOf(",");
        head.replace(indexOf,indexOf+1,"");
        constraint.append(" ").append(SqlColumnTypesMapper.sqlMapper.get("from"));
        constraint.append(" ").append(TableUtils.getTableName(cls));
        return  new Query(head,constraint,conditions,end,list,cls);
    }
}

