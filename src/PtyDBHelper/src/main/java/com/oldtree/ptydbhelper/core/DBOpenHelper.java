package com.oldtree.ptydbhelper.core;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.oldtree.ptydbhelper.property.ConfigProperty;

/**
 * dbOpenHelper
 * @ClassName DBOpenHelper
 * @Author oldTree
 * @Date 2022/4/21
 * @Version 1.0
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    private static DBOpenHelper dbOpenHelper;
    private DBOpenHelper(ConfigProperty configProperty) {
        super(configProperty.getContext(), configProperty.getDbName(), null, configProperty.getDbVersion());
    }

    /**
     * 单例模式 ,目前不考虑多线程去实现数据的增删改查
     * @param configProperty
     * @return
     */
    public static DBOpenHelper getInstance(ConfigProperty configProperty) {
        if (dbOpenHelper == null) {
            synchronized (DBOpenHelper.class) {
                if (dbOpenHelper == null) {
                    dbOpenHelper = new DBOpenHelper(configProperty);
                }
            }
        }
        return dbOpenHelper;
    }

    /**
     * 调用SqliteDb的api时，判断数据库是否存在，不存在则会创建数据库,
     * 也即是说，我们需要在调用api前生成对应的tableProperties的set集合，
     * 然后通过TableFactory生成对应的建立表的sql语句
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("PitaYadao","欢迎使用pitayaDao，详情使用请参考帮助文档");
        PtyDBFactory ptyDBFactory = PtyDBFactory.build();
        try {
            ptyDBFactory.createAllTables(sqLiteDatabase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 目前我想的是，自动管理版本，然后通过pojo类上面的注解的改变，进行db的升级操作！
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.i("PitaYaDAO", "更新sqlite，老版本：" + oldVersion + " 到新版本： " + newVersion + " ，此操作将删除所有表");
        PtyDBFactory ptyDBFactory = PtyDBFactory.build();
        try {
            ptyDBFactory.dropAllTables(sqLiteDatabase);

            ptyDBFactory.createAllTables(sqLiteDatabase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

}
