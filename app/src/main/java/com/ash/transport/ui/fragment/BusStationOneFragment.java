package com.ash.transport.ui.fragment;

import android.widget.TextView;

import com.ash.transport.R;
import com.ash.transport.request.BaseRequest;
import com.ash.transport.request.GetBusStationRequest;

import java.util.List;

/*----------------------------------------------*
 * @package:   com.ash.transport.ui.fragment
 * @fileName:  BusFragment.java
 * @describe:  城市公交一号站台页面碎片类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-27 22:02
 * @继承关系:   BusStationOneFragment ← BaseFragment ← [Fragment]
 *----------------------------------------------*/
public class BusStationOneFragment extends BaseFragment {
    private TextView tvBus1;            // 1号公交距离 文本框
    private TextView tvBus2;            // 2号公交距离 文本框

    private Thread autoRefresh;         // 自动刷新 线程
    private boolean threadRun;          // 线程运行 标识


    // 重写父类抽象方法 设置布局ID
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_bus_station_1;
    }

    // 重写父类抽象方法 初始化视图
    @Override
    protected void initView() {
        tvBus1 = mView.findViewById(R.id.tv_bus_1);
        tvBus2 = mView.findViewById(R.id.tv_bus_2);
    }

    // 重写父类抽象方法 初始化数据
    @Override
    protected void initData() {

        // 线程运行表示 默认开启自动刷新
        threadRun = true;

        // 定义 自动刷新 子线程
        autoRefresh = new Thread(new Runnable() {
            @Override
            public void run() {
                // 只要线程标识许可就一直循环
                while (threadRun) {
                    // 请求公交站台数据
                    queryBusStation();

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

    // 定义 请求公交站台数据 方法
    private void queryBusStation() {
        GetBusStationRequest getBusStationRequest = new GetBusStationRequest(mContext);
        getBusStationRequest.setStationId(1);   // 查询 1号站台 信息
        getBusStationRequest.connec(new BaseRequest.OnGetDataListener() {
            @Override
            public void onReturn(Object data) {
                List<Integer> list = (List<Integer>) data;
                // 显示两辆公交车距离1号站台的距离值
                tvBus1.setText("1号公交：" + list.get(0) + " m");
                tvBus2.setText("2号公交：" + list.get(1) + " m");
            }
        });
    }

    // 重写页面暂停时触发事件方法
    @Override
    public void onPause() {
        super.onPause();
        // 页面不可见后 及时关闭线程
        threadRun = false;  // 撤回线程标识许可 线程自动停止
    }
}
