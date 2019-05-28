package com.ash.transport.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ash.transport.R;
import com.ash.transport.ui.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/*----------------------------------------------*
 * @package:   com.ash.transport.ui.fragment
 * @fileName:  AboutFragment.java
 * @describe:  关于页面 碎片类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-28 18:23
 * @继承关系:   AboutFragment ← BaseFragment ← [Fragment]
 *----------------------------------------------*/
public class AboutFragment extends BaseFragment implements View.OnClickListener{
    private ImageView ivFace;
    private TextView tvAuthor;
    private TextView tvLicense;
    private TextView tvGitHub;

    // 重写父类抽象方法 设置布局ID
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_about;
    }

    // 重写父类抽象方法 初始化视图
    @Override
    protected void initView() {
        ivFace = mView.findViewById(R.id.iv_face);
        tvAuthor = mView.findViewById(R.id.tv_author);
        tvLicense = mView.findViewById(R.id.tv_license);
        tvGitHub = mView.findViewById(R.id.tv_github);
    }

    // 重写父类抽象方法 初始化数据
    @Override
    protected void initData() {
        ivFace.setOnClickListener(this);
        tvAuthor.setOnClickListener(this);
        tvLicense.setOnClickListener(this);
        tvGitHub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_face:
                Uri uriFace = Uri.parse("https://github.com/Ashinch");
                Intent intentFace = new Intent(Intent.ACTION_VIEW, uriFace);
                startActivity(intentFace);
                break;
            case R.id.tv_author:
                Uri uriAuthor = Uri.parse("https://github.com/Ashinch");
                Intent intentAuthor = new Intent(Intent.ACTION_VIEW, uriAuthor);
                startActivity(intentAuthor);
                break;
            case R.id.tv_license:
                Uri uriLicense = Uri.parse("https://github.com/Ashinch/transport/blob/master/LICENSE.md");
                Intent intentLicense = new Intent(Intent.ACTION_VIEW, uriLicense);
                startActivity(intentLicense);
                break;
            case R.id.tv_github:
                Uri uriGithub = Uri.parse("https://github.com/Ashinch/transport");
                Intent intentGitHub = new Intent(Intent.ACTION_VIEW, uriGithub);
                startActivity(intentGitHub);
                break;
        }
    }
}
