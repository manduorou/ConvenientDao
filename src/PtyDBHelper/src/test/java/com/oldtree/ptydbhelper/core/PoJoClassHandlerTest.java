package com.oldtree.ptydbhelper.core;

import com.oldtree.ptydbhelper.entity.Room;
import com.oldtree.ptydbhelper.exception.PoJoException;
import com.oldtree.ptydbhelper.property.TableProperty;
import org.junit.Test;

public class PoJoClassHandlerTest {

    @Test
    public void test1(){
        PoJoClassHandler poJoClassHandler = new PoJoClassHandler();
        try {
            TableProperty tableProperties = poJoClassHandler.createOneTableProperties(Room.class);
            String sql = PropertyResolver.resolvedPropertyToCreate(tableProperties);
            System.out.println(sql);
            String toDrop = PropertyResolver.resolvedPropertyToDrop(tableProperties);
            System.out.println(toDrop);
        } catch (PoJoException e) {
            e.printStackTrace();
        }
    }
}