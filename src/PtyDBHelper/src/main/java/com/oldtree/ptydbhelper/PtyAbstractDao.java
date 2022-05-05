package com.oldtree.ptydbhelper;

import android.database.sqlite.SQLiteDatabase;
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
public abstract class PtyAbstractDao<T,E> {
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
    private void mappingTable(Class<?> cls){
        SQLiteDatabase database = ptyDBFactory.getDbOpenHelper().getWritableDatabase();
        ptyDBFactory.createTableByClass(database,cls);
    }
    /**
     * 保存无须条件
     * @param element
     * @return
     */
    public boolean saveOne(T element){
        Carrier carrier = CarrierBuilder.build(element);
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
    public boolean updateOne(Object pojoInstance){
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
    public boolean saveList(List<E> list){
        boolean flag = false;
        Set<Carrier> set = null;
        if(null!=list&&list.size()>0){
            set = new LinkedHashSet<>();
            Carrier carrier = null;
            for (E e : list) {
                carrier = CarrierBuilder.build(e);
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
    public List<E> getList(Query query){
        return ptyDBFactory.getPerformer().findByQuery(query);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    public boolean deleteById(Integer id){
        Object instance = null;
        try {
            instance =  aClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Carrier carrier = CarrierBuilder.build(instance);
        carrier.getConstraint().append(" ='").append(id).append("'");
        return ptyDBFactory.getPerformer().deleteByCarrier(carrier);
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public T findById(Integer id){
        Property pkProperty = null;
        try {
            pkProperty = ptyDBFactory.getPoJoClassHandler().createPkProperty(aClass);
        } catch (PoJoException e) {
            e.printStackTrace();
        }
        Query query = QueryBuilder.build(aClass)
                .eq(pkProperty,id);
        List<E> list = ptyDBFactory.getPerformer().findByQuery(query);
        T instance = null;
        if(null!=list&&list.size()>0){
            instance = (T) list.get(0);
        }
        return instance;
    }

    /**
     * 查询所有
     * @return
     */
    public List<E> findAll(){
        Query query = QueryBuilder.build(aClass).getAll();
        return ptyDBFactory.getPerformer().findByQuery(query);
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
}

