package com.ash.transport.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ash.transport.R;
import com.ash.transport.model.RecordInfo;

import java.util.List;

/*----------------------------------------------*
 * @package:   com.ash.transport.ui.adapter
 * @fileName:  RecordsAdapter.java
 * @describe:  充值记录列表适配器
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-23 17:42
 * @继承关系:   RecordsAdapter ← [BaseAdapter]
 *----------------------------------------------*/
public class RecordsAdapter extends BaseAdapter {
    private Context context;            // 上下文类
    private List<RecordInfo> records;   // 充值记录数据数组

    private TextView tvUserName;        // 充值者 文本框
    private TextView tvCarId;           // 车辆编号 文本框
    private TextView tvMoney;           // 充值金额 文本框
    private TextView tvDate;            // 充值时间 文本框


    // 定义构造函数 传入 上下文 和 list存放记录数组
    public RecordsAdapter(Context context, List<RecordInfo> records) {
        this.context = context;
        this.records = records;
    }

    // 重写父类方法 获取总记录数
    @Override
    public int getCount() {
        return records.size();
    }

    // 重写父类方法 获取指定位置元素
    @Override
    public Object getItem(int position) {
        return records.get(position);
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
            view = inflater.inflate(R.layout.item_record,null);
            // ListView滑动卡顿多数是因为频繁填充布局导致
        } else {
            // 有缓存，不需要重新生成
            view = convertView;
        }

        tvUserName = view.findViewById(R.id.item_username);
        tvCarId = view.findViewById(R.id.item_car_id);
        tvMoney = view.findViewById(R.id.item_money);
        tvDate = view.findViewById(R.id.item_date);

        tvUserName.setText(records.get(position).getUserName());
        tvCarId.setText(records.get(position).getCarId() + "号小车");
        tvMoney.setText("￥ " + records.get(position).getMoney() + ".00");
        tvDate.setText(records.get(position).getChargeDate());

        return view;
    }
}
