package com.oldtree.convenientdao.dao;

import com.oldtree.convenientdao.query.Query;

import java.util.List;
import java.util.Map;

/**
 * 如果你是一个比较懒的开发者，
 * 这个类会帮到你做一些基础开发中你所需要做的事情
 * 继承自AbstractDao{@linkplain com.oldtree.convenientdao.dao.AbstractDao<T>}
 * @ClassName BaseDao
 * @Author oldTree
 * @Date 2022/6/21
 * @Version 1.0.5
 */
public class BaseDao<T> extends AbstractDao<T>{

    /**
     * BaseDao<T>的构造方法，详情可查{@linkplain com.oldtree.convenientdao.dao.AbstractDao<T>}
     * @param aClass 这个类是一个被@POJO注解的类，如果不是，会抛出异常
     */
    public BaseDao(Class<?> aClass) {
        super(aClass);
        //表不存在则创建表
        if(!tableIsExists()){
            createTable();
        }
    }

    @Override
    protected void mappingTable() {
        super.mappingTable();
    }

    @Override
    protected void dropTheMappedTable() {
        super.dropTheMappedTable();
    }

    @Override
    public boolean tableIsExists() {
        return super.tableIsExists();
    }

    @Override
    public boolean entrusted() {
        return super.entrusted();
    }

    @Override
    public void clearTheTrust() {
        super.clearTheTrust();
    }

    @Override
    public void createTable() {
        super.createTable();
    }

    @Override
    public void dropTable() {
        super.dropTable();
    }

    @Override
    public boolean saveOne(T pojo) {
        return super.saveOne(pojo);
    }

    @Override
    public long insert(T pojo) {
        return super.insert(pojo);
    }

    @Override
    public boolean saveList(List<T> list) {
        return super.saveList(list);
    }

    @Override
    public boolean insertList(List<T> list) {
        return super.insertList(list);
    }

    @Override
    public List<T> getList(Query query) {
        return super.getList(query);
    }

    @Override
    public boolean deleteByFk(Object fk) {
        return super.deleteByFk(fk);
    }

    @Override
    public long delete(Object fk) {
        return super.delete(fk);
    }

    @Override
    public boolean delete(Object... fks) {
        return super.delete(fks);
    }

    @Override
    public long removeAll() {
        return super.removeAll();
    }

    @Override
    public boolean deleteByFks(Object... fks) {
        return super.deleteByFks(fks);
    }

    @Override
    public boolean deleteAll() {
        return super.deleteAll();
    }

    @Override
    public boolean updateOne(T pojo) {
        return super.updateOne(pojo);
    }

    @Override
    public long update(T pojo) {
        return super.update(pojo);
    }

    @Override
    public T findByFk(Object fk) {
        return super.findByFk(fk);
    }

    @Override
    public List<T> findAll() {
        return super.findAll();
    }

    @Override
    public List<T> selectByColumn(Object columnName, String condition, Object value) {
        return super.selectByColumn(columnName, condition, value);
    }

    @Override
    public long count(Object... columns) {
        return super.count(columns);
    }

    @Override
    public long dataCount() {
        return super.dataCount();
    }

    @Override
    public long count(Integer... columnIndexes) {
        return super.count(columnIndexes);
    }

    @Override
    public List<T> selectByColumnIndex(Integer columnIndex, String condition, Object value) {
        return super.selectByColumnIndex(columnIndex, condition, value);
    }

    @Override
    public List<T> selectByColumns(String condition, Map map) {
        return super.selectByColumns(condition, map);
    }

    @Override
    public List<T> selectByMap(Map map) {
        return super.selectByMap(map);
    }

    @Override
    public T max(Object columnName) {
        return super.max(columnName);
    }

    @Override
    public T max(Integer columnIndex) {
        return super.max(columnIndex);
    }

    @Override
    public T min(Object columnName) {
        return super.min(columnName);
    }

    @Override
    public T min(Integer columnIndex) {
        return super.min(columnIndex);
    }
}

