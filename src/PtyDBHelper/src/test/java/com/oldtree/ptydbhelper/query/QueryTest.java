package com.oldtree.ptydbhelper.query;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.oldtree.ptydbhelper.core.DBOpenHelper;
import com.oldtree.ptydbhelper.core.PoJoClassHandler;
import com.oldtree.ptydbhelper.core.QueryResolver;
import com.oldtree.ptydbhelper.dao.UserDao;
import com.oldtree.ptydbhelper.entity.Room;
import com.oldtree.ptydbhelper.entity.User;
import com.oldtree.ptydbhelper.exception.PoJoException;
import com.oldtree.ptydbhelper.property.Property;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.*;

public class QueryTest {
    @Test
    public void test1(){
//        字符串
//        for循环便利速度
        printInfo(null);
    }
    @Test
    public void test2(){
        //        迭代器速度
        printInfoPlus("啦啦","你会吗","你好啊","Java","我喜欢");
    }
    public static void printInfo(String... strings){
        if(null==strings){
            System.out.println("没有数据");
        }
        for (String string : strings) {
            System.out.println(string);
        }
    }
    public static void printInfoPlus(String... strings){
        Iterator<String> iterator = Arrays.stream(strings).iterator();
        while(iterator.hasNext()){
            String next = iterator.next();
            System.out.println(next);
        }
    }


    @Test
    public void test3(){
        StringBuffer end =new StringBuffer("zxcvbnm");
        StringBuffer limi = new StringBuffer("HAVING COLUMN1,COLUMN2");
        int orderBy = end.indexOf("ORDER BY");
        System.out.println("ORDERBY的位置"+orderBy);
    }

    /**
     * 测试首要位置插入
     */
    @Test
    public void test4(){
        StringBuffer stringBuffer = new StringBuffer("lalalalla");
        stringBuffer.insert(0," hello");
        System.out.println(stringBuffer.toString());
    }
    @Test
    public void test5(){
        StringBuffer stringBuffer = new StringBuffer("sfafasf, OR");
        int or = stringBuffer.lastIndexOf("OR");
        if(-1!=or&&or==(stringBuffer.length()-2)){
            stringBuffer.replace(or,or+2,"");
        }
        System.out.println("or的位置"+or+"长度"+stringBuffer.length());
        System.out.println(stringBuffer);
    }


    /**
     * 利用property进行sql的相关的操作
     */
    @Test
    public void test6(){
        Query query = QueryBuilder.build(User.class);
        query.groupBy(new Property(0,Integer.class,"userid",false,"USERID"));
        query.orderBy(false,new Property(0,Integer.class,"userId",false,"USERID"));
        query.eq(new Property(0,Integer.class,"userId",false,"USERID"),1)
                .lt(new Property(0,Integer.class,"userid",false,"USERID"),1);
        String resolvedQuery = QueryResolver.resolvedQuery(query);
        System.out.println(resolvedQuery);
    }

    /**
     * 明天创建使用下标，字符等方式去进行操作
     */
    


    @Test
    public void test7(){
        Query query = QueryBuilder.build(User.class).eq("userid", 12)
                .eq(0, 1)
                .eq(1, "admin")
                .eq(2, "123456")
                .eq(3, 18);
        System.out.println(QueryResolver.resolvedQuery(query));
    }

    @Test
    public void test8(){
        Query query = QueryBuilder.build(User.class).eq(0,12);
        String resolvedQuery = QueryResolver.resolvedQuery(query);
        System.out.println(resolvedQuery);
    }

    @Test
    public void test9(){
        test7();
        test8();
    }


    @Test
    public void test10(){
        DBOpenHelper openHelper = DBOpenHelper.getInstance(null);
        SQLiteDatabase readableDatabase = openHelper.getReadableDatabase();
        String sql = "SELECT  USERID, USERNAME, PASSWORD, AGE FROM User WHERE USERID='12'";
        Cursor cursor = readableDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()){
            /**
             * 封装实体操作，根据实体属性数量，这部分代码依次臃肿
             */
        }
        readableDatabase.close();
    }

    @Test
    public void test11(){
        Query query = QueryBuilder.build(User.class).join(Room.class, "userid");
        String resolvedQuery = QueryResolver.resolvedQuery(query);
        System.out.println(resolvedQuery);
    }

    @Test
    public void test12(){
        Query query = QueryBuilder.build(User.class).getAll();
        String resolvedQuery = QueryResolver.resolvedQuery(query);
        System.out.println(resolvedQuery);
    }
    @Test
    public void test13(){
        PoJoClassHandler poJoClassHandler = new PoJoClassHandler();
        try {
            Property pkProperty = poJoClassHandler.createPkProperty(User.class);
            Query query = QueryBuilder.build(User.class).eq(pkProperty,1);
            String resolvedQuery = QueryResolver.resolvedQuery(query);
            System.out.println(resolvedQuery);
        } catch (PoJoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test14(){
        UserDao userDao = new UserDao(User.class);

    }
}