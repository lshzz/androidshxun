package com.ash.transport.request;

import android.content.Context;

import com.ash.transport.bean.EnvInfo;
import com.ash.transport.config.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

/*----------------------------------------------*
 * @package:   com.ash.transport.request
 * @fileName:  GetEnvRequest.java
 * @describe:  环境信息请求类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-25 16:31
 * @继承关系:   GetEnvRequest ← BaseRequest
 *----------------------------------------------*/
public class GetEnvRequest extends BaseRequest {

    // 定义 构造方法
    public GetEnvRequest(Context context) {
        super(context);     // 使用超类的构造方法
    }

    // 重写抽象方法 返回请求后部地址
    @Override
    protected String getAddress() {

        //  AppConfig.GET_ALL_SENSOR 请求所有环境传感器信息
        return AppConfig.GET_ALL_SENSOR;
    }

    // 重写抽象方法 拼接请求参数 返回json格式字符串
    @Override
    protected String getParams() {
        return "";     // 请求环境传感器接口无需参数
    }

    // 重写抽象方法 解析响应结果json字符串 返回指定值
    @Override
    protected Object analyzeResponse(String responseString) {

        // 使用环境实体类存放数据
        EnvInfo env = new EnvInfo();

        try {
            JSONObject jsonObject = new JSONObject(responseString);

            env.setCo2(jsonObject.optInt(AppConfig.KEY_SENSOR_CO2));
            env.setHum(jsonObject.optInt(AppConfig.KEY_SENSOR_HUM));
            env.setLight(jsonObject.optInt(AppConfig.KEY_SENSOR_LIGHT));
            env.setPm(jsonObject.optInt(AppConfig.KEY_SENSOR_PM));
            env.setTemp(jsonObject.optInt(AppConfig.KEY_SENSOR_TEMP));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 返回填充数据后的环境实体类
        return env;
    }
}
