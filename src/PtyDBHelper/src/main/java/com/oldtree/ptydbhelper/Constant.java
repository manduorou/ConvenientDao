package com.oldtree.ptydbhelper;

import java.util.HashMap;
import java.util.Map;

/**
 * 详细介绍类的情况.
 *
 * @ClassName ConStant
 * @Author oldTree
 * @Date 2022/4/30
 * @Version 1.0
 */
public class Constant {
    public static Map<String , String> map;

    static {
        map = new HashMap<>();
        map.put("black","");
        map.put("blackPlace"," ");
        map.put("douhao",",");
        map.put("leftkuohao","(");
        map.put("rightkuohao",")");
    }
}

