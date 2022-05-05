package com.oldtree.ptydbhelper.dao;

import android.database.sqlite.SQLiteDatabase;
import com.oldtree.ptydbhelper.core.DBOpenHelper;
import com.oldtree.ptydbhelper.core.PtyDBFactory;
import com.oldtree.ptydbhelper.exception.ConfigException;
import com.oldtree.ptydbhelper.property.ConfigProperty;
import com.oldtree.ptydbhelper.property.Property;
import com.oldtree.ptydbhelper.property.TableProperty;

/**
 * 详细介绍类的情况.
 *
 * @ClassName RoomDao
 * @Author oldTree
 * @Date 2022/4/29
 * @Version 1.0
 */
public class RoomDao {

    public static final Property roomId = new Property(0,Integer.class,"roomId",false,"ROOMID");

    public RoomDao(){

    }


}

