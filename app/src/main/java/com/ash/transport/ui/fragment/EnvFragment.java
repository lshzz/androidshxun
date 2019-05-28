package com.ash.transport.ui.fragment;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ash.transport.R;
import com.ash.transport.model.EnvInfo;
import com.ash.transport.request.BaseRequest;
import com.ash.transport.request.GetEnvRequest;

/*----------------------------------------------*
 * @package:   com.ash.transport.ui.fragment
 * @fileName:  EnvFragment.java
 * @describe:  出行环境 页面碎片类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-25 15:43
 * @继承关系:   EnvFragment ← BaseFragment ← [Fragment]
 *----------------------------------------------*/
public class EnvFragment extends BaseFragment implements Switch.OnCheckedChangeListener {
    private TextView tvTemp;            // 温度 文本框
    private TextView tvPm;              // pm2.5 文本框
    private TextView tvCo2;             // co2 文本框
    private TextView tvLight;           // 光照强度 文本框
    private TextView tvHum;             // 湿度 文本框
    private Switch swRefresh;           // 自动刷新 开关
    private Switch swNotify;            // 温度通知 开关

    private Handler handler;            // 声明 handler 注意：这里使用的是 android.os 包中的 handler
    private Thread autoRefresh;         // 自动刷新 线程

    private boolean threadRun;          // 线程运行 标识
    private boolean notifyRun;          // 通知运行 标识

    private NotificationManager notifyManager; // 通知管理类


    // 重写父类抽象方法 设置布局ID
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_env;
    }

    // 重写父类抽象方法 初始化视图
    @Override
    protected void initView() {
        tvTemp = mView.findViewById(R.id.tv_temperature);
        tvPm = mView.findViewById(R.id.tv_pm);
        tvCo2 = mView.findViewById(R.id.tv_co2);
        tvLight = mView.findViewById(R.id.tv_light);
        tvHum = mView.findViewById(R.id.tv_humidity);

        swRefresh = mView.findViewById(R.id.sw_auto_refresh);
        swNotify = mView.findViewById(R.id.sw_notify);
    }

    // 重写父类抽象方法 初始化数据
    @SuppressLint("HandlerLeak")
    @Override
    protected void initData() {
        // 通过系统服务 获取通知管理类
        notifyManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        // 使用自动刷新开关来为线程标识赋值
        threadRun = swRefresh.isChecked();
        // 使用温度通知开关来为通知标识赋值
        notifyRun = swNotify.isChecked();

        // 使用在本类中实现的开关改变监听事件接口
        swRefresh.setOnCheckedChangeListener(this);
        swNotify.setOnCheckedChangeListener(this);

        // 定义handler消息处理内容
        handler = new Handler() {
            // 当 handle 收到消息后 会在子线程中处理以下消息
            @SuppressLint("SetTextI18n")
            public void handleMessage(Message msg) {
                // 通过 msg.obj 获取传递的对象
                EnvInfo env = (EnvInfo) msg.obj;

                // 更新 UI
                tvTemp.setText(env.getTemp() + "℃");
                tvPm.setText(env.getPm() + "");
                tvCo2.setText(env.getCo2() + "");
                tvLight.setText(env.getLight() + "");
                tvHum.setText(env.getHum() + "");

                // 发送通知 温度大于30度 且 通知开关已打开
                if (env.getTemp() > 30 && notifyRun) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
                    builder.setSmallIcon(R.mipmap.ic_launcher);//设置图标
                    builder.setWhen(System.currentTimeMillis());//时间
                    builder.setContentTitle("温度提醒");//标题
                    builder.setContentText("温度过高！");//通知内容
                    builder.setAutoCancel(true);
                    Notification notify = builder.build();
                    notifyManager.notify(0,notify);
                }
            }
            // 处理结束后子线程会回收到[主线程](UI线程)循环
        };

        // 定义 自动刷新 子线程
        autoRefresh = new Thread(new Runnable() {
            @Override
            public void run() {
                // 只要线程标识许可就一直循环
                while (threadRun) {
                    // 请求环境数据
                    queryEnv();

                    try {
                        // 每3秒重复一次
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // 如果自动刷新开关已打开就启动线程
        if (threadRun) {
            autoRefresh.start();
        }
    }

    // 定义 请求环境信息 方法
    private void queryEnv() {
        GetEnvRequest getEnvRequest = new GetEnvRequest(mContext);
        getEnvRequest.connec(new BaseRequest.OnGetDataListener() {
            @Override
            public void onReturn(Object data) {
                if (data != null) {
                    // 将返回结果对象转换赋值
                    EnvInfo env = (EnvInfo) data;

                    // 使用消息传递环境实体类对象
                    Message msg = new Message();
                    msg.obj = env;

                    // 通知handler处理消息
                    // msg.obj 内的对象会传递到 handleMessage 消息处理中使用
                    handler.sendMessage(msg);
                }
            }
        });
    }

    // 重写实现开关改变监听事件接口方法
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            // 自动刷新
            case R.id.sw_auto_refresh:
                if (isChecked) {
                    // 打开
                    threadRun = true;       // 给予线程标识许可
                    autoRefresh.start();    // 启动线程
                } else {
                    // 关闭
                    threadRun = false;      // 撤回线程标识许可 线程自动停止
                }
                break;

            // 温度通知
            case R.id.sw_notify:
                // 使用温度通知开关来为通知标识赋值
                notifyRun = isChecked;
                break;
        }
    }

    // 重写页面暂停时触发事件方法
    @Override
    public void onPause() {
        super.onPause();
        // 页面不可见后 及时关闭线程
        threadRun = false;  // 撤回线程标识许可 线程自动停止
    }
}
