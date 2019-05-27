package com.ash.transport.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ash.transport.R;
import com.ash.transport.bean.TrafficInfo;
import com.ash.transport.factory.SpinnerFactory;
import com.ash.transport.request.BaseRequest;
import com.ash.transport.request.GetRoadStatusRequest;
import com.ash.transport.request.GetTrafficRequest;
import com.ash.transport.ui.adapter.TrafficsAdapter;

import java.util.ArrayList;
import java.util.List;

/*----------------------------------------------*
 * @package:   com.ash.transport.ui.fragment
 * @fileName:  RoadFragment.java
 * @describe:  道路状态 页面碎片类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-25 21:20
 * @继承关系:   RoadFragment ← BaseFragment ← [Fragment]
 *----------------------------------------------*/
public class RoadFragment extends BaseFragment {
    private TextView[] tvRoad;
    private Spinner spnSortMode;
    private Button btnQuery;
    private ListView lvTraffic;
    private TrafficsAdapter adapter;

    private int[] iStatus;
    private String[] sStatus;
    private String[] cStatus;
    private String[] sSpnTitles;
    private int sortMode;
    private int sortField;
    private List<TrafficInfo> traffics;

    private Handler handler;            // 声明 handler 注意：这里使用的是 android.os 包中的 handler
    private Thread autoRefresh;         // 自动刷新 线程
    private boolean threadRun;          // 线程运行 标识


    // 重写父类抽象方法 设置布局ID
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_road;
    }

    // 重写父类抽象方法 初始化视图
    @Override
    protected void initView() {
        tvRoad = new TextView[4];
        tvRoad[0] = mView.findViewById(R.id.tv_road_1);
        tvRoad[1] = mView.findViewById(R.id.tv_road_2);
        tvRoad[2] = mView.findViewById(R.id.tv_road_3);
        tvRoad[3] = mView.findViewById(R.id.tv_road_4);

        lvTraffic = mView.findViewById(R.id.lv_traffic);
        spnSortMode = mView.findViewById(R.id.spn_sort_mode);
        btnQuery = mView.findViewById(R.id.btn_query);
    }

    // 重写父类抽象方法 初始化数据
    @SuppressLint("HandlerLeak")
    @Override
    protected void initData() {
        traffics = new ArrayList<>();


        iStatus = new int[4];

        sStatus = new String[]{
                "通畅",
                "较通畅",
                "拥挤",
                "堵塞",
                "爆表"
        };

        cStatus = new String[]{
                "#049B07",
                "#73C400",
                "#CBCB20",
                "#A55921",
                "#4c060e"
        };

        sSpnTitles = new String[]{
                "路口升序",
                "路口降序",
                "红灯升序",
                "红灯降序",
                "黄灯升序",
                "黄灯降序",
                "绿灯升序",
                "绿灯降序"
        };

        handler = new Handler() {
            public void handleMessage(Message msg) {
                // 通知 adapter 更新 ListView 数据
                adapter.notifyDataSetChanged();
            }
        };

        SpinnerFactory.getSpinner(mContext, spnSortMode, sSpnTitles, new SpinnerFactory.OnItemSelected() {
            @Override
            public void onSelected(int position) {
                sortMode = position % 2 == 0 ? 0 : 1;
                sortField = position < 2 ? 0
                        : position < 4 ? 1
                        : position < 6 ? 2
                        : 3;
            }
        });


        // 定义 自动刷新 子线程
        autoRefresh = new Thread(new Runnable() {
            @Override
            public void run() {
                // 只要线程标识许可就一直循环
                while (threadRun) {
                    // 循环请求1~5号道路状态数据
                    for (int i = 0; i < 4; i++) {
                        queryRoadStatus(i);
                    }

                    try {
                        // 每3秒重复一次
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        threadRun = true;
        autoRefresh.start();

        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryTrafficInfo();
            }
        });
    }

    private void queryRoadStatus(final int roadId) {
        GetRoadStatusRequest getRoadStatusRequest = new GetRoadStatusRequest(mContext);
        getRoadStatusRequest.setRoadId(roadId);
        getRoadStatusRequest.connec(new BaseRequest.OnGetDataListener() {
            @Override
            public void onReturn(Object data) {
                if (data != null) {
                    int status = (int) data;
                    tvRoad[roadId].setText(sStatus[status - 1]);
                    tvRoad[roadId].setBackgroundColor(Color.parseColor(cStatus[status - 1]));
                }
            }
        });
    }

    private void queryTrafficInfo() {
        traffics.clear();


        GetTrafficRequest getTrafficRequest = new GetTrafficRequest(mContext);

        getTrafficRequest.setRoadId(1);
        getTrafficRequest.connec(new BaseRequest.OnGetDataListener() {
            @Override
            public void onReturn(Object data) {
                traffics.add((TrafficInfo) data);
                adapter = new TrafficsAdapter(mContext,traffics);
                lvTraffic.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
            }
        });


//        adapter.notifyDataSetChanged();
//        sortList();
//
//        // 通过 handler 创建线程消息类 并通知其执行 handleMessage 方法
//        Message msg = handler.obtainMessage();
//
//        // 读取 list 中指定编号的车辆数据放入msg.obj
//        msg.obj = traffics;
//
//        // 通知handler处理消息
//        // msg.obj 内的对象会传递到 handleMessage 消息处理中使用
//        handler.sendMessage(msg);
    }

    private void sortList() {

    }

    @Override
    public void onPause() {
        super.onPause();
        threadRun = false;
    }
}
