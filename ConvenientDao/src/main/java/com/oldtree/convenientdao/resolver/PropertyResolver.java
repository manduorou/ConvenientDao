package com.oldtree.convenientdao.resolver;

import com.oldtree.convenientdao.config.SqlColumnTypesMapper;
import com.oldtree.convenientdao.property.TableProperty;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 解析Property.
 * @ClassName PropertyResolver
 * @Author oldTree
 * @Date 2022/4/24
 * @Version 1.0
 */
public class PropertyResolver {

    /**
     * 解析tableProperty进行建表sql拼写,创建删除表不进行bindOne 或者bindMany处理
     * @param tableProperty
     * @return
     */
    public static String resolvedPropertyToCreate(TableProperty tableProperty){
        final String head = SqlColumnTypesMapper.sqlMapper.get("createTable");
        AtomicReference<StringBuffer> sql = new AtomicReference<>(new StringBuffer());
        sql.get().append(head).append(tableProperty.getTabName()).append(" (\n");
        tableProperty.getColumns().forEach((column -> {
            sql.set(mappedExistsTypes(sql.get(), column));
        }));
        int i = sql.get().lastIndexOf(",");
        sql.get().replace(i,i+1,"");
        sql.get().append(")");
        return sql.toString();
    }

    /**
     * 映射mapper内部不存在的数据类型
     * @param stringBuffer
     * @param column
     * @return
     */
    private static StringBuffer mappedOtherType(StringBuffer stringBuffer, TableProperty.Column column){
        stringBuffer.append(" ");
        return stringBuffer;
    }

    /**
     * 映射mapper内部存在的数据类型，需要根据开发者的不同的选择
     * 然后映射成所需要的数据类型！
     * @param stringBuffer
     * @param column
     */
    private static StringBuffer mappedExistsTypes(StringBuffer stringBuffer , TableProperty.Column column){
        String type = SqlColumnTypesMapper.sqlMapper.get(column.getColumnType());
        if(null==type){
            return mappedOtherType(stringBuffer,column);
        }
        if(null!=type){
            stringBuffer.append(" \t").append(column.getColumnName());
            stringBuffer.append(" ").append(type);
            if(column.isPk()){
                stringBuffer.append(" ").append(SqlColumnTypesMapper.sqlMapper.get("pk"));
            }
            if(!"".equals(column.getSort())){
                stringBuffer.append(" ").append(SqlColumnTypesMapper.sqlMapper.get(column.getSort()));
            }
            String other = column.getOther();
            if(!"".equals(other)){
                stringBuffer.append(" ").append(SqlColumnTypesMapper.sqlMapper.get(other));
            }
            if(!column.isCanNull()){
                stringBuffer.append(" ").append(SqlColumnTypesMapper.sqlMapper.get("notNull"));
            }
        }
        stringBuffer.append(",\n");
        return stringBuffer;
    }

    /**
     * 解析tableProperty成drop a table 的sqlite语句
     * @param tableProperty
     * @return
     */
    public static String resolvedPropertyToDrop(TableProperty tableProperty){
        final String head = SqlColumnTypesMapper.sqlMapper.get("dropTable");
        StringBuffer sql = new StringBuffer();
        sql.append(head).append(tableProperty.getTabName()).append(" ");
        return sql.toString();
    }
}

