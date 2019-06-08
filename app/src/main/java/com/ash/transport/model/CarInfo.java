package com.ash.transport.model;

/*----------------------------------------------*
 * @package:   com.ash.transport.bean
 * @fileName:  CarInfo.java
 * @describe:  车辆信息实体类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-22 20:12
 *----------------------------------------------*/
public class CarInfo {
    private String carId;           // 车辆编号
    private String make;            // 品牌型号
    private String engine;          // 发动机
    private String frame;           // 车架
    private String type;            // 车辆类型
    private String licenseType;     // 牌照类型
    private String licenseNum;      // 车牌号码
    private String vioNum;          // 违章次数
    private String points;          // 总扣分
    private String fine;            // 总罚款

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getLicenseNum() {
        return licenseNum;
    }

    public void setLicenseNum(String licenseNum) {
        this.licenseNum = licenseNum;
    }

    public String getVioNum() {
        return vioNum;
    }

    public void setVioNum(String vioNum) {
        this.vioNum = vioNum;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }
}
