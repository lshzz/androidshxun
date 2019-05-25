package com.ash.transport.ui.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ash.transport.R;
import com.ash.transport.bean.RecordInfo;
import com.ash.transport.dao.RecordDao;
import com.ash.transport.factory.DialogFactory;
import com.ash.transport.factory.ToastFactory;
import com.ash.transport.request.BaseRequest;
import com.ash.transport.request.SetBalanceRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

/*----------------------------------------------*
 * @package:   com.ash.transport.ui.activity
 * @fileName:  ChargeActivity.java
 * @describe:  车辆余额充值活动类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-22 21:56
 * @继承关系:   ChargeActivity ← BaseActivity ← [AppCompatActivity]
 *----------------------------------------------*/
public class ChargeActivity extends BaseActivity implements View.OnClickListener {
    private int carId;

    private TextView tvCarId;
    private TextView btnMoney20;
    private TextView btnMoney50;
    private TextView btnMoney100;
    private TextView btnCustom;
    private RecordDao recordDao;


    // 重写父类抽象方法 设置布局ID
    @Override
    protected int setLayoutId() {
        return R.layout.activity_charge;
    }

    // 重写父类抽象方法 初始化视图
    @Override
    protected void initView() {
        // 显示顶部原生标题栏返回按钮
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // 设置顶部原生标题栏标题
        ChargeActivity.this.setTitle("车辆余额充值");

        tvCarId = findViewById(R.id.iv_car_id);
        btnMoney20 = findViewById(R.id.btn_money_20);
        btnMoney50 = findViewById(R.id.btn_money_50);
        btnMoney100 = findViewById(R.id.btn_money_100);
        btnCustom = findViewById(R.id.btn_money_custom);
    }

    // 重写父类抽象方法 初始化数据
    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        // Android 规范是以 包名+类名+变量名 来命名意图传值键名
        carId = getIntent().getIntExtra("com.ash.transport.ui.fragment.CarFragment.carId", 0);
        tvCarId.setText(carId + "");
        btnMoney20.setOnClickListener(this);
        btnMoney50.setOnClickListener(this);
        btnMoney100.setOnClickListener(this);
        btnCustom.setOnClickListener(this);
        recordDao = new RecordDao(ChargeActivity.this);
    }

    // 重写顶部标题栏按钮监听事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 返回按钮被单击事件
            case android.R.id.home:
                // 销毁当前Activity
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 重写按钮监听接口事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_money_20:         // 20元
                openDialog(20);
                break;

            case R.id.btn_money_50:         // 50元
                openDialog(50);
                break;

            case R.id.btn_money_100:        // 100元
                openDialog(100);
                break;

            case R.id.btn_money_custom:     // 自定义金额
                break;
        }
    }

    // 定义 打开对话框 方法
    private void openDialog(final int money) {
        // 使用对话框工厂类生成对话框并显示
        DialogFactory.showDialog(ChargeActivity.this,
                "充值确认",
                "您确定要给" + carId + "号小车充值 " + money + " 元吗？",
                new DialogFactory.OnListener() {

                    @Override
                    public void onAfter() {
                        charge(money);
                    }
                });
    }

    // 定义 充值 方法
    private void charge(final int money) {
        // 使用余额充值请求类
        SetBalanceRequest setBalanceRequest = new SetBalanceRequest(ChargeActivity.this);
        setBalanceRequest.setCarId(carId);
        setBalanceRequest.setMoney(money);
        setBalanceRequest.connec(new BaseRequest.OnGetDataListener() {
            @Override
            public void onReturn(Object data) {
                if ("ok".equals(data.toString())) {

                    RecordInfo record = new RecordInfo();
                    record.setCarId(carId);
                    record.setMoney(money);

                    SharedPreferences shared = getSharedPreferences("userInfo", MODE_PRIVATE);
                    record.setUserName(shared.getString("name", "name"));

                    Date currentTime = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("MM-dd hh:mm");
                    record.setChargeDate(formatter.format(currentTime));

                    recordDao.insert(record);

                    ToastFactory.show(ChargeActivity.this,"充值成功",true);
                } else {
                    ToastFactory.show(ChargeActivity.this,"充值失败",true);
                }

                // 充值结束后销毁当前页面
                finish();
            }
        });
    }
}
