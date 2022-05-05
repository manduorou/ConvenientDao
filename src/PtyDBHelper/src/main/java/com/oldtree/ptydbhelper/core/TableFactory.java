package com.oldtree.ptydbhelper.core;

import android.database.sqlite.SQLiteDatabase;
import com.oldtree.ptydbhelper.property.TableProperty;

public interface TableFactory {
    public void createTable(SQLiteDatabase sqLiteDatabase, TableProperty tableProperty);
    public void dropTable(SQLiteDatabase sqLiteDatabase, TableProperty tableProperty);
}
