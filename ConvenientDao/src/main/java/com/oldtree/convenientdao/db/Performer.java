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
    public long update(Carrier...carriers);
    public long update(List<Carrier> carriers);
    public long delete(Carrier carrier);
    public long delete(Carrier...carriers);
    public long delete(List<Carrier> carriers);
    public boolean deleteAll(String tableName);
    public long deleteAll(Class<E> cls);

    public List<E> rawQuery(Query query);

    public Cursor query(Query query);

    public Cursor mQuery(Class cls,String conditions,String...args);

    public List<E> queryList(String conditions,Object...args);

    public long query(String sql);

    public List<E> mQuery(String sql);

    public E queryOne(String conditions,Object...args);

    //1.1.5版本内容,回调
    public interface callBack<E>{
        public void success (E data);
        public void error(Throwable throwable);
    }
}
