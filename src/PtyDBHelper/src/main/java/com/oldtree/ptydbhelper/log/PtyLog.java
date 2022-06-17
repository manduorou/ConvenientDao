package com.oldtree.ptydbhelper.log;

import android.util.Log;
import com.oldtree.ptydbhelper.core.PtyMaster;
import com.oldtree.ptydbhelper.exception.ConfigException;
import com.oldtree.ptydbhelper.property.ConfigProperty;

/**
 * 统一日志输出类
 * @ClassName PtyLog
 * @Author oldTree
 * @Date 2022/6/17
 * @Version 1.0
 */
public class PtyLog {
    public static final String PTY_TAG = "PtyDBHelper";
    public static final String MSG_PREFIX = "====>\t";
    public static boolean appDebug;
    static {
        try {
            appDebug = ConfigProperty.getConfigProperty().getAppDebug();
        } catch (ConfigException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * @param msg
     */
    public static final void d(String msg){
        if(appDebug){
            Log.d(PTY_TAG,MSG_PREFIX+msg);
        }
    }
    /**
     * verbose啰嗦语句
     * @param msg
     */
    public static final void v(String msg){
        Log.v(PTY_TAG,MSG_PREFIX+msg);
    }

    /**
     * error错误提示
     * @param msg
     */
    public static final void e(String msg){
        Log.e(PTY_TAG,MSG_PREFIX+msg);
    }

    /**
     * warning警告
     * @param msg
     */
    public static final void w(String msg){
        Log.w(PTY_TAG,MSG_PREFIX+msg);
    }

    /**
     * information提示性的消息
     * @param msg
     */
    public static final void i(String msg){
        Log.i(PTY_TAG,msg);
    }

}

