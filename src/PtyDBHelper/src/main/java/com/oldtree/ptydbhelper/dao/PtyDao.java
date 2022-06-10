package com.oldtree.ptydbhelper.dao;

import com.oldtree.ptydbhelper.query.Query;
import com.oldtree.ptydbhelper.stmt.Carrier;

import java.util.List;

/**
 * 详细介绍接口.
 *
 * @ClassName PtyDao
 * @Author oldTree
 * @Date 2022/5/1
 * @Version 1.0
 */
public interface PtyDao<T> {
    public boolean saveOne(T pojoInstance);
    public boolean saveList(List<T> list);
    public boolean updateOne(T pojoInstance);
    public boolean deleteByFk(Object fk);
    public T findByFk(Object fk);
    public List<T> getList(Query query);
}
