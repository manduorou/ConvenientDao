package com.oldtree.convenientdao.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间显示工具类
 * @ClassName DateUtils
 * @Author oldTree
 * @Date 2022/6/20
 * @Version 1.0.5
 */
public final class DateUtils {
    private static Date mDate;
    private static String formatDate;
    /**
     * 得到本地的当前时间
     * @return 返回一个当前时间的字符串
     */
    public static String now(){
        mDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatDate = sdf.format(mDate);
        return formatDate;
    }

}

