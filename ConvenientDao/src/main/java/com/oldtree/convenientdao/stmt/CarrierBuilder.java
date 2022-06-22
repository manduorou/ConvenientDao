package com.oldtree.convenientdao.stmt;

import android.content.ContentValues;
import com.oldtree.convenientdao.exception.PoJoException;
import com.oldtree.convenientdao.log.ConvenientDaoLog;
import com.oldtree.convenientdao.utils.TableUtils;
import com.oldtree.convenientdao.core.Pk;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 详细介绍类的情况.
 *
 * @ClassName CarrierBuilder
 * @Author oldTree
 * @Date 2022/4/28
 * @Version 1.0
 */
public class CarrierBuilder {
    /**
     * 设置值的时候需要加判断
     * @param object
     * @return
     */
    public static Carrier build(Object object) {
        Carrier carrier = null;
        ContentValues contentValues = new ContentValues();
        StringBuffer constraint= new StringBuffer();
        Class<?> aClass = object.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        if(null!=declaredFields&&declaredFields.length>0){
            for (Field declaredField : declaredFields) {
                try {
                    declaredField.setAccessible(true);
                    Object value = declaredField.get(object);
                    if(null!=value){
                        contentValues.put(declaredField.getName(),value.toString());
                    }
                    declaredField.setAccessible(false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            constraint = createConstraint(constraint,declaredFields);
            String tableName = null;
            try {
                tableName = TableUtils.getTableName(aClass);
            } catch (PoJoException e) {
                ConvenientDaoLog.e(e.getMessage());
                e.printStackTrace();
            }
            carrier = new Carrier(tableName,contentValues,constraint);
        }
        return carrier;
    }

    public static List<Carrier> builds(List list){
        List<Carrier> carrierList = new ArrayList<>();
        for (Object obj : list) {
            carrierList.add(build(obj));
        }
        return carrierList;
    }

    private static StringBuffer createConstraint(StringBuffer constraint,Field[] declaredFields){
        for (Field declaredField : declaredFields) {
            Pk pk = declaredField.getDeclaredAnnotation(Pk.class);
            if(null != pk){
                constraint.append(declaredField.getName());
            }
        }
        return constraint;
    }


    public static void handledPojoInstance(Object pojoInstance) throws PoJoException {
        Class<?> aClass = pojoInstance.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        if(null==declaredFields||declaredFields.length==0){
            throw new PoJoException("无法映射属性",aClass);
        }
        if(null != declaredFields&&declaredFields.length>0){
            boolean flag = false;
            for (Field declaredField : declaredFields) {
                Pk pk = declaredField.getDeclaredAnnotation(Pk.class);
                flag = (null != pk);
                if(flag){
                    declaredField.setAccessible(true);
                    try {
                        Object o = declaredField.get(pojoInstance);
                        if(null == o){
                            throw new PoJoException("主键"+declaredField.getName()+"的值不能为空！",aClass);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } finally {
                        declaredField.setAccessible(false);
                    }
                    break;
                }
            }
            if(!flag){
                throw new PoJoException("主键属性无法映射，因为不存在该属性！",aClass);
            }
        }
    }
}

