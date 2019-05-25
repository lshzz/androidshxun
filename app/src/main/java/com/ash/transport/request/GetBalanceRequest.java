package com.ash.transport.request;

import android.content.Context;

import com.ash.transport.config.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

/*----------------------------------------------*
 * @package:   com.ash.transport.request
 * @fileName:  GetBalanceRequest.java
 * @describe:  查询余额请求类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-22 18:01
 * @继承关系:   GetBalanceRequest ← BaseRequest
 *----------------------------------------------*/
public class GetBalanceRequest extends BaseRequest {
    private int carId;      // 定义 小车ID：1~4

    // 定义 构造方法
    public GetBalanceRequest(Context context) {
        super(context);     // 使用超类的构造方法
    }

    // 定义 设置小车ID 方法
    public void setCarId(int carId) {
        this.carId = carId;
    }

    // 重写抽象方法 返回请求后部地址
    @Override
    protected String getAddress() {

        //  AppConfig.GET_CAR_BALANCE 请求得到指定小车余额
        return AppConfig.GET_CAR_BALANCE;
    }

    // 重写抽象方法 拼接请求参数 返回json格式字符串
    @Override
    protected String getParams() {
        JSONObject json = new JSONObject();

        try {

            json.put(AppConfig.KEY_CAR_ID,carId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();     // "{'CarId':1}"
    }

    // 重写抽象方法 解析响应结果json字符串 返回指定值
    @Override
    protected Object analyzeResponse(String responseString) {
        int money = 0;
        try {
            JSONObject json = new JSONObject(responseString);
            money = json.optInt(AppConfig.KEY_CAR_BALANCE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return money;
    }
}
