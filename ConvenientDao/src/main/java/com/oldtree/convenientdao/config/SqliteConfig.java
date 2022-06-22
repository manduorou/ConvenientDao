package com.oldtree.convenientdao.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * sqlite的配置类
 * 通过deName属性设置数据库文件的名字，通常后缀名为.db文件
 * 通过dbVersion设置数据库的版本，很方便的进行数据库的升级操作
 * 通过pojoClassArray设置最初数据库建立时就会创建的表
 * 通过appDebug设置开发过程中，显示每次使用对应的Dao操作所产生的调试sql语句，契合初学者。
 * @ClassName PtyConfig
 * @Author oldTree
 * @Date 2022/4/28
 * @Version 1.0.5
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SqliteConfig {
    /**
     * 设置数据库名与在虚拟机中的文件名，如果有需要自行设置，db作为后缀名
     * @return 得到一个数据库的名字，初始值为convenientDao.db
     */
    String dbName() default "convenientDao.db";
    /**
     * 控制数据库版本，通常用于数据库的升级操作，请小心点使用！
     * @return 得到数据库的版本号，初始值为1
     */
    int dbVersion() default 1;
    /**
     * 数据库最开始创建时就会创建的表集合，这里放置一个class数组，如果有需要的话
     * @return 得到一个被注释上POJO的类的数组
     */
    Class[] pojoClassArray() default {};
    /**
     * 设置为false这样可以节省性能消耗，但是一定程度上，对初学者不友好。
     * 因为体会到了便利，但是基础的直接忽视了可不行，基础是最重要的。
     * @return 初始值为false，默认不开启语句调试，改成true开启语句调试
     */
    boolean appDebug() default false;
}
