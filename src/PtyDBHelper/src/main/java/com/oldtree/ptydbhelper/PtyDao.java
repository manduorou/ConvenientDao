package com.oldtree.ptydbhelper;

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
public interface PtyDao<E> {
    public void mappingTable(Class<?> aClass);
    public boolean saveOne(Carrier carrier);
    public List<E> getList(Query query);
    public boolean saveList(List<Carrier> list);
    public boolean updateOne(Carrier carrier);
    public boolean deleteById(Class<?> cls,Integer id);
}
