package com.oldtree.convenientdao.dao;

import android.content.Context;
import com.oldtree.convenientdao.async.TheadPool;
import com.oldtree.convenientdao.config.SqliteConfig;
import com.oldtree.convenientdao.db.DBOpenHelper;
import com.oldtree.convenientdao.property.ConfigProperty;
import com.oldtree.convenientdao.log.ConvenientDaoLog;

/**
 * 初始化的类，本框架所有的类的工作，大部分依赖于此类的初始化，
 * 初始化后才能做你想要它所做的事情，原名PtyMaster，更名于1.0.5版本
 * @ClassName ConvenientDaoInitializer
 * @Author oldTree
 * @Date 2022/4/28
 * @Version 1.0.5
 */
public final class ConvenientDaoInitializer {
    private static ConvenientDaoInitializer convenientDaoInitializer =null;
    private Class<?> configCls;
    private TheadPool theadPool;
    private static String version = "Version--1.0.5";
    public ConvenientDaoInitializer(Class<?> configCls) {
        this.configCls = configCls;
        this.theadPool = TheadPool.getInstance();
    }

    public ConvenientDaoInitializer() {

    }
    public static ConvenientDaoInitializer getInstance(Class<?> cls) {
        if (convenientDaoInitializer == null){
            synchronized (ConvenientDaoInitializer.class){
                if (convenientDaoInitializer == null){
                    convenientDaoInitializer = new ConvenientDaoInitializer(cls);
                }
            }
        }
        return convenientDaoInitializer;
    }

    /**
     * 装载配置类
     * @param context
     * @return
     */
    public ConvenientDaoInitializer autoConfig(Context context){
        SqliteConfig sqliteConfig = configCls.getDeclaredAnnotation(SqliteConfig.class);
        if(null== sqliteConfig ||null==context){
            ConvenientDaoLog.e("空指针异常，context或者没有将注解配置类载入ConvenientDaoInitializer中。。");
            throw  new NullPointerException();
        }
        ConfigProperty configProperty = ConfigProperty.getInstance(sqliteConfig.dbName(), sqliteConfig.dbVersion(), sqliteConfig.pojoClassArray(),context , sqliteConfig.appDebug());
        DBOpenHelper.getInstance(configProperty);
        showLogo();
        return this;
    }

    /**
     * 装载配置类
     * @param context 提供一个上下文
     * @param cls 提供一个配置类的Class
     * @return 返回一个配置ConvenientDao初始化对象
     */
    public static ConvenientDaoInitializer autoConfig(Context context, Class<?> cls){
        return getInstance(cls).autoConfig(context);
    }
    /**
     * logo showing...
     */
    private void showLogo(){
        ConvenientDaoLog.v("------------------------------------------------------------");
        ConvenientDaoLog.v("------------------------------------------------------------");
        ConvenientDaoLog.v("-----------------------<<<欢迎使用>>>-------------------------");
        ConvenientDaoLog.v("---本框架是一个轻量级ORM，便捷开发，支持原生sqlite语句，无其他依赖---");
        ConvenientDaoLog.v("------------------------------------------------------------");
        ConvenientDaoLog.v("----@@@@------@@--------------@@----------------------------");
        ConvenientDaoLog.v("--@@----@@----@@--------------@@----------------------------");
        ConvenientDaoLog.v("-@@------@@---@@--------------@@----------------------------");
        ConvenientDaoLog.v("@@--------@@--@@--------------@@----------------------------");
        ConvenientDaoLog.v("@@--------@@--@@---------@@@@-@@----@@@@@--@@@---@@@@--@@@@-");
        ConvenientDaoLog.v("@@--------@@--@@--------@-----@@------@----@@-@--@-----@----");
        ConvenientDaoLog.v("-@@------@@---@@-------@------@@------@----@@@---@@@@--@@@@-");
        ConvenientDaoLog.v("--@@----@@----@@--------@----@@-------@----@--@--@-----@----");
        ConvenientDaoLog.v("----@@@@-------@@@@@@@@---@@--@@------@----@---@-@@@@--@@@@-");
        ConvenientDaoLog.v("------------------------------------------------------------");
        ConvenientDaoLog.v("----------------------------------------------qq：493582307-");
        ConvenientDaoLog.v("------------------------------------------"+version+"----");
        ConvenientDaoLog.v("------------------------------------------------------------");
        ConvenientDaoLog.v("------------------------------------------------------------");
    }
}

