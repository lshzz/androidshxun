package com.ash.transport.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/*----------------------------------------------*
 * @package:   com.ash.transport.ui.activity
 * @fileName:  BaseActivity.java
 * @describe:  活动抽象基类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-21 14:17
 * @继承关系:   BaseActivity ← [AppCompatActivity]
 *----------------------------------------------*/
public abstract class BaseActivity extends AppCompatActivity {
    // 重写超类活动创建事件
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());  // 设置布局ID
        initView();                     // 初始化视图
        initData();                     // 初始化数据
    }

    // 抽象方法 设置布局ID
    protected abstract int setLayoutId();

    // 抽象方法 初始化视图
    protected abstract void initView();

    // 抽象方法 初始化数据
    protected abstract void initData();
}
