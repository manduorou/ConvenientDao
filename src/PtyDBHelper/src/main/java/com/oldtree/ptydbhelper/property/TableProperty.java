package com.oldtree.ptydbhelper.property;

import java.util.Objects;
import java.util.Set;

/**
 * 表属性实体,单个表单主键.
 *
 * @ClassName TableProperty
 * @Author oldTree
 * @Date 2022/4/22
 * @Version 1.0
 */
public class TableProperty {

    private String tabName;
    private Set<Column> columns;
    private Set<TableProperty> sonTab;

    public Set<Column> getColumns() {
        return columns;
    }

    public void setColumns(Set<Column> columns) {
        this.columns = columns;
    }

    public Set<TableProperty> getSonTab() {
        return sonTab;
    }

    public void setSonTab(Set<TableProperty> sonTab) {
        this.sonTab = sonTab;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    /**
     * 内部字段类，可同类型，不可同字段名
     */
    public static class Column{
        //位置
        private int position;
        //列名
        private String columnName;
        //列数据类型
        private String columnType;
        //是否主键
        private boolean isPk;
        //是否允空
        private boolean canNull;
        //其他
        private String other;

        private String sort;

        public Column(int position, String columnName, String columnType, boolean isPk, boolean canNull, String other, String sort) {
            this.position = position;
            this.columnName = columnName;
            this.columnType = columnType;
            this.isPk = isPk;
            this.canNull = canNull;
            this.other = other;
            this.sort = sort;
        }

        public boolean isPk() {
            return isPk;
        }

        public void setPk(boolean pk) {
            isPk = pk;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getColumnType() {
            return columnType;
        }

        public void setColumnType(String columnType) {
            this.columnType = columnType;
        }

        public boolean isCanNull() {
            return canNull;
        }

        public void setCanNull(boolean canNull) {
            this.canNull = canNull;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Column column = (Column) o;
            return position == column.position && canNull == column.canNull && Objects.equals(columnType, column.columnType) && Objects.equals(other, column.other) && Objects.equals(sort, column.sort);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, columnName, columnType, canNull, other, sort);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableProperty that = (TableProperty) o;
        return Objects.equals(columns, that.columns) && Objects.equals(sonTab, that.sonTab);
    }
    @Override
    public int hashCode() {
        return Objects.hash(tabName, columns, sonTab);
    }
}

