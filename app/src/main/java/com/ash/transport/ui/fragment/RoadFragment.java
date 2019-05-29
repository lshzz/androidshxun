package com.ash.transport.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ash.transport.R;
import com.ash.transport.factory.SpinnerFactory;
import com.ash.transport.factory.ToastFactory;
import com.ash.transport.model.TrafficInfo;
import com.ash.transport.request.BaseRequest;
import com.ash.transport.request.GetRoadStatusRequest;
import com.ash.transport.request.GetTrafficRequest;
import com.ash.transport.ui.adapter.TrafficsAdapter;
import com.ash.transport.utils.NetUtil;

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
    private TextView[] tvRoad;          // 1~4号道路 文本框数组
    private Spinner spnSortMode;        // 排序 下拉列表框
    private ListView lvTraffic;         // 红绿灯信息 列表容器
    private TrafficsAdapter adapter;    // 红绿灯信息 列表适配器

    private String[] statusLabel;       // 道路状态标签 字符串
    private String[] statusColors;      // 道路状态颜色值 字符串
    private String[] sSpnTitles;        // 排序下拉列表框项目标题 字符串数组
    private int sortMode;               // 排序方式 升序 降序
    private int sortField;              // 排序字段 0~3
    private List<TrafficInfo> traffics; // 红绿灯信息集合

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
    }

    // 重写父类抽象方法 初始化数据
    @SuppressLint("HandlerLeak")
    @Override
    protected void initData() {

        // 检查网络状态
        if (!NetUtil.isNetworkOK(mContext)) {
            ToastFactory.show(mContext, "网络不可用！");
        }

        // 初始化红绿灯信息集合
        traffics = new ArrayList<>();

        // 实例化红绿灯列表适配器
        adapter = new TrafficsAdapter(mContext,traffics);

        // 为红绿灯列表设置适配器
        lvTraffic.setAdapter(adapter);

        // 初始化排序规则 默认按路口升序排列
        sortMode = 0;
        sortField = 0;

        // 初始化 道路状态标签 字符串
        statusLabel = new String[]{
                "通畅",
                "较通畅",
                "拥挤",
                "堵塞",
                "爆表"
        };

        // 初始化 道路状态颜色值 字符串
        statusColors = new String[]{
                "#049B07",      // 通畅 颜色
                "#73C400",      // 较通畅 颜色
                "#CBCB20",      // 拥挤 颜色
                "#A55921",      // 堵塞 颜色
                "#4c060e"       // 爆表 颜色
        };

        // 初始化 排序下拉列表框 项目标题 字符串数组
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

        // 通过 Spinner工厂类 创建下拉列表框
        SpinnerFactory.getSpinner(mContext, spnSortMode, sSpnTitles, new SpinnerFactory.OnItemSelected() {
            // 项目列表选中事件
            @Override
            public void onSelected(int position) {

                // 通过求余判断排序方式是升序还是降序
                sortMode = position % 2 == 0 ? 0 : 1;

                // 通过三目运算符快速判断需要排序的字段
                sortField = position < 2 ? 0
                        : position < 4 ? 1
                        : position < 6 ? 2
                        : 3;

                // 排序方式选择后 查询红绿灯信息
                queryTrafficInfo();
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

        // 默认开启自动刷新
        threadRun = true;
        autoRefresh.start();
    }

    // 定义 查询道路状态 方法
    private void queryRoadStatus(final int roadId) {
        GetRoadStatusRequest getRoadStatusRequest = new GetRoadStatusRequest(mContext);
        getRoadStatusRequest.setRoadId(roadId);
        getRoadStatusRequest.connec(new BaseRequest.OnGetDataListener() {
            @Override
            public void onReturn(Object data) {
                if (data != null) {
                    // 获取返回的状态值 1~5
                    int status = (int) data;
                    // 根据状态值设置道路状态标签
                    tvRoad[roadId].setText(statusLabel[status - 1]);
                    // 根据状态值设置道路状态颜色
                    tvRoad[roadId].setBackgroundColor(Color.parseColor(statusColors[status - 1]));
                }
            }
        });
    }

    // 定义 查询红绿灯信息 方法
    private void queryTrafficInfo() {
        // 先清空红绿灯信息集合
        traffics.clear();

        // 线程中传值只能使用引用类型
        final int[] count = {0};

        // 循环请求1~4号路口的红绿灯信息
        for (int i = 1; i < 5; i++) {
            GetTrafficRequest getTrafficRequest = new GetTrafficRequest(mContext);
            getTrafficRequest.setRoadId(i);
            getTrafficRequest.connec(new BaseRequest.OnGetDataListener() {
                @Override
                public void onReturn(Object data) {

                    // 将请求到的红绿灯信息实体类储存到集合中
                    traffics.add((TrafficInfo) data);

                    // 记录循环次数(循环变量i 不能使用，在线程中传值只能使用引用类型)
                    count[0]++;

                    // 判断红绿灯信息集合储存完4个路口后
                    if (count[0] == 4) {
                        // 进行集合排序
                        sortList();
                        // 通知
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    // 定义 红绿灯信息集合排序 方法
    private void sortList() {
        for (int i=0; i<traffics.size()-1; i++) {
            for (int j=0; j<traffics.size()-i-1;j++) {

                // 先取出当前集合元素
                int[] list1 = new int[] {
                        traffics.get(j).getRoadId(),
                        traffics.get(j).getRedTime(),
                        traffics.get(j).getYellowTime(),
                        traffics.get(j).getGreenTime(),
                };

                // 再取出后一个集合元素
                int[] list2 = new int[]{
                        traffics.get(j + 1).getRoadId(),
                        traffics.get(j + 1).getRedTime(),
                        traffics.get(j + 1).getYellowTime(),
                        traffics.get(j + 1).getGreenTime(),
                };


                if (sortMode == 0) {

                    // 升序排序
                    if (list1[sortField] > list2[sortField]) {
                        TrafficInfo temp = traffics.get(j);
                        traffics.set(j,traffics.get(j+1));
                        traffics.set(j+1,temp);
                    }

                } else {

                    // 降序排序
                    if (list1[sortField] < list2[sortField]) {
                        TrafficInfo temp = traffics.get(j);
                        traffics.set(j,traffics.get(j+1));
                        traffics.set(j+1,temp);
                    }

                }
            }
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
