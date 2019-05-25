package com.ash.transport.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ash.transport.bean.RecordInfo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/*----------------------------------------------*
 * @package:   com.ash.transport.dao
 * @fileName:  DatabaseHelper.java
 * @describe:  数据库访问器基类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-23 16:59
 * @继承关系:   DatabaseHelper ← [OrmLiteSqliteOpenHelper]
 *----------------------------------------------*/
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    // 数据库名称
    public static final String DATABASE_NAME = "transport.db";

    // 本类的单例实例
    private static DatabaseHelper instance;

    // 存储APP中所有的DAO对象的Map集合
    private Map<String, Dao> daos = new HashMap<>();

    // 获取本类单例对象的方法
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null) {
                    instance = new DatabaseHelper(context);
                }
            }
        }
        return instance;
    }

    // 私有的构造方法
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // 根据传入的DAO的路径获取到这个DAO的单例对象
    // (要么从daos这个Map中获取，要么新创建一个并存入daos)
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    // 重写数据库创建事件
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, RecordInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 重写数据库版本更新创建事件
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            // 删表重建 (注意：记录将会一并删除)
            TableUtils.dropTable(connectionSource, RecordInfo.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 重写数据库关闭事件
    @Override
    public void close() {
        super.close();
        // 释放Dao集合资源
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}