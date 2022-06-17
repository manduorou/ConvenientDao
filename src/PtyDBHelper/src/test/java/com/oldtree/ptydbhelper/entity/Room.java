package com.oldtree.ptydbhelper.entity;

import com.oldtree.ptydbhelper.core.NotNull;
import com.oldtree.ptydbhelper.core.Pk;
import com.oldtree.ptydbhelper.core.PoJo;

/**
 * 详细介绍类的情况.
 *
 * @ClassName Room
 * @Author oldTree
 * @Date 2022/4/29
 * @Version 1.0
 */
@PoJo("roomTable")
public class Room {
    @NotNull
    @Pk(autoincrement = true)
    private Long roomId;
    private String username;
    private char sex;
    private Double balance;
    private float sum;
    private Float ss;




    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public Float getSs() {
        return ss;
    }

    public void setSs(Float ss) {
        this.ss = ss;
    }

}

