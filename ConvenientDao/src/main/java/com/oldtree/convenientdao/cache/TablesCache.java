package com.oldtree.convenientdao.cache;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.oldtree.convenientdao.db.DBOpenHelper;
import com.oldtree.convenientdao.db.MDBFactory;
import com.oldtree.convenientdao.property.TableProperty;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * TODO 缓存本地表字段与结构等，如果当前得到的表结构跟缓存的有差别，就alert对应表修改表结构为当前的，然后修改缓存数据
 * @ClassName TablesCache
 * @Author oldTree
 * @Date 2022/6/17
 * @Version 1.0
 */
public class TablesCache {

    //TODO 获取表缓存
    public static final Set<TableProperty> getTableCache(String tableName){
        Set<TableProperty> tableProperties = null;
        MDBFactory build = MDBFactory.build();
        DBOpenHelper dbOpenHelper = build.getDbOpenHelper();
        SQLiteDatabase writableDatabase = dbOpenHelper.getWritableDatabase();
        String sql = "";
        Cursor query = writableDatabase.query(tableName, null, null, null, null, null, null);
        if(null!=query&&query.getCount()>0){
            tableProperties = new LinkedHashSet<>();
            tableProperties.add(new TableProperty());
        }

        return tableProperties;
    }

    //TODO 存入缓存



    //TODO 对比修改




}

