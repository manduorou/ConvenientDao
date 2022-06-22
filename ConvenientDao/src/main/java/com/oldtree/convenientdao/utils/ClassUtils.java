package com.oldtree.convenientdao.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import dalvik.system.DexFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 详细介绍类的情况.
 *
 * @ClassName ClassUtils
 * @Author oldTree
 * @Date 2022/6/21
 * @Version 1.0
 */
public class ClassUtils {
    //TODO 可以使用于dao克隆
    //根据父类找
    public static final List<Class<?>> scanSubClasses(Context context, Class parent) {
        List<Class<?>> classList = null;
        ApplicationInfo ai = context.getApplicationInfo();
        String classPath = ai.sourceDir;
        DexFile dex = null;
        try {
            dex = new DexFile(classPath);
            Enumeration<String> apkClassNames = dex.entries();
            classList = new ArrayList<>();
            while (apkClassNames.hasMoreElements()) {
                String className = apkClassNames.nextElement();
                try {
                    Class c = context.getClassLoader().loadClass(className);
                    if (parent.isAssignableFrom(c)) {
                        classList.add(c);
                        android.util.Log.i("nora", className);
                    }
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return classList;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                dex.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
}

