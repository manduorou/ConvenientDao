package com.oldtree.ptydbhelper.dao;

import android.database.sqlite.SQLiteDatabase;
import com.oldtree.ptydbhelper.core.Condition;
import com.oldtree.ptydbhelper.core.Pk;
import com.oldtree.ptydbhelper.core.PtyDBFactory;
import com.oldtree.ptydbhelper.exception.PoJoException;
import com.oldtree.ptydbhelper.property.Property;
import com.oldtree.ptydbhelper.property.TableProperty;
import com.oldtree.ptydbhelper.query.Query;
import com.oldtree.ptydbhelper.query.QueryBuilder;
import com.oldtree.ptydbhelper.stmt.Carrier;
import com.oldtree.ptydbhelper.stmt.CarrierBuilder;
import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 向外提供模版dao
 * @ClassName PtyAbstractDao
 * @Author oldTree
 * @Date 2022/5/1
 * @Version 1.0
 */
public abstract class PtyAbstractDao<T> implements PtyDao<T> {
    private PtyDBFactory ptyDBFactory;
    private Class<?> aClass;
    public PtyAbstractDao(Class<?> aClass) {
        ptyDBFactory = PtyDBFactory.build();
        this.aClass = aClass;
        mappingTable(this.aClass);
    }
    /**
     * 映射表,这里我写死了
     * @param cls
     */
    protected void mappingTable(Class<?> cls){
        SQLiteDatabase database = ptyDBFactory.getDbOpenHelper().getWritableDatabase();
        ptyDBFactory.createTableByClass(database,cls);
    }

    /**
     * 删除当前被映射的表
     */
    protected void dropTheMappedTable(){
        SQLiteDatabase database = ptyDBFactory.getDbOpenHelper().getWritableDatabase();
        try {
            TableProperty oneTableProperties = ptyDBFactory.getPoJoClassHandler().createOneTableProperties(aClass);
            ptyDBFactory.dropTable(database,oneTableProperties);
        } catch (PoJoException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存无须条件
     * @param pojoInstance
     * @return
     */
    @Override
    public boolean saveOne(T pojoInstance){
        Carrier carrier = CarrierBuilder.build(pojoInstance);
        return ptyDBFactory.getPerformer().saveOne(carrier);
    }

    /**
     * 得到主键的列名
     * 更新需要先查询出原来的数据
     * 获取element上面的主键declaredField
     * 通过declaredField调用get方法的到值，加入到constraint内部拼接数据
     * @param pojoInstance
     * @return
     */
    @Override
    public boolean updateOne(T pojoInstance){
        try {
            CarrierBuilder.handledPojoInstance(pojoInstance);
        } catch (PoJoException e) {
            e.printStackTrace();
        }
        Carrier carrier = CarrierBuilder.build(pojoInstance);
        Class<?> aClass = pojoInstance.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        try {
            if(null != declaredFields&&declaredFields.length>0){
                for (Field declaredField : declaredFields) {
                    Pk pk = declaredField.getDeclaredAnnotation(Pk.class);
                    if(null!=pk){
                        declaredField.setAccessible(true);
                        Object value = declaredField.get(pojoInstance);
                        if(null != value){
                            carrier.getConstraint().append(" = '").append(value.toString()).append("'");
                        }
                        declaredField.setAccessible(false);
                        break;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return ptyDBFactory.getPerformer().updateOne(carrier);
    }


    /**
     * 批量保存
     * @param list
     * @return
     */
    @Override
    public boolean saveList(List<T> list){
        boolean flag = false;
        Set<Carrier> set = null;
        if(null!=list&&list.size()>0){
            set = new LinkedHashSet<>();
            Carrier carrier = null;
            for (T t : list) {
                carrier = CarrierBuilder.build(t);
                set.add(carrier);
            }
            flag = ptyDBFactory.getPerformer().saveList(set);
        }
        return flag;
    }

    /**
     * 通过query查询list数据集合
     * @param query
     * @return
     */
    @Override
    public List<T> getList(Query query){
        return ptyDBFactory.getPerformer().findByQuery(query);
    }

    /**
     * 根据id删除
     * @param fk
     * @return
     */
    @Override
    public boolean deleteByFk(Object fk){
        Object instance = null;
        try {
            instance =  aClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Carrier carrier = CarrierBuilder.build(instance);
        carrier.getConstraint().append(" ='").append(fk).append("'");
        return ptyDBFactory.getPerformer().deleteByCarrier(carrier);
    }
    /**
     * 根据id查询
     * @param fk
     * @return
     */
    @Override
    public T findByFk(Object fk){
        Property pkProperty = null;
        try {
            pkProperty = ptyDBFactory.getPoJoClassHandler().createPkProperty(this.aClass);
        } catch (PoJoException e) {
            e.printStackTrace();
        }
        Query query = QueryBuilder.build(this.aClass)
                .eq(pkProperty,fk);
        List<T> list = ptyDBFactory.getPerformer().findByQuery(query);
        T instance = null;
        if(null!=list&&list.size()>0){
            instance = list.get(0);
        }
        return instance;
    }

    /**
     * 查询所有
     * @return
     */
    public List<T> findAll(){
        Query query = QueryBuilder.build(aClass).getAll();
        return ptyDBFactory.getPerformer().findByQuery(query);
    }
    /**
     * 根据列名查询
     */
    public List<T> findByColumn(Object columnName,String condition,Object value){
        Query query = QueryBuilder.build(aClass);
        switch (condition){
            case "=":
                query.eq(columnName.toString(),value);
                break;
            case ">":
                query.gt(columnName.toString(),value);
                break;
            case "<":
                query.lt(columnName.toString(),value);
                break;
            case "lk":
                query.like(columnName.toString(),value);
                break;
            default:
                throw new IllegalStateException("====>\tUnexpected value: " + condition);
        }
        if(null==query.getConditions()||query.getConditions().equals("")){
            throw new NullPointerException("====>\t所给的条件不符合: 可以选择\">\",\"<\",\"=\",\"like\"等");
        }
        return getList(query);
    }
}

