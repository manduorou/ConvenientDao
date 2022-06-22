package com.oldtree.convenientdao.db;


import android.database.Cursor;
import com.oldtree.convenientdao.query.Query;
import com.oldtree.convenientdao.stmt.Carrier;

import java.util.List;
import java.util.Set;

/**
 * 执行者.
 *
 * @ClassName Performer
 * @Author oldTree
 * @Date 2022/4/24
 * @Version 1.0
 */
public interface Performer<E> {
    public boolean saveOne(Carrier carrier);
    public boolean saveList(Set<Carrier> carrierSet);
    public boolean updateOne(Carrier carrier);
    public boolean saveOrUpdate(Carrier carrier);
    public boolean deleteByCarrier(Carrier carrier);
    public long insert(Carrier carrier);
    public boolean insertList(List<Carrier> carrierList);
    public long update(Carrier carrier);
    public List<Long> update(Carrier...carriers);
    public List<Long> update(List<Carrier> carriers);
    public long delete(Carrier carrier);
    public List<Long> delete(Carrier...carriers);
    public List<Long> delete(List<Carrier> carriers);
    public boolean deleteAll(String tableName);
    public long deleteAll(Class<E> cls);
    public List<E> rawQuery(Query query);
    public Cursor query(Query query);
}
