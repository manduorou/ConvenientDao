package com.oldtree.ptydbhelper.entity;

import com.oldtree.ptydbhelper.core.NotNull;
import com.oldtree.ptydbhelper.core.Pk;
import com.oldtree.ptydbhelper.core.PoJo;

/**
 * 详细介绍类的情况.
 *
 * @ClassName User
 * @Author oldTree
 * @Date 2022/4/28
 * @Version 1.0
 */
@PoJo("tab_User")
public class User {
    @Pk
    private Integer userId;
    @NotNull
    private String username;

    @NotNull
    private String password;

    private Integer age;

    public User() {
    }

    public User(Integer userId, String username, String password, Integer age) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

