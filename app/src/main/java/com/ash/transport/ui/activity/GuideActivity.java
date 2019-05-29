package com.ash.transport.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.view.WindowManager;

import com.ash.transport.R;

/*----------------------------------------------*
 * @package:   com.ash.transport.ui.activity
 * @fileName:  WelcomeActivity.java
 * @describe:  引导页面 活动类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-21 17:39
 * @继承关系:   WelcomeActivity ← BaseActivity ← [AppCompatActivity]
 *----------------------------------------------*/
public class GuideActivity extends BaseActivity {

    // 重写父类抽象方法 设置布局ID
    @Override
    protected int setLayoutId() {
        return R.layout.activity_guide;
    }

    // 重写父类抽象方法 初始化视图
    @Override
    protected void initView() {
        // Activity 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    // 重写父类抽象方法 初始化数据
    @Override
    protected void initData() {

        // 启动倒时器 总持续时间为2秒 每次减去1秒
        new CountDownTimer(2000, 1000) {

            // 每完成一次倒时后触发的事件
            @Override
            public void onTick(long millisUntilFinished) {

            }

            // 完成全部倒时后触发的事件
            @Override
            public void onFinish() {

                // 使用shared读取用户数据
                SharedPreferences shared = getSharedPreferences("userInfo", MODE_PRIVATE);

                // 判断是否需要重新登录
                if (shared.getBoolean("isGuide", true)) {
                    startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(GuideActivity.this, MainActivity.class));
                }

                // 跳转后销毁当前页面 防止返回
                finish();
            }
        }.start();
    }
}
