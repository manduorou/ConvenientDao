package com.oldtree.ptydbhelper.property;

import android.content.Context;
import com.oldtree.ptydbhelper.exception.ConfigException;

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

    private static ConfigProperty configProperty;
    public static final ConfigProperty getInstance(String dbName, int dbVersion, Class[] pojoClasses, Context context){
        if(configProperty==null){
            synchronized ((ConfigProperty.class)){
                if (configProperty==null){
                    configProperty = new ConfigProperty(dbName,dbVersion,pojoClasses,context);
                }
            }
        }
        return configProperty;
    }

    public static ConfigProperty getConfigProperty() throws ConfigException {
        if(configProperty==null){
            throw new ConfigException(configProperty.getClass().getName()+"配置类没有初始化");
        }
        return configProperty;
    }

    public ConfigProperty(String dbName, int dbVersion, Class[] pojoClasses, Context context) {
        this.dbName = dbName;
        this.dbVersion = dbVersion;
        this.pojoClasses = pojoClasses;
        this.context = context;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public int getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(int dbVersion) {
        this.dbVersion = dbVersion;
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

    public void setContext(Context context) {
        this.context = context;
    }


}

