package com.oldtree.ptydbhelper.core;

import com.oldtree.ptydbhelper.exception.PoJoException;
import com.oldtree.ptydbhelper.property.ConfigProperty;
import com.oldtree.ptydbhelper.property.Property;
import com.oldtree.ptydbhelper.property.TableProperty;
import com.oldtree.ptydbhelper.utils.TableUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 处理注解上pojo的类的各种操作，比如获取类中所有属性，包括注解上面的值
 * 封装所有应改生成的表
 * @ClassName PoJoClassHandle
 * @Author oldTree
 * @Date 2022/4/21
 * @Version 1.0
 */
public class PoJoClassHandler {
    //存放所有的被注解的类
    private Set<Class<?>> classSet;

    //所有的表,表名不能重复
    private Set<TableProperty> tableProperties;

    public PoJoClassHandler() {
    }

    public PoJoClassHandler(Set<Class<?>> classSet) {
        this.classSet = classSet;
    }

    public Set<Class<?>> getClassSet() {
        return classSet;
    }

    public void setClassSet(Set<Class<?>> classSet) {
        this.classSet = classSet;
    }

    public Set<TableProperty> getTableProperties() {
        return tableProperties;
    }

    public void setTableProperties(Set<TableProperty> tableProperties) {
        this.tableProperties = tableProperties;
    }

    public Set<Class<?>> getClassArrayByProperty(ConfigProperty configProperty){
        return getClassesByArray(configProperty.getPojoClasses());
    }
    private Set<Class<?>> getClassesByArray(Class[] classes){
        Set<Class<?>> classSet = null;
        if(null!=classes&&classes.length>0){
            classSet = new LinkedHashSet<>();
            for (Class aClass : classes) {
                classSet.add(aClass);
            }
        }
        return classSet;
    }
    /**
     * 存放class对应的column的property
     */
    public List<Property> createAllProperties(Class cls){
        List<Property> list = new ArrayList<>();
        //扫描类的属性等
        Field[] declaredFields = cls.getDeclaredFields();
        //判断属性数组
        if(null != declaredFields && declaredFields.length > 0){
            int i =0;
            for (Field field : declaredFields) {
                String fieldName = field.getName();
                list.add(new Property(i++,field.getType(),fieldName,false,fieldName.toUpperCase()));
            }
        }
        //返回
        return  null!=list&&list.size()>0? list:null;
    }

    public Property createPkProperty(Class cls) throws PoJoException {
        checkedThePojoWasHandled(cls);
        Field[] declaredFields = cls.getDeclaredFields();
        Field fkField = null;
        for (Field declaredField : declaredFields) {
            Pk annotation = declaredField.getDeclaredAnnotation(Pk.class);
            if(null != annotation){
                fkField = declaredField;
            }
        }
        if(null == fkField)
            throw  new PoJoException("",cls);
        return new Property(0,fkField.getType(),fkField.getName(),true, fkField.getName().toUpperCase());
    }
    /**
     * 主要功能：根据cls类的生成一个tableProperties
     * 详细：
     * 遍历cls类下面的所有field进行字段上面的注解处理
     * 主键必然不能接受null值，如果对应的field上面有NotNull注解，必须不是主键
     * 这里检测cls中的所有必要属性，比如pojo注解和pk注解是否加上了
     * @param cls
     * @return
     */
    public TableProperty createOneTableProperties(Class<?> cls) throws PoJoException {
        checkedThePojoWasHandled(cls);
        TableProperty tableProperty = new TableProperty();
        Set<TableProperty.Column> columnSet = new LinkedHashSet<>();
        String tableName = TableUtils.getTableName(cls).toUpperCase();
        tableProperty.setTabName(tableName);
        Field[] fields = cls.getDeclaredFields();
        if (fields.length>0){
            TableProperty.Column column=null;
            int i =0;
            for (Field field : fields) {
                column = new TableProperty.Column(i++, field.getName().toUpperCase(), field.getType().getSimpleName(), false, true, "", "");
                Pk pk = field.getDeclaredAnnotation(Pk.class);
                NotNull notNull = field.getDeclaredAnnotation(NotNull.class);
                if(pk!=null){
                    column.setPk(true);
                    if(pk.autoincrement()){
                        column.setOther("autoAdd");
                    }
                }
                if(notNull!=null&&pk==null||(pk!=null)) {
                    column.setCanNull(false);
                }
                columnSet.add(column);
            }
            tableProperty.setColumns(columnSet);
        }else {
            throw new PoJoException(cls.getSimpleName()+"类无法建立表,因为该类中Field不存在!",cls);
        }
        return tableProperty;
    }

    /**
     * 创建所有的tableProperties
     * @param classSet
     * @return
     * @throws PoJoException
     */
    public Set<TableProperty> createAllTablesProperties(Set<Class<?>> classSet) throws PoJoException {
        Set<TableProperty> set=null;
        if(null!=classSet){
            set = new LinkedHashSet<>();
            for (Class<?> aClass : classSet) {
                TableProperty oneTableProperties = createOneTableProperties(aClass);
                set.add(oneTableProperties);
            }
        }
        return set;
    }

    /**
     * 简单的检测作用！
     * @param aClass
     */
    private void checkedThePojoWasHandled(Class<?> aClass) throws PoJoException {

        PoJo poJo = aClass.getDeclaredAnnotation(PoJo.class);
        if(null == poJo){
            throw new PoJoException(aClass.getName()+"类PoJo注解没有加上，无法映射表",aClass);
        }
        boolean flag = false;
        int i = 0;
        for (Field declaredField : aClass.getDeclaredFields()) {
            Pk pk = declaredField.getDeclaredAnnotation(Pk.class);
            if(null!=pk){
                i++;
                flag=true;
                break;
            }
        }
        if(!flag){
            throw new PoJoException(aClass.getName()+"类无法映射表，因为该类没有声明pk主键",aClass);
        }
        if(i>1){
            throw new PoJoException(aClass.getName()+"无法映射含有多个Pk主键的表",aClass);
        }

    }

}

