package com.oldtree.convenientdao.dao;
import com.oldtree.convenientdao.core.Dao;
import com.oldtree.convenientdao.query.Query;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.List;
import java.util.Map;

/**
 * Dao的基层接口，实现本接口可重写所有的方法
 * @ClassName ConvenientDao<T>
 * @Author oldTree
 * @Date 2022/5/1
 * @Version 1.0
 */
@Dao
public interface ConvenientDao<T> {
    //各种表操作的接口-------------------------

    /**
     * 创建表
     */
    public void createTable();
    /**
     * 删除表
     */
    public void dropTable();
    /**
     * 判断表是否存在
     * @return 存在则返回true，否则返回false
     */
    public boolean tableIsExists();
    /**
     * 将表托管给托管对象处理，这样做的好处就是不用每次模型变化了，
     * 但是表的结构还没有变化，导致得重删库或者清理缓存等繁琐不必要的操作。
     * 如果你是一个偷懒的家伙，你就需要将实现本接口的dao对应的表加入托管，则可以免去你这些烦恼！
     * @return 如果当前表不是处于托管状态，则返回true，否则返回false
     */
    public boolean entrusted();
    /**
     * 清除表托管
     */
    public void clearTheTrust();


    //插入数据-------------------------------

    /**
     * 保存一个对象的实例，前提是这个对象的无参数Constructor存，
     * 并且getter和setter是标准的。
     * @param pojo
     * @Version 1.0.5
     * @return 如果保存成功，则返回true，反之返回false
     */
    public boolean saveOne(T pojo);
    /**
     * 插入一条数据，返回响应行数
     * @param pojo
     * @return 返回响应的行数
     */
    public long insert(T pojo);
    /**
     * 批量保存List<T/>容器里面的数据，
     * 本方法采用的是多线程执行insert方法，
     * 在底层已经开启了数据库的事务的操作，
     * 无须在外部进行事务处理等。
     * @param list
     * @Version 1.0.5
     * @return 如果全部保存成功，才会返回true，否则事务回滚，返回false
     */
    public boolean saveList(List<T> list);
    /**
     * 批量保存数据
     * @param list 携带需要保存数据的集合
     * @return 全部保存成功返回true，反之事务回滚，返回false
     */
    public boolean insertList(List<T> list);


    //删除数据-------------------------------
    /**
     * 根据主键删除
     * @param fk
     * @Version 1.0.5
     * @return 删除成功返回true，否则返回false
     */
    public boolean deleteByFk(@NotNull Object fk);
    public long delete(Object fk);
    /**
     * 根据多个主键进行批量删除
     * @Version 1.0.5
     * @param fks
     * @return 全部删除成功后返回true，否则事务回滚，返回false
     */
    public boolean deleteByFks(Object...fks);
    public boolean delete(Object...fks);
    /**
     * 删除当前表中的所有数据
     * @return 返回一个存放数据库执行响应行数的list<Long/>集合
     */
    public boolean deleteAll();
    /**
     * 移除当前表下面的所有数据
     * @return 移除成功后返回true
     */
    public long removeAll();


    //修改数据-------------------------------
    /**
     * 根据实例的主键,更新一条数据
     * @param pojo
     * @Version 1.0.5
     * @return 更新成功则返回true
     */
    public boolean updateOne(T pojo);
    /**
     * 根据实例修改一条数据
     * @param pojo
     * @return
     */
    public long update(T pojo);

    //下面是各种查询方法-----------------------

    /**
     * 根据设置的@Pk主键属性值去查询数据，返回一个匹配的实体
     * @param fk
     * @return 查询成功返回实体对象，否则返回null
     */
    public T findByFk(@NotNull Object fk);

    //TODO 1.1.0版本新增
    public T findByColName(@NotNull String colName, Object value);

    //TODO 1.1.0版本新增
    public T findByQueryMap(@NotNull Map queryMap);

    //TODO 1.1.0版本新增
    public T findByCondition(@NotNull String condition,String...args);

    //TODO 1.1.0版本新增
    public T find(@Nullable String condition, Object... args);

    //TODO 1.1.0版本新增
    public T find(@Nullable String restrict , Map queryMap);

    //TODO 1.1.0版本新增
    public T findHead();

    //TODO 1.1.0版本新增
    public T findTail();

    /**
     * 返回当前dao所对应表的所有的数据，封装在集合里面
     * @return 如果有数据则返回一个有数据的List<T/>，否则返回null
     */
    public List<T> findAll();

    //TODO 1.1.0版本新增
    public List<T> findList(@Nullable String condition, Object... args);

    //TODO 1.1.0版本新增
    public List<T> findList(@Nullable String restrict , Map queryMap);

    /**
     *
     * @param ColName
     * @param condition
     * @param value
     * @return
     */
    public List<T> selectByColName(Object ColName,String condition,Object value);

    public List<T> selectByColIndex(Integer ColIndex,String condition,Object value);

    public List<T> selectByCols(String conditions, Map map);

    /**
     * 根据map的key对应数据库表的列名，value等于需要query的值来查询
     * @param map 一组条件对应的map，这里条件固定为"="
     * @return 查询到数据返回一个List集合，否则返回null
     */
    public List<T> selectByMap(Map map);

    /**
     * 根据query组成条件进行查询
     * 大多数都是依赖此接口进行的操作
     * @version 1.0.0
     * @param query
     * @return 查询到数据则返回一个有数据的List<T/> ，否则返回null
     */
    public List<T> getList(Query query);


    //各种函数操作----------------------------
    /**
     * 直接对当前的表数据进行计数
     * @return 如果表内有数据，则返回数据的数量，如果表不存在则会跑出异常，存在但无数据返回0
     */
    public long dataCount();
    /**
     * 根据列名计数
     * @param ColNames 多个列的名称
     * @return 如果有对应的数据，返回数据的数量，否则返回0
     */
    public long count(Object...ColNames);
    /**
     * 根据多个列的index得到对应的数据量
     * @param ColIndexes 可变数量个列的索引值
     * @return 如果有对应的数据，返回数据的数量，否则返回0
     */
    public long count(Integer...ColIndexes);
    /**
     * 根据传递的列名,得到最大值的对象实例。
     * @param ColName
     * @return 如果存在数据，则返回数据对应列的为最大值的数据，不存在数据则返回null。
     */
    public T max(Object ColName);
    /**
     * 根据传递的列的index值,得到最大值的对象实例。
     * @param ColIndex
     * @return 如果存在数据，则返回数据对应列的为最大值的数据，不存在数据则返回null。
     */
    public T max(Integer ColIndex);
    /**
     * 根据传递的列名,得到最小值的对象实例。
     * @param ColName
     * @return 如果存在数据，则返回数据对应列的为最小值的数据，不存在数据则返回null。
     */
    public T min(Object ColName);
    /**
     * 根据传递的列名,得到最小值的对象实例。
     * @param ColIndex
     * @return 如果存在数据，则返回数据对应列的为最小值的数据，不存在数据则返回null。
     */
    public T min(Integer ColIndex);

}
