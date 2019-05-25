package com.ash.transport.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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
    protected Context mContext;
    protected View mView;
    protected String mBasURL;
    protected Bundle mSavedInstanceState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(setLayoutId(), container, false);
        mContext = mView.getContext();
        initView();
        initData();
        getIP();
        return mView;
    }

    private void getIP() {
        SharedPreferences sh = getActivity().getSharedPreferences("ipset", 0);
        mBasURL = "http://" + sh.getString("ip", "192.168.1.133") + ":" + 8080
                + "/transportservice/type/jason/action/";
    }

    protected abstract int setLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected void gotoFragment(int content, Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(content, fragment)
                .commit();
    }

    protected void gotoFragment(int content, Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(content, fragment)
                .commit();

    }
}
