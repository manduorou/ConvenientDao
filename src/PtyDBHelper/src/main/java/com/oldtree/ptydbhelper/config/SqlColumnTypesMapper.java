package com.oldtree.ptydbhelper.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 详细介绍类的情况.
 *
 * @ClassName SqlColumnTypesMapper
 * @Author oldTree
 * @Date 2022/4/21
 * @Version 1.0
 */
public class SqlColumnTypesMapper<K,V> {
    public static Map<String,String> sqlMapper = new HashMap<>();
    static{
        sqlMapper.put("String","TEXT");
        sqlMapper.put("char","TEXT");
        sqlMapper.put("Integer","INTEGER");
        sqlMapper.put("int","INTEGER");
        sqlMapper.put("double","DOUBLE");
        sqlMapper.put("Double","DOUBLE");
        sqlMapper.put("float","REAL");
        sqlMapper.put("Float","REAL");
        sqlMapper.put("long","BIGINT");
        sqlMapper.put("Long","BIGINT");
        sqlMapper.put("Date","DATETIME");
        sqlMapper.put("pk","PRIMARY KEY");
        sqlMapper.put("fk","REFERENCES");
        sqlMapper.put("autoAdd","AUTOINCREMENT");
        sqlMapper.put("find","SELECT");
        sqlMapper.put("and","AND");
        sqlMapper.put("glob","GLOB");
        sqlMapper.put("limit","LIMIT");
        sqlMapper.put("orderBy","ORDER BY");
        sqlMapper.put("groupBy","GROUP BY");
        sqlMapper.put("having","HAVING");
        sqlMapper.put("select","SELECT");
        sqlMapper.put("where","WHERE");
        sqlMapper.put("from","FROM");
        sqlMapper.put("update","UPDATE");
        sqlMapper.put("insert","INSERT");
        sqlMapper.put("delete","DELETE");
        sqlMapper.put("createTable","CREATE TABLE IF NOT EXISTS ");
        sqlMapper.put("dropTable","DROP TABLE IF EXISTS ");
        sqlMapper.put("exist","EXIST");
        sqlMapper.put("notNull","NOT NULL");
        sqlMapper.put("noExist","NOT EXISTS");
        sqlMapper.put("constraint","CONSTRAINT");
        sqlMapper.put("unique","UNIQUE");
    }
    public void getEE(){

    }
}

