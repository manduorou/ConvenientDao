package com.oldtree.ptydbhelper.property;

import android.content.Context;
import com.oldtree.ptydbhelper.exception.ConfigException;
import com.oldtree.ptydbhelper.log.PtyLog;

/**
 * 详细介绍类的情况.
 *
 * @ClassName ConfigProperty
 * @Author oldTree
 * @Date 2022/4/28
 * @Version 1.0
 */
public class ConfigProperty {
    private String dbName;
    private int dbVersion;
    private Class[] pojoClasses;
    private Context context;
    private Boolean appDebug;

    private static ConfigProperty configProperty;
    public static final ConfigProperty getInstance(String dbName, int dbVersion, Class[] pojoClasses, Context context , Boolean appDebug){
        if(configProperty==null){
            synchronized ((ConfigProperty.class)){
                if (configProperty==null){
                    configProperty = new ConfigProperty(dbName,dbVersion,pojoClasses,context,appDebug);
                }
            }
        }
        return configProperty;
    }

    public static ConfigProperty getConfigProperty() throws ConfigException {
        if(configProperty==null){
            PtyLog.e(configProperty.getClass().getName()+"配置类没有初始化");
            throw new ConfigException(configProperty.getClass().getName()+"配置类没有初始化");
        }
        return configProperty;
    }

    public ConfigProperty(String dbName, int dbVersion, Class[] pojoClasses, Context context , Boolean appDebug) {
        this.dbName = dbName;
        this.dbVersion = dbVersion;
        this.pojoClasses = pojoClasses;
        this.context = context;
        this.appDebug = appDebug;
    }

    public String getDbName() {
        return dbName;
    }


    public int getDbVersion() {
        return dbVersion;
    }


    public Class[] getPojoClasses() {
        return pojoClasses;
    }

    public void setPojoClasses(Class[] pojoClasses) {
        this.pojoClasses = pojoClasses;
    }

    public Context getContext() {
        return context;
    }

    public Boolean getAppDebug() {
        return appDebug;
    }
}

