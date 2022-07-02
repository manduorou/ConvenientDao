package com.oldtree.convenientdao.dao;

import com.oldtree.convenientdao.query.Query;
import com.sun.istack.internal.NotNull;

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
    public T findByColName(String colName, Object value) {
        return super.findByColName(colName, value);
    }

    @Override
    public List<T> findAll() {
        return super.findAll();
    }

    @Override
    public List<T> findList(String condition, Object... args) {
        return super.findList(condition, args);
    }

    @Override
    public List<T> findList(String restrict, Map queryMap) {
        return super.findList(restrict, queryMap);
    }

    @Override
    public List<T> selectByColName(Object ColName, String condition, Object value) {
        return super.selectByColName(ColName, condition, value);
    }

    @Override
    public T findByCondition(String conditions, String... args) {
        return super.findByCondition(conditions, args);
    }

    @Override
    public T find(String condition, Object... args) {
        return super.find(condition, args);
    }

    @Override
    public T find(String restrict, Map queryMap) {
        return super.find(restrict, queryMap);
    }

    @Override
    public T findHead() {
        return super.findHead();
    }

    @Override
    public T findTail() {
        return super.findTail();
    }

    @Override
    public T findByQueryMap(Map queryMap) {
        return super.findByQueryMap(queryMap);
    }

    @Override
    public long count(Object... Cols) {
        return super.count(Cols);
    }

    @Override
    public long dataCount() {
        return super.dataCount();
    }

    @Override
    public long count(Integer... ColIndexes) {
        return super.count(ColIndexes);
    }

    @Override
    public List<T> selectByColIndex(Integer ColIndex, String condition, Object value) {
        return super.selectByColIndex(ColIndex, condition, value);
    }

    @Override
    public List<T> selectByCols(String condition, Map map) {
        return super.selectByCols(condition, map);
    }

    @Override
    public List<T> selectByMap(Map map) {
        return super.selectByMap(map);
    }

    @Override
    public T max(Object ColName) {
        return super.max(ColName);
    }

    @Override
    public T max(Integer ColIndex) {
        return super.max(ColIndex);
    }

    @Override
    public T min(Object ColName) {
        return super.min(ColName);
    }

    @Override
    public T min(Integer ColIndex) {
        return super.min(ColIndex);
    }
}

