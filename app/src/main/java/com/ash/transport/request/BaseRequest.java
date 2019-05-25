package com.ash.transport.request;

import android.content.Context;

import com.ash.transport.factory.ToastFactory;
import com.ash.transport.utils.NetUtil;

/*----------------------------------------------*
 * @package:   com.ash.transport.request
 * @fileName:  BaseRequest.java
 * @describe:  请求抽象基类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-22 17:22
 *----------------------------------------------*/
public abstract class BaseRequest {
    private NetUtil netUtil;
    private String url;
    private Context context;

    // 定义 [得到返回数据事件监听器] 公开接口
    // 所有相关请求结果都通过该监听器返回响应数据
    public interface OnGetDataListener {
        void onReturn(Object data);     // 定义 数据返回 事件 空方法
    }

    // 定义 构造方法
    public BaseRequest(Context context) {

        // 接收上下文
        this.context = context;

        // 通过SharedPreferences获取已储存的IP地址
        // 并设置url地址
        // (只包含前部共用地址，后部地址根据getAddress()抽象方法的不同实现获取)
        url = "http://"
                + context.getSharedPreferences("ipset", 0).getString("ip", "47.106.226.220") + ":"
                + 8080
                + "/transportservice/type/jason/action/";

        /*  url前部形式：http://47.106.226.220:8080/transportservice/type/jason/action/
         *  url后部形式：GetCarSpeed.do
         *-------------------------------------------------
         *  根据抽象方法实现拼接完整后的形式：
         *  http://47.106.226.220:8080/transportservice/type/jason/action/GetCarSpeed.do
         *-------------------------------------------------
         *  url后部地址已在config包中的AppConfig类中定义，如：
         *  AppConfig.GET_CAR_SPEED
         */
    }

    // 定义 开始连接 方法
    public void connec(final OnGetDataListener listener) {
        netUtil = new NetUtil();
        netUtil.asyncRequest(url + getAddress(), getParams(), new NetUtil.ResponseListener() {

            // 重写实现请求成功事件
            @Override
            public void success(String result) {
                if (listener != null) {
                    if (!result.isEmpty()) {
                        listener.onReturn(analyzeResponse(result));
                    }
                }
            }

            // 重写实现请求失败事件
            @Override
            public void error(String message) {
                ToastFactory.show(context, message);
            }
        });
    }

    //  抽象方法 得到请求地址
    protected abstract String getAddress();

    //  抽象方法 得到请求参数
    protected abstract String getParams();

    //  抽象方法 分析处理数据
    protected abstract Object analyzeResponse(String responseString);
}
