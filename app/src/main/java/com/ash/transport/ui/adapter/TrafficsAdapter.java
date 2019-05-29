package com.ash.transport.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ash.transport.R;
import com.ash.transport.model.TrafficInfo;

import java.util.List;

/*----------------------------------------------*
 * @package:   com.ash.transport.ui.adapter
 * @fileName:  TrafficsAdapter.java
 * @describe:  红绿灯信息列表适配器
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-26 21:48
 * @继承关系:   TrafficsAdapter ← [BaseAdapter]
 *----------------------------------------------*/
public class TrafficsAdapter extends BaseAdapter {
    private Context context;            // 上下文类
    private List<TrafficInfo> traffics;  // 红绿灯信息数组

    private TextView tvRoadId;          // 路口编号 文本框
    private TextView tvRedTime;         // 红灯时长 文本框
    private TextView tvYellowTime;      // 黄灯时长 文本框
    private TextView tvGreenTime;       // 绿灯时长 文本框


    // 定义构造函数 传入 上下文 和 list存放记录数组
    public TrafficsAdapter(Context context, List<TrafficInfo> traffics) {
        this.context = context;
        this.traffics = traffics;
    }

    // 重写父类方法 获取总记录数
    @Override
    public int getCount() {
        return traffics.size();
    }

    // 重写父类方法 获取指定位置元素
    @Override
    public Object getItem(int position) {
        return traffics.get(position);
    }

    // 重写父类方法 获取指定位置元素ID
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 重写父类方法 子项视图绘制
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 获取布局填充器的另一种方法
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 缓存视图(优化性能)
        View view;

        if (convertView == null) {
            // 没有缓存，需要重新生成
            // 因为getView()返回的对象，Adapter会自动填充ListView
            view = inflater.inflate(R.layout.item_traffic,null);
            // ListView滑动卡顿多数是因为频繁填充布局导致
        } else {
            // 有缓存，不需要重新生成
            view = convertView;
        }

        tvRoadId = view.findViewById(R.id.item_road_id);
        tvRedTime = view.findViewById(R.id.item_red_time);
        tvYellowTime = view.findViewById(R.id.item_yellow_time);
        tvGreenTime = view.findViewById(R.id.item_green_time);

        tvRoadId.setText(traffics.get(position).getRoadId() + "");
        tvRedTime.setText(traffics.get(position).getRedTime() + "");
        tvYellowTime.setText(traffics.get(position).getYellowTime() + "");
        tvGreenTime.setText(traffics.get(position).getGreenTime() + "");

        return view;
    }
}
