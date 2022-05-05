package com.oldtree.ptydbhelper.stmt;

import android.content.ContentValues;
import com.oldtree.ptydbhelper.core.Pk;
import com.oldtree.ptydbhelper.exception.PoJoException;
import com.oldtree.ptydbhelper.utils.TableUtils;
import java.lang.reflect.Field;

/**
 * 详细介绍类的情况.
 *
 * @ClassName CarrierBuilder
 * @Author oldTree
 * @Date 2022/4/28
 * @Version 1.0
 */
public class CarrierBuilder<T> {
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
        String valueOfField = "";
        if(null!=declaredFields&&declaredFields.length>0){
            for (Field declaredField : declaredFields) {
                try {
                    declaredField.setAccessible(true);
                    Object value = declaredField.get(object);
                    if(null!=value){
                        valueOfField = value.toString();
                    }
                    //放到容器里
                    contentValues.put(declaredField.getName(),valueOfField);
                    declaredField.setAccessible(false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
//            条件生成！
            constraint = createConstraint(constraint,declaredFields);
            carrier = new Carrier(TableUtils.getTableName(aClass).toUpperCase(),contentValues,constraint);
        }
        return carrier;
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
            throw  new PoJoException("无法映射属性",aClass);
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

