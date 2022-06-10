package com.oldtree.ptydbhelper.core;

import android.content.Context;
import android.util.Log;
import com.oldtree.ptydbhelper.config.PtyConfig;
import com.oldtree.ptydbhelper.property.ConfigProperty;

/**
 * 主要的类.
 *
 * @ClassName PitayaDAO
 * @Author oldTree
 * @Date 2022/4/28
 * @Version 1.0
 */
public final class PtyMaster {

    private static PtyMaster ptyMaster =null;
    private Class<?> configCls;

    public PtyMaster(Class<?> configCls) {
        this.configCls = configCls;
    }

    public PtyMaster() {

    }
    public static PtyMaster getInstance(Class<?> cls) {
        if (ptyMaster == null){
            synchronized (PtyMaster.class){
                if (ptyMaster == null){
                    ptyMaster = new PtyMaster(cls);
                }
            }
        }
        return ptyMaster;
    }

    /**
     * 装载配置类
     * @param context
     * @return
     */
    public PtyMaster autoConfig(Context context){
        PtyConfig ptyConfig = configCls.getDeclaredAnnotation(PtyConfig.class);
        if(null==ptyConfig||null==context){
            Log.e("PtyDBHelper","====>\t空指针异常，context或者没有将注解配置类载入PtyMaster中。。");
            throw  new NullPointerException();
        }
        ConfigProperty configProperty = ConfigProperty.getInstance(ptyConfig.dbName(),ptyConfig.dbVersion(),ptyConfig.pojoClassArray(),context);
        DBOpenHelper.getInstance(configProperty);
        showMsg();
        return this;
    }

    /**
     *
     * @param context
     * @param cls
     * @return
     */
    public static PtyMaster autoConfig(Context context, Class<?> cls){
        return getInstance(cls).autoConfig(context);
    }


    /**
     * logo showing...
     */
    private void showMsg(){
        Log.d("PtyDBHelper","====>\t------------------------------------------------------------");
        Log.d("PtyDBHelper","====>\t------------------------------------------------------------");
        Log.d("PtyDBHelper","====>\t-----------------------<<<欢迎使用>>>-------------------------");
        Log.d("PtyDBHelper","====>\t---本框架是一个轻量级ORM，便捷开发，支持原生sqlite语句，无其他依赖---");
        Log.d("PtyDBHelper","====>\t------------------------------------------------------------");
        Log.d("PtyDBHelper","====>\t----@@@@------@@--------------@@----------------------------");
        Log.d("PtyDBHelper","====>\t--@@----@@----@@--------------@@----------------------------");
        Log.d("PtyDBHelper","====>\t-@@------@@---@@--------------@@----------------------------");
        Log.d("PtyDBHelper","====>\t@@--------@@--@@--------------@@----------------------------");
        Log.d("PtyDBHelper","====>\t@@--------@@--@@---------@@@@-@@----@@@@@--@@@---@@@@--@@@@-");
        Log.d("PtyDBHelper","====>\t@@--------@@--@@--------@-----@@------@----@@-@--@-----@----");
        Log.d("PtyDBHelper","====>\t-@@------@@---@@-------@------@@------@----@@@---@@@@--@@@@-");
        Log.d("PtyDBHelper","====>\t--@@----@@----@@--------@----@@-------@----@--@--@-----@----");
        Log.d("PtyDBHelper","====>\t----@@@@-------@@@@@@@@---@@--@@------@----@---@-@@@@--@@@@-");
        Log.d("PtyDBHelper","====>\t------------------------------------------------------------");
        Log.d("PtyDBHelper","====>\t----------------------------------------------qq：493582307-");
        Log.d("PtyDBHelper","====>\t------------------------------------------Version--1.0.2----");
        Log.d("PtyDBHelper","====>\t------------------------------------------------------------");
        Log.d("PtyDBHelper","====>\t------------------------------------------------------------");
    }
}

