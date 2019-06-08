package com.ash.transport.model;

/*----------------------------------------------*
 * @package:   com.ash.transport.bean
 * @fileName:  TrafficInfo.java
 * @describe:  红绿灯信息实体类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-26 21:38
 *----------------------------------------------*/
public class TrafficInfo {
    private int roadId;         // 路口编号
    private int redTime;        // 红灯时长
    private int yellowTime;     // 黄灯时长
    private int greenTime;      // 绿灯时长

    public int getRoadId() {
        return roadId;
    }

    public void setRoadId(int roadId) {
        this.roadId = roadId;
    }

    public int getRedTime() {
        return redTime;
    }

    public void setRedTime(int redTime) {
        this.redTime = redTime;
    }

    public int getYellowTime() {
        return yellowTime;
    }

    public void setYellowTime(int yellowTime) {
        this.yellowTime = yellowTime;
    }

    public int getGreenTime() {
        return greenTime;
    }

    public void setGreenTime(int greenTime) {
        this.greenTime = greenTime;
    }
}
