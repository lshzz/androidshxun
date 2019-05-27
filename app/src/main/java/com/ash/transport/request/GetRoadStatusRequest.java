package com.ash.transport.request;

import android.content.Context;

import com.ash.transport.config.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

/*----------------------------------------------*
 * @package:   com.ash.transport.request
 * @fileName:  GetRoadStatusRequest.java
 * @describe:  查询道路状态请求类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-25 21:13
 * @继承关系:   GetRoadStatusRequest ← BaseRequest
 *----------------------------------------------*/
public class GetRoadStatusRequest extends BaseRequest {
    private int roadId;      // 定义 道路ID：1~5

    // 定义 构造方法
    public GetRoadStatusRequest(Context context) {
        super(context);     // 使用超类的构造方法
    }

    // 定义 设置道路ID 方法
    public void setRoadId(int roadId) {
        this.roadId = roadId;
    }

    // 重写抽象方法 返回请求后部地址
    @Override
    protected String getAddress() {

        //  AppConfig.GET_ROAD_STATION_INFO 请求得到指定道路状态
        return AppConfig.GET_ROAD_STATION_INFO;
    }

    // 重写抽象方法 拼接请求参数 返回json格式字符串
    @Override
    protected String getParams() {
        JSONObject json = new JSONObject();

        try {

            json.put(AppConfig.KEY_ROAD_ID,roadId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();     // "{'RoadId':1}"
    }

    // 重写抽象方法 解析响应结果json字符串 返回指定值
    @Override
    protected Object analyzeResponse(String responseString) {
        int status = 1;
        try {
            JSONObject jsonObject = new JSONObject(responseString);
            status = jsonObject.optInt(AppConfig.KEY_STATUS);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return status;
    }
}
