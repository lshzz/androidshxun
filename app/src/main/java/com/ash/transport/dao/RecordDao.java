package com.ash.transport.dao;

import android.content.Context;

import com.ash.transport.model.RecordInfo;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*----------------------------------------------*
 * @package:   com.ash.transport.dao
 * @fileName:  RecordDao.java
 * @describe:  充值记录表 访问器类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-23 17:10
 *----------------------------------------------*/
public class RecordDao {
    private Context context;

    // ORMLite提供的DAO类对象
    // 第一个泛型是要操作的数据表映射成的实体类
    // 第二个泛型是这个实体类中ID的数据类型
    private Dao<RecordInfo, Integer> dao;

    // 构造方法
    public RecordDao(Context context) {
        this.context = context;
        try {
            // 从数据库访问基类的单例对象获取Dao
            this.dao = DatabaseHelper.getInstance(context).getDao(RecordInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 向RecordInfo表中添加一条数据
    public void insert(RecordInfo data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除RecordInfo表中的一条数据
    public void delete(RecordInfo data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改RecordInfo表中的一条数据
    public void update(RecordInfo data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询RecordInfo表中的所有数据
    public List<RecordInfo> selectAll() {
        List<RecordInfo> users = null;
        try {
            users = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // 根据ID取出对应的RecordInfo对象
    public RecordInfo queryById(int id) {
        RecordInfo depositRecord = null;
        try {
            depositRecord = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return depositRecord;
    }

    // 根据username取出信息
    public List<RecordInfo> queryByUserId(String name) {
        List<RecordInfo> depositRecords = new ArrayList<RecordInfo>();
        try {
            depositRecords = dao.queryBuilder().where().eq("user_id", name).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return depositRecords;
    }

}
