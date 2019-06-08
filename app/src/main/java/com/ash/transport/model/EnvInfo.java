package com.ash.transport.model;

/*----------------------------------------------*
 * @package:   com.ash.transport.bean
 * @fileName:  EnvInfo.java
 * @describe:  环境信息实体类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-25 16:34
 *----------------------------------------------*/
public class EnvInfo {
    private int temp;       // 温度
    private int pm;         // pm2.5
    private int co2;        // 二氧化碳
    private int light;      // 光照强度
    private int hum;        // 湿度

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getPm() {
        return pm;
    }

    public void setPm(int pm) {
        this.pm = pm;
    }

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getHum() {
        return hum;
    }

    public void setHum(int hum) {
        this.hum = hum;
    }
}
