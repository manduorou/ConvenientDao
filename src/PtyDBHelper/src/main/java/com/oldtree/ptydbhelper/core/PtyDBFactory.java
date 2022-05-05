package com.oldtree.ptydbhelper.core;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.oldtree.ptydbhelper.exception.ConfigException;
import com.oldtree.ptydbhelper.exception.PoJoException;
import com.oldtree.ptydbhelper.property.ConfigProperty;
import com.oldtree.ptydbhelper.property.TableProperty;
import com.oldtree.ptydbhelper.query.Query;
import com.oldtree.ptydbhelper.stmt.Carrier;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/**
 * 详细介绍类的情况.
 *
 * @ClassName PtyDBFactory
 * @Author oldTree
 * @Date 2022/4/28
 * @Version 1.0
 */
public class PtyDBFactory implements TableFactory{

    private PoJoClassHandler poJoClassHandler;
    private ConfigProperty configProperty;
    private Performer performer;
    private DBOpenHelper dbOpenHelper;

    public PoJoClassHandler getPoJoClassHandler() {
        return poJoClassHandler;
    }

    public ConfigProperty getConfigProperty() {
        return configProperty;
    }

    public Performer getPerformer() {
        return performer;
    }

    public DBOpenHelper getDbOpenHelper() {
        return dbOpenHelper;
    }


    public PtyDBFactory(){
        try {
            poJoClassHandler = new PoJoClassHandler();
            configProperty = ConfigProperty.getConfigProperty();
            dbOpenHelper = DBOpenHelper.getInstance(configProperty);
            performer = new DBPerFormer(dbOpenHelper);
        } catch (ConfigException e) {
            e.printStackTrace();
        }
    }

    public static PtyDBFactory build(){
        return new PtyDBFactory();
    }

    @Override
    public void createTable(SQLiteDatabase sqLiteDatabase, TableProperty tableProperty) {
        String sql = PropertyResolver.resolvedPropertyToCreate(tableProperty);
        Log.d("PitaYaDao",sql);
        sqLiteDatabase.execSQL(sql);
        Log.e("PitaYaDao","This table was created successfully!!!!");
    }

    @Override
    public void dropTable(SQLiteDatabase sqLiteDatabase, TableProperty tableProperty) {
        String sql = PropertyResolver.resolvedPropertyToDrop(tableProperty);
        Log.d("PitaYaDao",sql);
        Log.d("PtyDBHelper","Drop the table successfully！Was dropped the tableName:"+tableProperty.getTabName());
        sqLiteDatabase.execSQL(sql);
    }

    /**
     * 数据库文件存在，但是不是属于升级时，
     * 可直接在某些仅使用一次的地方调用该方法，
     * 会自动根据类的各个属性生成表！
     * @param pojoClass
     */
    public void createTableIfDBExists(Class<?> pojoClass){
        try {
            TableProperty tableProperties = poJoClassHandler.createOneTableProperties(pojoClass);
            SQLiteDatabase sqLiteDatabase = dbOpenHelper.getWritableDatabase();
            createTable(sqLiteDatabase,tableProperties);
            sqLiteDatabase.close();
        } catch (PoJoException e) {
            e.printStackTrace();
        }
    }
    /**
     * 创建所有表
     * @param sqLiteDatabase
     * @throws ConfigException
     */
    public void createAllTables(SQLiteDatabase sqLiteDatabase) throws Exception {
        if(configProperty==null){
            Log.e("PitaYaDao","创建所有表存在配置装载错误");
            throw  new ConfigException("配置类没有装载");
        }
        Set<Class<?>> classSet = poJoClassHandler.getClassArrayByProperty(configProperty);
        if(null!=classSet&&classSet.size()>0){
            Set<TableProperty> allTablesProperties = poJoClassHandler.createAllTablesProperties(classSet);
            for (TableProperty tableProperty : allTablesProperties) {
                createTable(sqLiteDatabase,tableProperty);
            }
        }
        Log.e("PitaYaDao","This databases was initialized successfully!!!!");
    }

    /**
     * 删除所有表
     * @param
     */
    public void dropAllTables(SQLiteDatabase sqLiteDatabase) throws Exception {
        Set<Class<?>>  classSet = poJoClassHandler.getClassArrayByProperty(configProperty);
        if(null!=classSet&&classSet.size()>0){
            Set<TableProperty> allTablesProperties = poJoClassHandler.createAllTablesProperties(classSet);
            for (TableProperty tableProperty : allTablesProperties) {
                dropTable(sqLiteDatabase,tableProperty);
            }
        }
    }

    /**
     * 可用于dao内部建立表
     * @param aClass
     */
    public void createTableByClass( SQLiteDatabase sqLiteDatabase , Class<?> aClass){
        try {
            TableProperty oneTableProperties = poJoClassHandler.createOneTableProperties(aClass);
            createTable(sqLiteDatabase,oneTableProperties);
        } catch (PoJoException e) {
            e.printStackTrace();
        }

    }
    /**
     * 内部类，CRUD语句执行者
     */
    public static class DBPerFormer<E> implements Performer<E>{
        private DBOpenHelper dbOpenHelper;
        private SQLiteDatabase sqLiteDatabase;

        public DBPerFormer(DBOpenHelper dbOpenHelper) {
            this.dbOpenHelper = dbOpenHelper;
        }

        /**
         * 单体保存
         * @param carrier
         * @return
         */
        @Override
        public boolean saveOne(Carrier carrier) {
            sqLiteDatabase = dbOpenHelper.getWritableDatabase();
            long rows = sqLiteDatabase.insert(carrier.getTableName(),"", carrier.getContentValues());
            dbOpenHelper.close();
            return rows>0;
        }
        /**
         * 批量保存,开启批量操作
         * @param carrierSet
         * @return
         */
        @Override
        public boolean saveList(Set<Carrier> carrierSet) {
            boolean flag =false;
            sqLiteDatabase = dbOpenHelper.getWritableDatabase();
            if(!sqLiteDatabase.inTransaction()){
                sqLiteDatabase.beginTransaction();
                if(null!=carrierSet&&carrierSet.size()>0){
                    for (Carrier carrier : carrierSet) {
                        flag = sqLiteDatabase.insert(carrier.getTableName(),"", carrier.getContentValues())>0;
                    }
                    if(flag){
                        sqLiteDatabase.setTransactionSuccessful();
                    }
                }
                sqLiteDatabase.endTransaction();
            }
            sqLiteDatabase.close();
            return flag;
        }

        /**
         * 保存一个
         * @param carrier
         * @return
         */
        @Override
        public boolean updateOne(Carrier carrier) {
            long update = -1L;
            sqLiteDatabase = dbOpenHelper.getWritableDatabase();
            update = sqLiteDatabase.update(carrier.getTableName(), carrier.getContentValues(), carrier.getConstraint().toString(), null);
            dbOpenHelper.close();
            return update>0;
        }

        /**
         * 逻辑保存，暂时不推荐这个，功能没有去完善
         * @param carrier
         * @return
         */
        @Deprecated
        @Override
        public boolean saveOrUpdate(Carrier carrier) {
            long update = -1L;
            sqLiteDatabase = dbOpenHelper.getWritableDatabase();
            update = sqLiteDatabase.update(carrier.getTableName(), carrier.getContentValues(), carrier.getConstraint().toString(), null);
            dbOpenHelper.close();
            return update>0;
        }

        /**
         * 删除
         * @param carrier
         * @return
         */
        @Override
        public boolean deleteByCarrier(Carrier carrier) {
            int delete =-1;
            sqLiteDatabase = dbOpenHelper.getWritableDatabase();
            delete = sqLiteDatabase.delete(carrier.getTableName(),carrier.getConstraint().toString(),null);
            dbOpenHelper.close();
            return delete>0L;
        }
        /**
         * 反射获取实例化对象
         * @param query
         * @return list
         */
        @Override
        public List<E> findByQuery(Query query) {
            List list = null;
            String queryRows = QueryResolver.resolvedQuery(query);
            Log.d("PitaYaDao",queryRows);
            sqLiteDatabase = dbOpenHelper.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(queryRows, null);
            if(cursor!=null){
                list = new ArrayList();
                while (cursor.moveToNext()){
                    Class<?> cls = query.getCls();
                    Field[] declaredFields = cls.getDeclaredFields();
                    try {
                        E newInstance = poJoFieldAutoSetter(declaredFields, cursor , query.getCls());
                        list.add(newInstance);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            dbOpenHelper.close();
            return list;
        }

        /**
         * 通过无参构造反射实例，对应类型调用set方法映射数据
         * @param fields
         * @param cls
         * @return object
         * @throws InstantiationException
         * @throws IllegalAccessException
         */
        private E poJoFieldAutoSetter(Field[] fields , Cursor cursor , Class<?> cls) throws InstantiationException, IllegalAccessException{
            E newInstance = (E) cls.newInstance();
            if(null!=fields&&fields.length>0){
                for (Field field : fields) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    String fieldTypeSimpleName = field.getType().getSimpleName();
                    int index = cursor.getColumnIndex(fieldName.toUpperCase());
                    switch (fieldTypeSimpleName){
                        case "Short":
                        case "short":
                            field.set(newInstance,cursor.getShort(index));
                            break;
                        case "Integer":
                        case "int":
                            field.set(newInstance,cursor.getInt(index));
                            break;
                        case "Long":
                        case "long":
                            field.set(newInstance,cursor.getLong(index));
                            break;
                        case "Float":
                        case "float":
                            field.set(newInstance,cursor.getFloat(index));
                            break;
                        case "double":
                        case "Double":
                            field.set(newInstance,cursor.getDouble(index));
                            break;
                        case "String":
                            field.set(newInstance,cursor.getString(index));
                            break;
                        case "Character":
                        case "char":
                            field.set(newInstance,cursor.getString(index).toCharArray()[0]);
                            break;
                        default:
                            throw new RuntimeException("无法映射对应属性"+fieldTypeSimpleName+"类型的数据类型");
                    }
                    field.setAccessible(false);
                }
            }
            return newInstance;
        }
    }
}

