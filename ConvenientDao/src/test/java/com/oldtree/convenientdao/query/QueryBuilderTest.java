package com.oldtree.convenientdao.query;

import com.oldtree.convenientdao.model.Course;
import com.oldtree.convenientdao.resolver.QueryResolver;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueryBuilderTest {

    @Test
    public void test1(){
        Query query = QueryBuilder.buildToFun(Course.class,"*");
        String sql = QueryResolver.resolvedQuery(query);
        System.out.println(sql);
    }
}