package com.ash.transport.request;

import android.content.Context;

import com.ash.transport.config.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*----------------------------------------------*
 * @package:   com.ash.transport.request
 * @fileName:  GetRoadStatusRequest.java
 * @describe:  查询公交站台请求类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-28 17:21
 * @继承关系:   GetBusStationRequest ← BaseRequest
 *----------------------------------------------*/
public class GetBusStationRequest extends BaseRequest {
    private int stationId;      // 定义 站台ID：1~2

    // 定义 构造方法
    public GetBusStationRequest(Context context) {
        super(context);     // 使用超类的构造方法
    }

    // 定义 设置道路ID 方法
    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    // 重写抽象方法 返回请求后部地址
    @Override
    protected String getAddress() {

        //  AppConfig.GET_BUS_STATION_INFO 请求得到指定公交站台信息
        return AppConfig.GET_BUS_STATION_INFO;
    }

    // 重写抽象方法 拼接请求参数 返回json格式字符串
    @Override
    protected String getParams() {
        JSONObject json = new JSONObject();

        try {

            json.put(AppConfig.KEY_BUS_STATION_ID, stationId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();     // "{'BusStationId':1}"
    }

    // 重写抽象方法 解析响应结果json字符串 返回指定值
    @Override
    protected Object analyzeResponse(String responseString) {
        List<Integer> distances = new ArrayList<>();

        try {

            char[] first = responseString.substring(0, 1).toCharArray();
            if (first[0] == '[') {
                JSONArray jsonArray = new JSONArray(responseString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    distances.add(json.optInt(AppConfig.KEY_DISTANCE));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return distances;
    }
}
