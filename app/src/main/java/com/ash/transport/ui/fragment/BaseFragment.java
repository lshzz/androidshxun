package com.ash.transport.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*----------------------------------------------*
 * @package:   com.ash.transport.ui.fragment
 * @fileName:  BaseFragment.java
 * @describe:  碎片抽象基类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-21 17:18
 * @继承关系:   BaseFragment ← [Fragment]
 *----------------------------------------------*/

public abstract class BaseFragment extends Fragment {
    // protected 受保护类型 子类对象可以继承并赋值
    protected Context mContext;     // 碎片上下文对象
    protected View mView;           // 碎片视图对象


    // 重写超类碎片创建事件
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // 通过布局填充器和布局ID获得该碎片的视图对象
        mView = inflater.inflate(setLayoutId(), container, false);
        // 通过视图对象获得上下文对象
        mContext = mView.getContext();

        initView();     // 初始化视图
        initData();     // 初始化数据
        return mView;   // 返回视图对象，显示到屏幕上
    }

    // 抽象方法 设置布局ID
    protected abstract int setLayoutId();

    // 抽象方法 初始化视图
    protected abstract void initView();

    // 抽象方法 初始化数据
    protected abstract void initData();
}
