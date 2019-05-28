package com.ash.transport.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.ash.transport.R;
import com.ash.transport.ui.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/*----------------------------------------------*
 * @package:   com.ash.transport.ui.fragment
 * @fileName:  BusFragment.java
 * @describe:  城市公交总 页面碎片类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-27 21:58
 * @继承关系:   BusFragment ← BaseFragment ← [Fragment]
 *----------------------------------------------*/
public class BusFragment extends BaseFragment {
    private TabLayout tab;                  // 页面切换选项卡 按钮
    private ViewPager pager;                // 视图页面 容器
    private List<String> titles;            // 页面切换选项卡 标题 字符串集合
    private List<BaseFragment> fragments;   // 每个页面的 碎片集合
    private ViewPagerAdapter adapter;       // 视图页面容器 适配器


    // 重写父类抽象方法 设置布局ID
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_bus;
    }

    // 重写父类抽象方法 初始化视图
    @Override
    protected void initView() {
        tab = mView.findViewById(R.id.tab);
        pager = mView.findViewById(R.id.pager);
    }

    // 重写父类抽象方法 初始化数据
    @Override
    protected void initData() {
        titles = new ArrayList<>();     // 初始化 标题 字符串集合
        titles.add("1号站台");           // 添加 1号站台 标题
        titles.add("2号站台");           // 添加 2号站台 标题

        // 因为面向对象的多态特性
        // 声明 List<BaseFragment> 基本碎片元素集合
        // 可以储存 BaseFragment 的所有子类对象
        fragments = new ArrayList<>();                  // 初始化 视图页面 碎片集合
        fragments.add(new BusStationOneFragment());     // 添加 1号站台 页面碎片
        fragments.add(new BusStationTwoFragment());     // 添加 2号站台 页面碎片

        // 注意：想要在碎片中获取 FragmentManager 对象
        // 需要使用 getChildFragmentManager() 方法
        FragmentManager fm = getChildFragmentManager();
        // 使用 FragmentManager对象 标题集合 页面碎片集合
        // 来初始化视图页面容器适配器
        adapter = new ViewPagerAdapter(fm,titles,fragments);

        pager.setAdapter(adapter);      // 为视图页面容器设置适配器
        tab.setupWithViewPager(pager);  // 将视图页面容器绑定到页面切换选项卡按钮
    }
}
