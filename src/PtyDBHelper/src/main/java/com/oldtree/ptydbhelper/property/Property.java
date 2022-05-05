package com.oldtree.ptydbhelper.property;

import java.util.Objects;

/**
 * 详细介绍类的情况.
 *
 * @ClassName PoJoProperty
 * @Author oldTree
 * @Date 2022/4/19
 * @Version 1.0
 */
public class Property {

    public final int position;
    public final Class<?> type;
    public final String name;
    public final boolean primaryKey;
    public final String columnName;

    public Property(int position, Class<?> type, String name, boolean primaryKey, String columnName) {
        this.position = position;
        this.type = type;
        this.name = name;
        this.primaryKey = primaryKey;
        this.columnName = columnName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return position == property.position && primaryKey == property.primaryKey && Objects.equals(type, property.type) && Objects.equals(name, property.name) && Objects.equals(columnName, property.columnName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, type, name, primaryKey, columnName);
    }

    @Override
    public String toString() {
        return "Property{" +
                "position=" + position +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", primaryKey=" + primaryKey +
                ", columnName='" + columnName + '\'' +
                '}';
    }
}

