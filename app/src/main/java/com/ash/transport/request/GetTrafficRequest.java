package com.ash.transport.request;

import android.content.Context;

import com.ash.transport.config.AppConfig;
import com.ash.transport.model.TrafficInfo;

import org.json.JSONException;
import org.json.JSONObject;

/*----------------------------------------------*
 * @package:   com.ash.transport.request
 * @fileName:  GetTrafficRequest.java
 * @describe:  红绿灯信息请求类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-26 21:32
 * @继承关系:   GetTrafficRequest ← BaseRequest
 *----------------------------------------------*/
public class GetTrafficRequest extends BaseRequest {
    private int roadId;      // 定义 道路ID：1~4

    // 定义 构造方法
    public GetTrafficRequest(Context context) {
        super(context);     // 使用超类的构造方法
    }

    // 定义 设置小车ID 方法
    public void setRoadId(int roadId) {
        this.roadId = roadId;
    }

    // 重写抽象方法 返回请求后部地址
    @Override
    protected String getAddress() {

        //  AppConfig.GET_LIGHT_MSG 请求得到指定道路红绿灯信息
        return AppConfig.GET_LIGHT_MSG;
    }

    // 重写抽象方法 拼接请求参数 返回json格式字符串
    @Override
    protected String getParams() {
        JSONObject json = new JSONObject();

        try {

            json.put(AppConfig.KEY_TRAFFIC_LIGHT_ID, roadId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();     // "{'RoadId':1}"
    }

    // 重写抽象方法 解析响应结果json字符串 返回指定值
    @Override
    protected Object analyzeResponse(String responseString) {

        TrafficInfo traffic = new TrafficInfo();

        try {
            JSONObject json = new JSONObject(responseString);
            traffic.setRoadId(roadId);
            traffic.setRedTime(json.optInt(AppConfig.KEY_LIGHT_RED));
            traffic.setYellowTime(json.optInt(AppConfig.KEY_LIGHT_YELLOW));
            traffic.setGreenTime(json.optInt(AppConfig.KEY_LIGHT_GREEN));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return traffic;
    }
}
