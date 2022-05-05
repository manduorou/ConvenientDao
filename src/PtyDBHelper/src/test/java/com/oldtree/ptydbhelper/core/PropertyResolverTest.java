package com.oldtree.ptydbhelper.core;

import com.oldtree.ptydbhelper.entity.User;
import com.oldtree.ptydbhelper.exception.PoJoException;
import com.oldtree.ptydbhelper.property.TableProperty;
import org.junit.Test;

import static org.junit.Assert.*;

public class PropertyResolverTest {
    @Test
    public void test1(){
        PoJoClassHandler joClassHandler = new PoJoClassHandler();
        try {
            TableProperty tableProperties = joClassHandler.createOneTableProperties(User.class);
            String propertyToCreate = PropertyResolver.resolvedPropertyToCreate(tableProperties);
            System.out.println(propertyToCreate);
            String propertyToDrop = PropertyResolver.resolvedPropertyToDrop(tableProperties);
            System.out.println(propertyToDrop);
        } catch (PoJoException e) {
            e.printStackTrace();
        }
    }

}