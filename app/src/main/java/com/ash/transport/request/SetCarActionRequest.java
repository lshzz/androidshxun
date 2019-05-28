package com.ash.transport.request;

import android.content.Context;

import com.ash.transport.config.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

/*----------------------------------------------*
 * @package:   com.ash.transport.request
 * @fileName:  GetBalanceRequest.java
 * @describe:  设置车辆动作请求类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-28 19:26
 * @继承关系:   SetCarActionRequest ← BaseRequest
 *----------------------------------------------*/
public class SetCarActionRequest extends BaseRequest {
    private int carId;      // 定义 小车ID：1~4
    private String action;  // 定义 小车动作

    // 定义 构造方法
    public SetCarActionRequest(Context context) {
        super(context);     // 使用超类的构造方法
    }

    // 定义 设置小车ID 方法
    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    // 重写抽象方法 返回请求后部地址
    @Override
    protected String getAddress() {

        //  AppConfig.SET_CAR_ACTION 设置指定小车动作
        return AppConfig.SET_CAR_ACTION;
    }

    // 重写抽象方法 拼接请求参数 返回json格式字符串
    @Override
    protected String getParams() {
        JSONObject json = new JSONObject();

        try {

            json.put(AppConfig.KEY_CAR_ID, carId);
            json.put(AppConfig.KEY_CAR_ACTION, action);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();     // "{'CarId':1;'CarAction':'Start'}"
    }

    // 重写抽象方法 解析响应结果json字符串 返回指定值
    @Override
    protected Object analyzeResponse(String responseString) {
        String result = "";

        try {
            JSONObject json=new JSONObject(responseString);
            result=json.optString("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
