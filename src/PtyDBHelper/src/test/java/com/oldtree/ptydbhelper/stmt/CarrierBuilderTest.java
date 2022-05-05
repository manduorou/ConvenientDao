package com.oldtree.ptydbhelper.stmt;

import com.oldtree.ptydbhelper.entity.User;
import com.oldtree.ptydbhelper.exception.PoJoException;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class CarrierBuilderTest<T> {
    private StringBuffer condition;

    public void build(Object instance){
        Class<?> aClass = instance.getClass();
        condition = new StringBuffer();
        Field[] declaredFields = aClass.getDeclaredFields();
        if(null!=declaredFields&&declaredFields.length>0){
            for (Field field : declaredFields) {
                //得到类型名
                String name = field.getName();
                try {
                    field.setAccessible(true);
                    Object o = field.get(instance);
                    System.out.println("值为"+o+"类型名为："+name);
                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            condition.append(declaredFields[0].getName());
        }
    }
    public void deleteById(Integer id){
        try {
            Object o = User.class.newInstance();
            build(o);
            condition.append(" ='").append(id).append("'");
            System.out.println(condition);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test2(){
        User user = new User(10,null,"123456",21);
        build(user);
    }

    @Test
    public void test3(){
        deleteById(1);
    }


    @Test
    public void test4(){
        Tt t = new Tt();
        t.update(new User(1,"","",1));
    }

    public class Tt{
        public void update(Object instance){
            Class<?> aClass = instance.getClass();
            Field declaredField = aClass.getDeclaredFields()[0];
            declaredField.setAccessible(true);
            //得到主键的值
            try {
                Object value = declaredField.get(instance);
                System.out.println(value+"条件字段名"+declaredField.getName());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            declaredField.setAccessible(false);
        }
    }


    @Test
    public void test5(){
        try {
            CarrierBuilder.handledPojoInstance(new User());
        } catch (PoJoException e) {
            e.printStackTrace();
        }
    }
}