package com.oldtree.convenientdao.log;

import android.util.Log;
import com.oldtree.convenientdao.exception.ConfigException;
import com.oldtree.convenientdao.property.ConfigProperty;
import com.oldtree.convenientdao.utils.DateUtils;

/**
 * 统一日志输出类
 * @ClassName PtyLog
 * @Author oldTree
 * @Date 2022/6/17
 * @Version 1.0.5
 */
public final class ConvenientDaoLog{
    public static final String PTY_TAG = "ConvenientDao";
    public static final String MSG_PREFIX = "====>\t";
    public static final String MSG_DEBUG ="调试:";
    public static final String MSG_N = "\n";
    public static final String MSG_INFO ="消息:";
    public static final String MSG_WARN ="警告:";
    public static final String MSG_ERROR = "错误:";
    public static boolean appDebug;
    private static StringBuffer message;
    static {
        try {
            appDebug = ConfigProperty.getConfigProperty().getAppDebug();
        } catch (ConfigException e) {
            e.printStackTrace();
        }
    }
    /**
     * 开启调试后会提示的消息
     * @param msg 日志文本
     */
    public static final void d(String msg){
        if(appDebug){
            Log.d(PTY_TAG,MSG_PREFIX+MSG_DEBUG+ DateUtils.Now() +MSG_N+MSG_PREFIX+MSG_N+msg);
        }
    }
    /**
     * verbose 其他啰嗦的提示长句
     * @param msg 日志文本
     */
    public static final void v(String msg){
        Log.v(PTY_TAG,MSG_PREFIX+msg);
    }
    /**
     * error，当出现sql或者在不可允许的范围的比较严重的错误，进行错误提示
     * @param msg 日志文本
     */
    public static final void e(String msg){
        Log.e(PTY_TAG,MSG_PREFIX+MSG_ERROR+ DateUtils.Now() +MSG_N+MSG_PREFIX+MSG_N+msg);
    }
    /**
     * warning，可以正常允许，但是可能造成其他不可预料的后果，所以会警告
     * @param msg 日志文本
     */
    public static final void w(String msg){
        Log.w(PTY_TAG,MSG_PREFIX+MSG_WARN+ DateUtils.Now() +MSG_N+MSG_PREFIX+MSG_N+msg);
    }
    /**
     * information，一些提示性的消息提示
     * @param msg 日志文本
     */
    public static final void i(String msg){
        Log.i(PTY_TAG,MSG_PREFIX+MSG_INFO+ DateUtils.Now() +MSG_N+MSG_PREFIX+MSG_N+msg);
    }

}

