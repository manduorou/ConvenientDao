package com.oldtree.convenientdao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.oldtree.convenientdao.core.Dao;
import com.oldtree.convenientdao.core.Pk;
import com.oldtree.convenientdao.exception.PoJoException;
import com.oldtree.convenientdao.property.Property;
import com.oldtree.convenientdao.query.Query;
import com.oldtree.convenientdao.query.QueryBuilder;
import com.oldtree.convenientdao.stmt.Carrier;
import com.oldtree.convenientdao.stmt.CarrierBuilder;
import com.oldtree.convenientdao.db.MDBFactory;
import com.oldtree.convenientdao.log.ConvenientDaoLog;
import com.oldtree.convenientdao.utils.TableUtils;
import com.sun.istack.internal.NotNull;
import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 提供模版作用的dao，子类dao仅仅需要继承本类即可实现本类所实现的全部功能
 * 功能详情前往{@linkplain com.oldtree.convenientdao.dao.ConvenientDao}
 * 如果你的实际开发中，需要其他的功能，可以参照这些方法的方式去实现，
 * 如果因为你的类已经继承某些类无法再继承，谁让Java只能单继承呢，
 * 你可以选择ConvenientDao的接口{@linkplain com.oldtree.convenientdao.dao.ConvenientDao}
 * 方式去实例化一个BaseDao{@linkplain com.oldtree.convenientdao.dao.BaseDao<T>}
 * @ClassName AbstractDao<T>
 * @Author oldTree
 * @Date 2022/5/1
 * @Version 1.0.0
 */
@Dao
public abstract class AbstractDao<T> implements ConvenientDao<T> {
    private MDBFactory MDBFactory;
    private Class<?> aClass;

    /**
     * TODO 这里需要检查aClass的完整性
     * @param aClass
     */
    public AbstractDao(@NotNull Class<?> aClass){
        MDBFactory = MDBFactory.build();
        this.aClass = aClass;
    }
    protected void mappingTable(){
        createTable();
    }
    protected void dropTheMappedTable(){
        dropTable();
    }


    /**
     * 查询表是否存在，这个方法高效，推荐使用
     * @return 表如果已经建立，返回true
     */
    @Override
    public boolean tableIsExists() {
        SQLiteDatabase readableDatabase = null;
        try {
            String tableName = TableUtils.getTableName(this.aClass);
            String sql = String.format(MDBFactory.TABLE_QUERY_SQL,tableName.toUpperCase());
            readableDatabase = MDBFactory.getDbOpenHelper().getReadableDatabase();
            Cursor cursor = readableDatabase.rawQuery(sql, null);
            while (cursor.moveToNext()){
                int count = cursor.getInt(0);
                //如果count大于0，返回true,表示存在
                if(count>0){
                    return true;
                }
            }
        } catch (PoJoException e) {
            e.printStackTrace();
        } finally {
            if(null!=readableDatabase&&readableDatabase.isOpen()){
                readableDatabase.close();
            }
        }
        return false;
    }

    //TODO 2.0.0版本新内容,加入数据库伪热部署
    @Override
    public boolean entrusted() {


        return false;
    }

    //TODO 2.0.0版本新内容,清除伪热部署
    @Override
    public void clearTheTrust() {

    }

    /**
     * 调用本dao将会根据构造传递的Class所创建表，要求：类属性Class不为null并且满足基本要求
     */
    @Override
    public void createTable() {
        SQLiteDatabase database = MDBFactory.getDbOpenHelper().getWritableDatabase();
        MDBFactory.createTableByClass(database,this.aClass);
    }

    /**
     * 删除表，调用此方法，将会删除本dao对应的表
     */
    @Override
    public void dropTable() {
        SQLiteDatabase database = MDBFactory.getDbOpenHelper().getWritableDatabase();
        MDBFactory.dropTableByClass(database,this.aClass);
    }

    @Override
    public boolean saveOne(T pojo){
        Carrier carrier = CarrierBuilder.build(pojo);
        return MDBFactory.getPerformer().saveOne(carrier);
    }

    @Override
    public long insert(T pojo) {
        Carrier carrier = CarrierBuilder.build(pojo);
        return MDBFactory.getPerformer().insert(carrier);
    }

    @Override
    public boolean saveList(List<T> list){
        if(null==list||list.isEmpty()){
            throw new NullPointerException("传递的数据量不符合规范");
        }
        boolean flag = false;
        Set<Carrier> set = new LinkedHashSet<>();
        Carrier carrier = null;
        for (T t : list) {
            carrier = CarrierBuilder.build(t);
            set.add(carrier);
        }
        flag = MDBFactory.getPerformer().saveList(set);
        return flag;
    }

    @Override
    public boolean insertList(List<T> list) {
        if(null==list||list.isEmpty()){
            throw new NullPointerException("传递的数据量不符合规范");
        }
        List<Carrier> carrierList = CarrierBuilder.builds(list);
        boolean flag = MDBFactory.getPerformer().insertList(carrierList);
        return flag;
    }

    @Override
    public List<T> getList(Query query){
        return MDBFactory.getPerformer().rawQuery(query);
    }

    @Override
    public boolean deleteByFk(Object fk){
        Object instance = null;
        try {
            instance =  aClass.newInstance();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        Carrier carrier = CarrierBuilder.build(instance);
        carrier.getConstraint().append(" ='").append(fk).append("'");
        return MDBFactory.getPerformer().deleteByCarrier(carrier);
    }


    //TODO 根据主键删除数据返回响应结果
    @Override
    public long delete(Object fk) {
        Object instance = null;
        try {
            instance =  aClass.newInstance();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        Carrier carrier = CarrierBuilder.build(instance);
        carrier.getConstraint().append(" ='").append(fk).append("'");
        return MDBFactory.getPerformer().delete(carrier);
    }

    //TODO 根据主键删除，返回响应结果
    @Override
    public boolean delete(Object... fks) {

        return false;
    }

    @Override
    public long removeAll() {
        return MDBFactory.getPerformer().deleteAll(this.aClass);
    }

    //TODO 根据主键删除，返回响应结果
    @Override
    public boolean deleteByFks(Object... fks) {
        return false;
    }

    //TODO 删除对应表中所有的数据
    @Override
    public boolean deleteAll() {
        return MDBFactory.getPerformer().deleteAll(this.aClass)>0L;
    }

    @Override
    public boolean updateOne(T pojo){
        return update(pojo)>0L;
    }

    @Override
    public long update(T pojo) {
        try {
            CarrierBuilder.handledPojoInstance(pojo);
        } catch (PoJoException e) {
            e.printStackTrace();
        }
        Carrier carrier = CarrierBuilder.build(pojo);
        Class<?> aClass = pojo.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        try {
            if(null != declaredFields&&declaredFields.length>0){
                for (Field declaredField : declaredFields) {
                    Pk pk = declaredField.getDeclaredAnnotation(Pk.class);
                    if(null!=pk){
                        declaredField.setAccessible(true);
                        Object value = declaredField.get(pojo);
                        if(null != value){
                            carrier.getConstraint().append(" = '").append(value).append("'");
                        }
                        declaredField.setAccessible(false);
                        break;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return MDBFactory.getPerformer().update(carrier);
    }

    @Override
    public T findByFk(Object fk){
        Property pkProperty = null;
        try {
            pkProperty = MDBFactory.getPoJoClassHandler().createPkProperty(this.aClass);
        } catch (PoJoException e) {
            e.printStackTrace();
        }
        Query query = QueryBuilder.build(this.aClass)
                .eq(pkProperty,fk);
        List<T> list = MDBFactory.getPerformer().rawQuery(query);
        T instance = null;
        if(null!=list&&list.size()>0){
            instance = list.get(0);
        }
        return instance;
    }

    @Override
    public List<T> findAll(){
        Query query = QueryBuilder.build(aClass).getAll();
        return MDBFactory.getPerformer().rawQuery(query);
    }

    public List<T> selectByColumn(Object columnName,String condition,Object value){
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
                query.eq(columnName.toString(),value);
                ConvenientDaoLog.w("条件自动转换为<<\t=\t>>了");
                throw new IllegalStateException("====>\tUnexpected value: " + condition);
        }
        if(null==query.getConditions()||query.getConditions().equals("")){
            throw new NullPointerException("====>\t所给的条件不符合: 可以选择\">\",\"<\",\"=\",\"like\"等");
        }
        return getList(query);
    }

    // TODO 根据列名计数
    @Override
    public long count(Object... columns) {
        return 0;
    }

    //TODO 所有数据记数
    @Override
    public long dataCount() {
        return 0;
    }

    //TODO 根据多列的index进行计数
    @Override
    public long count(Integer... columnIndexes) {
        return 0;
    }

    //TODO 根据多列的index值的condition查询
    @Override
    public List<T> selectByColumnIndex(Integer columnIndex, String condition, Object value) {
        return null;
    }

    //TODO 根据前提条件的condition遍历map查询
    @Override
    public List<T> selectByColumns(String condition, Map map) {
        map.forEach((k,v)->{

        });
        return null;
    }

    //TODO 根据map的键值对对等查询查询
    @Override
    public List<T> selectByMap(Map map) {
        return null;
    }

    //TODO 获取对应列名最大的实例
    @Override
    public T max(Object columnName) {
        return null;
    }
    //TODO 获取对应列名最大的实例
    @Override
    public T max(Integer columnIndex) {
        return null;
    }
    //TODO 获取对应列名最小的实例
    @Override
    public T min(Object columnName) {
        return null;
    }
    //TODO 获取对应列名最小的实例
    @Override
    public T min(Integer columnIndex) {
        return null;
    }
}

