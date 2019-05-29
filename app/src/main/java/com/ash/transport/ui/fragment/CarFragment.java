package com.ash.transport.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.ash.transport.R;
import com.ash.transport.factory.SpinnerFactory;
import com.ash.transport.factory.ToastFactory;
import com.ash.transport.model.CarInfo;
import com.ash.transport.request.BaseRequest;
import com.ash.transport.request.GetBalanceRequest;
import com.ash.transport.request.SetCarActionRequest;
import com.ash.transport.service.CarInfoService;
import com.ash.transport.ui.activity.ChargeActivity;
import com.ash.transport.ui.activity.RecordActivity;
import com.ash.transport.utils.NetUtil;

import java.io.InputStream;
import java.util.List;

/*----------------------------------------------*
 * @package:   com.ash.transport.ui.fragment
 * @fileName:  CarFragment.java
 * @describe:  我的车辆 页面碎片类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-21 17:23
 * @继承关系:   CarFragment ← BaseFragment ← [Fragment]
 *----------------------------------------------*/
public class CarFragment extends BaseFragment implements View.OnClickListener {

    private Handler handler;            // 注意：这里使用的是 android.os 包中的 handler

    private int carId;                  // 声明 当前选择的车辆编号
    private String[] itemTexts;         // 声明 下拉列表框项目标题数组
    private int[] picIDs;               // 声明 drawable文件夹下 车辆图片 资源文件 id数组

    private ImageView imgCarPic;        // 声明 车辆图片 图片框
    private Spinner spnCarId;           // 声明 车辆编号 下拉列表框
    private TextView tvMoney;           // 声明 余额 文本框
    private TextView tvCarMake;         // 声明 型号 文本框
    private TextView tvCarEngine;       // 声明 发动机 文本框
    private TextView tvCarFrame;        // 声明 车架 文本框
    private TextView tvCarType;         // 声明 车辆类型 文本框
    private TextView tvLicenseType;     // 声明 车牌类型 文本框
    private TextView tvCarLicense;      // 声明 车牌号码 文本框
    private TextView tvVioNum;          // 声明 违章次数 文本框
    private TextView tvPoints;          // 声明 总扣分 文本框
    private TextView tvFine;            // 声明 总罚款 文本框
    private Button btnCharge;           // 声明 余额充值 按钮
    private TextView btnRecords;        // 声明 查询充值记录 按钮
    private Switch swiCarAction;        // 声明 车辆启停 开关


    // 重写父类抽象方法 设置布局ID
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_car;
    }

    // 重写父类抽象方法 初始化视图
    @Override
    protected void initView() {
        imgCarPic = mView.findViewById(R.id.img_car_pic);
        spnCarId = mView.findViewById(R.id.spn_car_id);
        tvMoney = mView.findViewById(R.id.tv_money);
        tvCarMake = mView.findViewById(R.id.tv_make);
        tvCarEngine = mView.findViewById(R.id.tv_engine);
        tvCarFrame = mView.findViewById(R.id.tv_frame);
        tvCarType = mView.findViewById(R.id.tv_car_type);
        tvLicenseType = mView.findViewById(R.id.tv_license_type);
        tvCarLicense = mView.findViewById(R.id.tv_car_license);
        tvVioNum = mView.findViewById(R.id.tv_Violation_num);
        tvPoints = mView.findViewById(R.id.tv_points);
        tvFine = mView.findViewById(R.id.tv_fine);
        btnCharge = mView.findViewById(R.id.btn_charge);
        btnRecords = mView.findViewById(R.id.btn_records);
        swiCarAction = mView.findViewById(R.id.swi_car_action);
    }

    // 重写父类抽象方法 初始化数据
    @SuppressLint("HandlerLeak")
    @Override
    protected void initData() {

        // 检查网络状态
        if (!NetUtil.isNetworkOK(mContext)) {
            ToastFactory.show(mContext,"网络不可用！");
        }

        // 使用本类中实现的按钮监听事件
        btnCharge.setOnClickListener(this);
        btnRecords.setOnClickListener(this);

        // 为车辆启停开关设置事件
        swiCarAction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 根据开关状态判断启动或停止
                String action = isChecked ? "Move" : "Stop";
                // 设置车辆启停
                setCarAction(carId, action);
            }
        });

        handler = new Handler() {
            // 当 handle 收到消息后 会在子线程中处理以下消息
            public void handleMessage(Message msg) {
                // 通过 msg.obj 获取传递的对象
                CarInfo carInfo = (CarInfo) msg.obj;
                // 更新 UI
                tvCarMake.setText(carInfo.getMake());
                tvCarEngine.setText(carInfo.getEngine());
                tvCarFrame.setText(carInfo.getFrame());
                tvCarType.setText(carInfo.getType());
                tvLicenseType.setText(carInfo.getLicenseType());
                tvCarLicense.setText(carInfo.getLicenseNum());
                tvVioNum.setText(carInfo.getVioNum());
                tvPoints.setText(carInfo.getPoints());
                tvFine.setText(carInfo.getFine());
            }
            // 处理结束后子线程会回收到[主线程](UI线程)循环
        };

        // 下拉列表框 项目标题
        itemTexts = new String[]{
                "车辆编号：1",
                "车辆编号：2",
                "车辆编号：3",
                "车辆编号：4"
        };

        // drawable文件夹下 车辆图片 资源文件 id数组
        picIDs = new int[]{
                R.drawable.bmw_x5,
                R.drawable.amg_gt,
                R.drawable.baojun_310,
                R.drawable.toyota_carola
        };

        // 通过Spinner工厂类 创建下拉列表框
        SpinnerFactory.getSpinner(mContext, spnCarId, itemTexts, new SpinnerFactory.OnItemSelected() {
            // 重写SpinnerFactory工厂类选中事件接口
            @Override
            public void onSelected(int position) {
                carId = position + 1;
                changeImage(position);              // 切换车辆图片
                changeInfo(position);               // 切换车辆信息
                queryMoney(position + 1);     // 切换查询余额
            }
        });

        // 进入页面后直接展示第一辆小车信息
        spnCarId.setSelection(0);
    }

    // 重写按钮监听接口事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 立即充值
            case R.id.btn_charge:

                // fragment 中使用父activity的context进行跳转
                Intent intent = new Intent(mContext, ChargeActivity.class);

                // Android 规范是以 包名+类名+变量名 来命名意图传值键名
                intent.putExtra("com.ash.transport.ui.fragment.CarFragment.carId", carId);

                startActivity(intent);
                break;

            // 历史记录
            case R.id.btn_records:
                startActivity(new Intent(mContext, RecordActivity.class));
                break;
        }
    }

    // 定义 改变车辆图片 方法
    private void changeImage(int pos) {
        imgCarPic.setImageResource(picIDs[pos]);
    }

    // 定义 改变车辆信息 方法
    private void changeInfo(final int pos) {
        // 读取文件必须在[非UI线程](子线程)中执行
        new Thread() {
            @Override
            public void run() {
                try {
                    // 读取 car_info.xml 文件
                    InputStream is = mContext.getResources().openRawResource(R.raw.car_info);

                    // 把每辆小车的信息集合存到 list 中
                    List<CarInfo> list = CarInfoService.getFromXML(is);

                    // 通过 handler 创建线程消息类 并通知其执行 handleMessage 方法
                    Message msg = handler.obtainMessage();

                    // 读取 list 中指定编号的车辆数据放入msg.obj
                    msg.obj = list.get(pos);

                    // 通知handler处理消息
                    // msg.obj 内的对象会传递到 handleMessage 消息处理中使用
                    handler.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastFactory.show(mContext, "车辆信息读取失败！", true);
                }
            }
        }.start();
    }

    // 定义 查询车辆余额 方法
    private void queryMoney(int carId) {
        // 使用车辆余额请求类
        GetBalanceRequest getBalanceRequest = new GetBalanceRequest(mContext);
        // 设置欲查询的车辆ID
        getBalanceRequest.setCarId(carId);
        // 连接服务器
        getBalanceRequest.connec(new BaseRequest.OnGetDataListener() {
            // 返回结果监听器 触发事件
            @SuppressLint("SetTextI18n")
            @Override
            public void onReturn(Object data) {
                // 显示余额
                tvMoney.setText("￥" + data + ".00");
            }
        });
    }

    // 定义 设置车辆启停 方法
    private void setCarAction(int carId, String action) {
        // 使用设置车辆启停请求类
        SetCarActionRequest setCarActionRequest = new SetCarActionRequest(mContext);
        // 设置车辆ID
        setCarActionRequest.setCarId(carId);
        // 设置车辆动作
        setCarActionRequest.setAction(action);
        // 连接服务器
        setCarActionRequest.connec(new BaseRequest.OnGetDataListener() {
            // 返回结果监听器 触发事件
            @Override
            public void onReturn(Object data) {
                if (data != null) {
                    ToastFactory.show(mContext, "操作成功！");
                } else {
                    ToastFactory.show(mContext, "操作失败！");
                    swiCarAction.setChecked(!swiCarAction.isChecked());
                }

            }
        });
    }

    // 回到当前页面时触发监听事件
    @Override
    public void onResume() {
        super.onResume();
        // 刷新当前车辆余额
        queryMoney(carId);
    }
}
