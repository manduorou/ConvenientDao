package com.oldtree.ptydbhelper.core;


import com.oldtree.ptydbhelper.query.Query;
import com.oldtree.ptydbhelper.stmt.Carrier;

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
    public List<E> findByQuery(Query query);
}
