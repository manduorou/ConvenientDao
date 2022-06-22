package com.oldtree.convenientdao.stmt;

import android.content.ContentValues;

/**
 * 详细介绍类的情况.
 *
 * @ClassName Carrier
 * @Author oldTree
 * @Date 2022/4/28
 * @Version 1.0
 */
public class Carrier {
    private String tableName;
    private ContentValues contentValues;
    private StringBuffer constraint;

    public StringBuffer getConstraint() {
        return constraint;
    }

    public void setConstraint(StringBuffer constraint) {
        this.constraint = constraint;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ContentValues getContentValues() {
        return contentValues;
    }

    public void setContentValues(ContentValues contentValues) {
        this.contentValues = contentValues;
    }


    public Carrier(String tableName, ContentValues contentValues, StringBuffer constraint) {
        this.tableName = tableName;
        this.contentValues = contentValues;
        this.constraint = constraint;
    }
}

