package com.oldtree.convenientdao.db;

import android.database.sqlite.SQLiteDatabase;
import com.oldtree.convenientdao.property.TableProperty;

public interface TableFactory {
    public void createTable(SQLiteDatabase sqLiteDatabase, TableProperty tableProperty);
    public void dropTable(SQLiteDatabase sqLiteDatabase, TableProperty tableProperty);
}
