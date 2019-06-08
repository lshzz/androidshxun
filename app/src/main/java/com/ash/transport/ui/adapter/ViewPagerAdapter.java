package com.ash.transport.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ash.transport.ui.fragment.BaseFragment;

import java.util.List;

/*----------------------------------------------*
 * @package:   com.ash.transport.ui.adapter
 * @fileName:  ViewPagerAdapter.java
 * @describe:  城市公交页面切换碎片适配器
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-27 22:05
 * @继承关系:   ViewPagerAdapter ← [FragmentPagerAdapter]
 *----------------------------------------------*/
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<String> titles;            // 标题列表
    private List<BaseFragment> fragments;   // 碎片列表

    // 构造函数
    public ViewPagerAdapter(FragmentManager fm, List<String> titles, List<BaseFragment> fragments) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }

    // 重写 获取子项 方法
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    // 重写 获取总数量 方法
    @Override
    public int getCount() {
        // return fragments.size();
        return titles.size();
    }

    // 重写 获取页面标题 方法
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
