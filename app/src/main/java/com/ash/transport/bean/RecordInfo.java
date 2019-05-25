package com.ash.transport.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/*----------------------------------------------*
 * @package:   com.ash.transport.bean
 * @fileName:  RecordInfo.java
 * @describe:  充值记录实体类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-23 17:11
 *----------------------------------------------*/
@DatabaseTable(tableName = "tb_record")
public class RecordInfo {

    // id 字段 主键 自动增长
    @DatabaseField(generatedId = true, useGetSet = true, columnName = "id")
    private int id;

    // 充值者用户名 字段
    @DatabaseField(useGetSet = true, columnName = "user_name")
    private String userName;

    // 充值小车编号 字段
    @DatabaseField(useGetSet = true, columnName = "car_id")
    private int carId;

    // 充值金额 字段
    @DatabaseField(useGetSet = true, columnName = "money")
    private int money;

    // 充值时间 字段
    @DatabaseField(useGetSet = true, columnName = "date")
    private String chargeDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(String chargeDate) {
        this.chargeDate = chargeDate;
    }

    // 为OrmLite库重写toString()方法
    @Override
    public String toString() {
        return "tb_record{" +
                "id=" + id +
                ", user_name='" + userName + "\'" +
                ", car_id=" + carId +
                ", money=" + money +
                ", date='" + chargeDate + "\'" +
                "}";
    }


}
