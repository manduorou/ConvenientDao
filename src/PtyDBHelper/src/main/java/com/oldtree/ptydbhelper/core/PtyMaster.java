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
        Log.d("PtyDBHelper","------------------------------------------------------------");
        Log.d("PtyDBHelper","------------------------------------------------------------");
        Log.d("PtyDBHelper","-----------------------<<<欢迎使用>>>-------------------------");
        Log.d("PtyDBHelper","---本框架是一个轻量级ORM，便捷开发，支持原生sqlite语句，无其他依赖----");
        Log.d("PtyDBHelper","------------------------------------------------------------");
        Log.d("PtyDBHelper","----@@@@------@@--------------@@----------------------------");
        Log.d("PtyDBHelper","--@@----@@----@@--------------@@----------------------------");
        Log.d("PtyDBHelper","-@@------@@---@@--------------@@----------------------------");
        Log.d("PtyDBHelper","@@--------@@--@@--------------@@----------------------------");
        Log.d("PtyDBHelper","@@--------@@--@@---------@@@@-@@----@@@@@--@@@---@@@@--@@@@-");
        Log.d("PtyDBHelper","@@--------@@--@@--------@-----@@------@----@@-@--@-----@----");
        Log.d("PtyDBHelper","-@@------@@---@@-------@------@@------@----@@@---@@@@--@@@@-");
        Log.d("PtyDBHelper","--@@----@@----@@--------@----@@-------@----@--@--@-----@----");
        Log.d("PtyDBHelper","----@@@@-------@@@@@@@@---@@--@@------@----@---@-@@@@--@@@@-");
        Log.d("PtyDBHelper","------------------------------------------------------------");
        Log.d("PtyDBHelper","----------------------------------------------qq：493582307-");
        Log.d("PtyDBHelper","------------------------------------------Version--1.0.2----");
        Log.d("PtyDBHelper","------------------------------------------------------------");
        Log.d("PtyDBHelper","------------------------------------------------------------");
    }
}

