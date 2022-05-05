package com.oldtree.ptydbhelper.core;

import com.oldtree.ptydbhelper.config.SqlColumnTypesMapper;
import com.oldtree.ptydbhelper.query.Query;

/**
 * 详细介绍类的情况.
 *
 * @ClassName QueryResolver
 * @Author oldTree
 * @Date 2022/4/25
 * @Version 1.0
 */
public class QueryResolver {

    private static final String[] keyCodes = new String[]{
            "OR",
            "AND",
            ","
    };
    private static final String[] conditionsErrorSample = new String[]{
            "ANDOR",
            "ORAND"
    };
    /**
     * 解析Query，进行总揽的处理
     * @param query
     * @return
     */
    public static String resolvedQuery(Query query){
        StringBuffer head = query.getHead();
        StringBuffer constraint = query.getConstraint();
        StringBuffer conditions = query.getConditions();
        StringBuffer queryEnd = query.getEnd();

        StringBuffer querySql = new StringBuffer();
        //处理conditions
        conditions = resolveConditions(conditions);
        querySql = connected(querySql,head,constraint,conditions,queryEnd);
        return querySql.toString();
    }

    /**
     * 连接处理
     * @param querySql
     * @param stringBuffers
     * @return
     */
    private static StringBuffer connected(StringBuffer querySql , StringBuffer... stringBuffers){
        for (StringBuffer buffer : stringBuffers) {
            querySql.append(buffer);
        }
        return querySql;
    }

    /**
     * 处理where的条件
     * @param conditions
     * @return
     */
    private static  StringBuffer resolveConditions(StringBuffer conditions){
        if(null!=conditions&&conditions.length()>0){
            String where = SqlColumnTypesMapper.sqlMapper.get("where");
            conditions.insert(0," "+where);
            for (String keyCode : keyCodes) {
                int length = conditions.length();
                int kLength = keyCode.length();
                int lastIndexOf = conditions.lastIndexOf(keyCode);
                if(-1!=lastIndexOf&&lastIndexOf == (length-kLength)){
                    conditions.replace(lastIndexOf,lastIndexOf+kLength,"");
                }
            }
            conditions = correctedConditions(conditions);
        }
        return  conditions;
    }

    /**
     * where条件的简单矫正
     * @param conditions
     * @return
     */
    private static StringBuffer correctedConditions(StringBuffer conditions){
        int indexOf = conditions.indexOf(conditionsErrorSample[0]);
        if(-1 != indexOf){
            conditions.replace(indexOf,indexOf+5,"OR");
        }
        indexOf = conditions.indexOf(conditionsErrorSample[1]);
        if(-1 != indexOf){
            conditions.replace(indexOf,indexOf+5,"AND");
        }
        return conditions;
    }

    /**
     * 处理groupBy和order by 还有having字句之间的一些语法上面的硬性要求
     * @param end
     * @return
     */
    private static StringBuffer resolvedEnd(StringBuffer end){

        return end;
    }
    /**
     * end简单矫正
     * @param end
     * @return
     */
    private static StringBuffer correctedEnd(StringBuffer end){

        return end;
    }
}

