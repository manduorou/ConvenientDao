package com.oldtree.ptydbhelper.core;

public enum Condition {
    EQ("eq"),GT("gt"),Lt("lt"),LK("lk"),JN("join"),IN("in");
    private String key;
    Condition(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }
}
