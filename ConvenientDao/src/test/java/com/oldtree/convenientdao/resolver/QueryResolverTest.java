package com.oldtree.convenientdao.resolver;

import com.oldtree.convenientdao.model.Course;
import com.oldtree.convenientdao.query.Query;
import com.oldtree.convenientdao.query.QueryBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueryResolverTest {

    @Test
    public void test1(){
        Query build = QueryBuilder.build(Course.class);
        System.out.println(QueryResolver.resolvedQuery(build));
    }

}