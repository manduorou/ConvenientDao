package com.oldtree.ptydbhelper.core;

import com.oldtree.ptydbhelper.entity.Room;
import com.oldtree.ptydbhelper.property.Property;
import com.oldtree.ptydbhelper.query.Query;
import com.oldtree.ptydbhelper.query.QueryBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueryResolverTest {


    /**
     * 测试自动sql
     */
    @Test
    public void test1(){
        Query query = QueryBuilder.build(Room.class);
        query.eq(new Property(0,Integer.class,"roomId",false,"ROOMID"),"1 or 1=1")
                .eq(new Property(0,Integer.class,"username",false,"USERNAME"),"王八")
                .lt(new Property(0,Integer.class,"userid",false,"USERID"),1);
        String resolvedQuery = QueryResolver.resolvedQuery(query);
        System.out.println(resolvedQuery);
    }
}