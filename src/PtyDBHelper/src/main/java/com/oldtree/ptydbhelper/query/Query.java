package com.oldtree.ptydbhelper.query;

import com.oldtree.ptydbhelper.property.Property;
import com.oldtree.ptydbhelper.utils.TableUtils;
import java.util.List;

/**
 * 核心类，处理所有的已有的sql条件
 * @ClassName Query
 * @Author oldTree
 * @Date 2022/4/22
 * @Version 1.0
 */
public class Query {

    /**
     * select *
     */
    private StringBuffer head;
    /**
     * form往后
     */
    private StringBuffer constraint;
    /**
     * where往后
     */
    private StringBuffer conditions;
    /**
     * asc desc等排序
     */
    private StringBuffer end;
    //属性集合
    private List<Property> propertyList;
    //映射实例类型
    private Class<?> cls;

    public Query(StringBuffer head, StringBuffer constraint, StringBuffer conditions, StringBuffer end, List<Property> propertyList, Class<?> cls) {
        this.head = head;
        this.constraint = constraint;
        this.conditions = conditions;
        this.end = end;
        this.propertyList = propertyList;
        this.cls = cls;
    }
    /**
     * 通过property对比
     * 匹配数据，大小写敏感
     * 通过字段匹配数据，大小写敏感
     * 但是会牺牲效率
     * @param property
     * @param value
     * @return
     */
    public Query eq(Property property , Object value){
        conditions.append(" ").append(property.columnName).append("='").append(value.toString()).append("' AND");
        return this;
    }

    public Query eq(String column,Object value){
        for (Property property : propertyList) {
            if(property.columnName.equals(column.toUpperCase())){
                conditions.append(" ").append(property.columnName).append("='").append(value.toString()).append("' AND");
            }
        }
        return this;
    }

    public Query eq(int indexOfField, Object value){
        conditions.append(" ").append(propertyList.get(indexOfField).columnName).append("='").append(value.toString()).append("' AND");
        return this;
    }

    /**
     * 大于操作，多个处理
     * @param property
     * @param value
     * @return
     */
    public Query gt(Property property ,Object value){
        conditions.append(" ").append(property.columnName).append(">'").append(value.toString()).append("' AND");
        return this;
    }

    public Query gt(String column , Object value){
        for (Property property : propertyList){
            if(property.columnName.equals(column.toUpperCase())){
                conditions.append(" ").append(property.columnName).append(">'").append(value.toString()).append("' AND");
            }
        }
        return this;
    }
    public Query gt(int indexOfField , Object value){
        conditions.append(" ").append(propertyList.get(indexOfField).columnName).append(">'").append(value.toString()).append("' AND");
        return this;
    }

    /**
     * 小于
     * @param property
     * @param value
     * @return
     */
    public Query lt(Property property ,Object value){
        conditions.append(" ").append(property.columnName).append("<'").append(value.toString()).append("' AND");
        return this;
    }

    public Query lt(String column , Object value){
        for (Property property : propertyList){
            if(property.columnName.equals(column.toUpperCase())){
                conditions.append(" ").append(property.columnName).append("<'").append(value.toString()).append("' AND");
            }
        }
        return this;
    }
    public Query lt(int indexOfField , Object value){
        conditions.append(" ").append(propertyList.get(indexOfField).columnName).append("<'").append(value.toString()).append("' AND");
        return this;
    }

    /**
     * 或者
     * @return
     */
    public Query or(){
        conditions.append("OR");
        return this;
    }
    /**
     * 简单模糊查询
     * 适用通配符%
     * @param property
     * @param value
     * @return
     */
    public Query like(Property property ,Object value){
        conditions.append(property.columnName).append(" LIKE '%").append(value.toString()).append("%' AND");
        return this;
    }
    public Query like(String column , Object value){
        for (Property property : propertyList) {
            if(property.columnName.equals(column.toUpperCase())){
                conditions.append(property.columnName).append(" LIKE '%").append(value.toString()).append("%' AND");
            }
        }
        return this;
    }
    public Query like(int indexOfField , Object value){
        conditions.append(propertyList.get(indexOfField).columnName).append(" LIKE '%").append(value.toString()).append("%' AND");
        return this;
    }

    public Query getAll(){
        conditions.append(" 1=1");
        return this;
    }
    /**
     * %为任意字符
     * _作为占位符
     * 暂时不支持这个，后续版本着重处理本模块
     * 可适用于复杂条件的模糊查询
     * @return
     */
    @Deprecated
    public Query likePlus(){
        return this;
    }

    /**
     *  分页查询
     * @param contentNum 每页数量
     * @param pagination 页码
     * @return
     */
    public Query limit(Integer contentNum,Integer pagination){
        if(null!=contentNum){
            end.append(" LIMIT ").append(contentNum);
        }
        if(null!=pagination){
            end.append(" OFFSET ").append(pagination);
        }
        return this;
    }

    /**
     * 在 SELECT 语句中，GROUP BY 子句放在 WHERE 子句之后
     * 放在 ORDER BY 子句之前。
     * @param properties
     * @return
     */
    public Query groupBy(Property... properties){
        end.append(" GROUP BY");
        for (Property property : properties) {
            end.append(" ").append(property.columnName);
        }
        return this;
    }

    /**
     * 在一个查询中，HAVING 子句必须放在 GROUP BY 子句之后，
     * 必须放在 ORDER BY 子句之前.
     * 否则就不让使用having子句
     * @param query
     * @return
     */
    public Query having(Query query){
        int orderBy = end.indexOf("ORDER BY");
        int groupBy = end.indexOf("GROUP BY");
        if(-1==orderBy){
            end.append(" HAVING").append( query.getConditions() );
        }
        if(-1 != groupBy){
            //把having这个语句插入到order by语句之前;

        }
        return  this;
    }

    /**
     * SQLite 的 ORDER BY 子句是用来基于一个或多个列按升序或降序顺序排列数据。
     * @param sortFlag
     * @param properties
     * @return
     */
    public Query orderBy(Boolean sortFlag,Property... properties){
        end.append(" ORDER BY");
        for (Property property : properties) {
            end.append(" ").append(property.columnName).append(",");
        }
        return setSort(sortFlag);
    }

    public Query orderBy(Boolean sortFlag, int... indexesOfField){
        end.append(" ORDER BY");
        for (int index : indexesOfField) {
            end.append(" ").append(propertyList.get(index).columnName).append(",");
        }
        return setSort(sortFlag);
    }

    /**
     * null不处理
     * true正序排列
     * false倒序排列
     * 其余不处理
     * @param flag
     * @return
     */
    private Query setSort(Boolean flag){
        if(null!=flag){
            if(flag){
                end.append(" ASC");
            }else {
                end.append(" DESC");
            }
        }
        return this;
    }

    /**
     * 连接查询，常用内连接
     * 使用using减少sql赘余字段长度
     * 内部连接查询,重新生成head，处理constraint
     * @param parentCls
     * @return
     */
    public Query join(Class<?> parentCls,Object... fks){
        constraint = new StringBuffer();
        constraint.append(" FROM");
        constraint = innerConnected(constraint,cls,parentCls);
        constraint.append(" USING (");
        for (Object fk : fks) {
            constraint.append(" ").append(fk.toString()).append(",");
        }
        constraint.append(")");
        int i = constraint.lastIndexOf(",");
        constraint.replace(i,i+1,"");
        return this;
    }

    /**
     * 内连俩表处理
     * @param constraint
     * @param cls
     * @param parent
     * @return
     */
    private StringBuffer innerConnected(StringBuffer constraint , Class<?> cls , Class<?> parent){
        String clsTableName = TableUtils.getTableName(cls);
        constraint.append(" ").append(clsTableName).append(" JOIN ");
        String parentTableName = TableUtils.getTableName(parent);
        constraint.append(parentTableName);
        return constraint;
    }

    /**
     * 关键字扩展
     * such as SQLite 的 DISTINCT 关键字与 SELECT 语句一起使用，
     * 来消除所有重复的记录，并只获取唯一一次记录。
     * 有可能出现一种情况，在一个表中有多个重复的记录。
     * 当提取这样的记录时，DISTINCT 关键字就显得特别有意义，它只获取唯一一次记录，而不是获取重复记录。
     */
    @Deprecated
    public Query masterKey(String str){
        return this;
    }

    public StringBuffer getHead() {
        return head;
    }

    public void setHead(StringBuffer head) {
        this.head = head;
    }

    public StringBuffer getConstraint() {
        return constraint;
    }

    public void setConstraint(StringBuffer constraint) {
        this.constraint = constraint;
    }

    public List<Property> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }

    public StringBuffer getEnd() {
        return end;
    }

    public void setEnd(StringBuffer end) {
        this.end = end;
    }

    public StringBuffer getConditions() {
        return conditions;
    }

    public void setConditions(StringBuffer conditions) {
        this.conditions = conditions;
    }
}

