package com.oldtree.convenientdao.model;

import com.oldtree.convenientdao.core.Pk;
import com.oldtree.convenientdao.core.PoJo;

/**
 * 详细介绍类的情况.
 *
 * @ClassName Course
 * @Author oldTree
 * @Date 2022/6/21
 * @Version 1.0
 */
@PoJo
public class Course {
    @Pk
    private String id;
    private String name;
    private Long number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}

