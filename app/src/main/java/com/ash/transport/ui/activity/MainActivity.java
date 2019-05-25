package com.ash.transport.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ash.transport.R;
import com.ash.transport.ui.fragment.CarFragment;

/*----------------------------------------------*
 * @package:   com.ash.transport.ui.activity
 * @fileName:  MainActivity.java
 * @describe:  主要活动类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-21 14:17
 * @继承关系:   MainActivity ← BaseActivity ← [AppCompatActivity]
 *----------------------------------------------*/
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;                // 顶部工具条
    private DrawerLayout drawer;            // 侧滑抽屉视图
    private NavigationView navigationView;  //
    private TextView tvName;
    private TextView tvContact;

    // 重写父类抽象方法 设置布局ID
    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    // 重写父类抽象方法 初始化视图
    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        View view = navigationView.getHeaderView(0);
        tvName = view.findViewById(R.id.tv_name);
        tvContact = view.findViewById(R.id.tv_contact);
    }

    // 重写父类抽象方法 初始化数据
    @Override
    protected void initData() {
        SharedPreferences shared = getSharedPreferences("userInfo", MODE_PRIVATE);
        tvName.setText(shared.getString("name", "name"));
        tvContact.setText(shared.getString("contact", "contact"));

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



    }

    // 重写超类抽象方法 按下返回键事件
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // 重写超类抽象方法 右上角菜单创建事件
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // 重写超类抽象方法 右上角菜单选中事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                SharedPreferences shared = getSharedPreferences("userInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putBoolean("isGuide", true);
                editor.apply();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;

            case R.id.action_exit:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // 重写超类抽象方法 侧滑菜单选中事件
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 我的车辆
            case R.id.nav_car:
                gotoFragment(R.id.content_main, new CarFragment());
                MainActivity.this.setTitle(R.string.menu_car);
                break;

            // 出行环境
            case R.id.nav_env:
                break;

            // 道路状态
            case R.id.nav_road:
                break;

            // 城市公交
            case R.id.nav_bus:
                break;

            // 设置
            case R.id.nav_settings:
                break;

            // 联系客服
            case R.id.nav_help:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // 定义 切换指定碎片页面 方法
    public void gotoFragment(int content, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(content, fragment)
                .commit();
    }
}
