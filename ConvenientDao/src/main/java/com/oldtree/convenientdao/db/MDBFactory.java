package com.oldtree.convenientdao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.oldtree.convenientdao.async.TheadPool;
import com.oldtree.convenientdao.utils.DateUtils;
import com.oldtree.convenientdao.utils.PoJoClassHandler;
import com.oldtree.convenientdao.exception.ConfigException;
import com.oldtree.convenientdao.exception.PoJoException;
import com.oldtree.convenientdao.property.ConfigProperty;
import com.oldtree.convenientdao.property.TableProperty;
import com.oldtree.convenientdao.query.Query;
import com.oldtree.convenientdao.resolver.QueryResolver;
import com.oldtree.convenientdao.stmt.Carrier;
import com.oldtree.convenientdao.log.ConvenientDaoLog;
import com.oldtree.convenientdao.resolver.PropertyResolver;
import com.oldtree.convenientdao.utils.TableUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 详细介绍类的情况.
 *
 * @ClassName PtyDBFactory
 * @Author oldTree
 * @Date 2022/4/28
 * @Version 1.0
 */
public class MDBFactory implements TableFactory {

    private PoJoClassHandler poJoClassHandler;
    private ConfigProperty configProperty;
    private Performer performer;
    private DBOpenHelper dbOpenHelper;
    public final static String TABLE_QUERY_SQL = "select count(*) as c from sqlite_master where type ='table' and name ='%s' limit(1);";

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


    private MDBFactory(){
        try {
            poJoClassHandler = new PoJoClassHandler();
            configProperty = ConfigProperty.getConfigProperty();
            dbOpenHelper = DBOpenHelper.getInstance(configProperty);
            performer = new DBPerFormer(dbOpenHelper);
        } catch (ConfigException e) {
            ConvenientDaoLog.e(e.getMessage());
            e.printStackTrace();
        }
    }

    public static MDBFactory build(){
        return new MDBFactory();
    }

    @Override
    public void createTable(SQLiteDatabase sqLiteDatabase, TableProperty tableProperty) {
        ConvenientDaoLog.d("正在创建表<"+tableProperty.getTabName()+">");
        String sql = PropertyResolver.resolvedPropertyToCreate(tableProperty);
        ConvenientDaoLog.d(sql);
        sqLiteDatabase.execSQL(sql);
        ConvenientDaoLog.d("<<<<<创建成功>>>>>");
    }

    @Override
    public void dropTable(SQLiteDatabase sqLiteDatabase, TableProperty tableProperty) {
        ConvenientDaoLog.d("正在删除表<"+tableProperty.getTabName()+">");
        String sql = PropertyResolver.resolvedPropertyToDrop(tableProperty);
        ConvenientDaoLog.d(sql);
        sqLiteDatabase.execSQL(sql);
        ConvenientDaoLog.d("<<<<<删除成功>>>>>");
    }

    /**
     * 数据库文件存在，但是不是属于升级时，
     * 可直接在某些仅使用一次的地方调用该方法，
     * 会自动根据类的各个属性生成表！
     * @param pojoClass
     * @Version 1.0.4
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
     * @Version 1.0.4
     */
    public void createAllTables(SQLiteDatabase sqLiteDatabase) throws Exception {
        if(configProperty==null){
            ConvenientDaoLog.e("创建所有表存在配置未装载错误，无法映射数据库");
            throw  new ConfigException("配置类没有装载");
        }
        Set<Class<?>> classSet = poJoClassHandler.getClassArrayByProperty(configProperty);
        if(null!=classSet&&classSet.size()>0){
            Set<TableProperty> allTablesProperties = poJoClassHandler.createAllTablesProperties(classSet);
            for (TableProperty tableProperty : allTablesProperties) {
                createTable(sqLiteDatabase,tableProperty);
            }
        }
        ConvenientDaoLog.v("This database was initialized successfully!!!!"+new Date());
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
     * 根据class删除表
     * @param sqLiteDatabase
     * @param aClass
     */
    public void dropTableByClass( SQLiteDatabase sqLiteDatabase , Class<?> aClass){
        try {
            TableProperty oneTableProperties = poJoClassHandler.createOneTableProperties(aClass);
            dropTable(sqLiteDatabase,oneTableProperties);
        } catch (PoJoException e) {
            e.printStackTrace();
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
        private Cursor cursor;
        private List<Long> rowsList;
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
            return insert(carrier)>0L;
        }
        /**
         * TODO 这里应该开启多线程操作
         * 批量保存,开启批量操作
         * @param carrierSet
         * @return
         */

        @Override
        public boolean saveList(Set<Carrier> carrierSet) {
            boolean flag = false;
            sqLiteDatabase = dbOpenHelper.getWritableDatabase();
            if(!sqLiteDatabase.inTransaction()){
                sqLiteDatabase.beginTransaction();
                if(null!=carrierSet&&carrierSet.size()>0){
                    for (Carrier carrier : carrierSet) {
                        flag = sqLiteDatabase.insert(carrier.getTableName(), "", carrier.getContentValues())>0L;
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
            return update(carrier)>0L;
        }

        /**
         * 逻辑保存，暂时不推荐这个，功能没有去完善
         * @param carrier
         * @return
         */
        @Deprecated
        @Override
        public boolean saveOrUpdate(Carrier carrier) {
            long rows = -1L;
            sqLiteDatabase = dbOpenHelper.getWritableDatabase();
            rows = sqLiteDatabase.update(carrier.getTableName(), carrier.getContentValues(), carrier.getConstraint().toString(), null);
            ConvenientDaoLog.d("saveOrReplace<"+carrier.getContentValues()+">\tresponse rows："+rows+"行");
            dbOpenHelper.close();
            return rows>0;
        }

        /**
         * 删除
         * @param carrier
         * @return
         */
        @Override
        public boolean deleteByCarrier(Carrier carrier) {
            return delete(carrier)>0L;
        }

        @Override
        public long insert(Carrier carrier) {
            sqLiteDatabase = dbOpenHelper.getWritableDatabase();
            long rows = sqLiteDatabase.insert(carrier.getTableName(),"", carrier.getContentValues());
            ConvenientDaoLog.d("saveOne<"+carrier.getContentValues()+">\tresponse rows："+rows+"行");
            dbOpenHelper.close();
            return rows;
        }

        //TODO 做一个
        @Override
        public boolean insertList(List<Carrier> carrierSet) {
            return false;
        }

        @Override
        public long update(Carrier carrier) {
            sqLiteDatabase = dbOpenHelper.getWritableDatabase();
            long rows = sqLiteDatabase.update(carrier.getTableName(), carrier.getContentValues(), carrier.getConstraint().toString(), null);
            ConvenientDaoLog.d("update<"+carrier.getContentValues()+">\tresponse rows："+rows+"行");
            dbOpenHelper.close();
            return rows;
        }

        @Override
        public long update(Carrier... carriers) {
            List<Long> list = new ArrayList<>();
            for (Carrier carrier : carriers) {
                list.add(update(carrier));
            }
            return 0L;
        }

        @Override
        public long update(List<Carrier> carriers) {
            rowsList = new ArrayList<>();
            for (Carrier carrier : carriers) {
                rowsList.add(update(carrier));
            }
            return 0L;
        }

        @Override
        public long delete(Carrier carrier) {
            sqLiteDatabase = dbOpenHelper.getWritableDatabase();
            long rows = sqLiteDatabase.delete(carrier.getTableName(),carrier.getConstraint().toString(),null);
            ConvenientDaoLog.d("delete<"+carrier.getContentValues()+">\tresponse rows："+rows+"行"+new Date());
            dbOpenHelper.close();
            return rows;
        }

        @Override
        public long delete(Carrier... carriers) {
            rowsList = new ArrayList<>();
            for (Carrier carrier : carriers) {
                rowsList.add(delete(carrier));
            }
            return 0L;
        }

        @Override
        public long delete(List<Carrier> carriers) {
            rowsList = new ArrayList<>();
            for (Carrier carrier : carriers) {
                rowsList.add(delete(carrier));
            }
            return 0L;
        }

        @Override
        public boolean deleteAll(String tableName) {
            sqLiteDatabase = dbOpenHelper.getWritableDatabase();
            long rows = sqLiteDatabase.delete(tableName,null,null);
            ConvenientDaoLog.d("deleteAll!!<"+tableName+">\tresponse rows："+rows+"行");
            dbOpenHelper.close();
            return rows>0L;
        }

        @Override
        public long deleteAll(Class<E> cls) {
            long rows = 0L;
            try {
                String tableName = TableUtils.getTableName(cls);
                sqLiteDatabase = dbOpenHelper.getWritableDatabase();
                rows = sqLiteDatabase.delete(tableName,null,null);
                ConvenientDaoLog.d("deleteAll!!<"+tableName+">\tresponse rows："+rows+"行");
                dbOpenHelper.close();
            } catch (PoJoException e) {
                e.printStackTrace();
            }
            return rows;
        }

        /**
         * 反射获取实例化对象
         * @param query
         * @return list
         */
        @Override
        public List<E> rawQuery(Query query) {
            List list = null;
            String sql = QueryResolver.resolvedQuery(query);
            ConvenientDaoLog.d(sql);
            sqLiteDatabase = dbOpenHelper.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
            if(cursor!=null&&cursor.getCount()>0){
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
            ConvenientDaoLog.d("response："+query.getCls().getSimpleName()+"类型的数据《"+list+"》");
            dbOpenHelper.close();
            return list;
        }

        /**
         * 这个操作后续需要关闭数据库,避免性能消耗
         * @param query
         * @return 返回一个游标
         */
        @Override
        public Cursor query(Query query) {
            String sql = QueryResolver.resolvedQuery(query);
            ConvenientDaoLog.d(sql);
            sqLiteDatabase = dbOpenHelper.getReadableDatabase();
            cursor = sqLiteDatabase.rawQuery(sql, null);
            return cursor;
        }

        @Override
        public Cursor mQuery(Class cls,String conditions, String... args) {
            sqLiteDatabase = dbOpenHelper.getReadableDatabase();
            try {
                String tableName = TableUtils.getTableName(cls);
                cursor = sqLiteDatabase.query(tableName,null,conditions,args,null,null,null,null);
                return cursor;
            } catch (PoJoException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public List<E> queryList(String conditions, Object... args) {


            return null;
        }

        @Override
        public long query(String sql) {

            return 0;
        }

        @Override
        public List<E> mQuery(String sql) {


            return null;
        }

        @Override
        public E queryOne(String conditions, Object... args) {


            return null;
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
                            field.set(newInstance,cursor.getString(index));
                            ConvenientDaoLog.w("属性为："+fieldName+"，暂时无法映射对应属性类型：《"+fieldTypeSimpleName+"》类型的数据类型，所以这里映射成了TEXT类型");
                            throw new RuntimeException("无法映射对应属性"+fieldTypeSimpleName+"类型的数据类型");
                    }
                    field.setAccessible(false);
                }
            }
            return newInstance;
        }
    }
}

