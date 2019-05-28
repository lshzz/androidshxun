package com.ash.transport.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ash.transport.R;
import com.ash.transport.factory.ToastFactory;
import com.ash.transport.ui.fragment.AboutFragment;
import com.ash.transport.ui.fragment.BusFragment;
import com.ash.transport.ui.fragment.CarFragment;
import com.ash.transport.ui.fragment.EnvFragment;
import com.ash.transport.ui.fragment.RoadFragment;

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
    private long exitTime = 0;


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

        // 进入MainActivity后跳转我的车辆页面
        gotoFragment(R.id.content_main, new CarFragment());
        MainActivity.this.setTitle(R.string.menu_car);
    }

    // 重写超类抽象方法 按下返回键事件
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // 如果侧滑菜单打开
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            // 则关闭菜单
            drawer.closeDrawer(GravityCompat.START);
        } else {

            // 按两次返回键退出
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                // 第一次 弹出提示
                ToastFactory.show(MainActivity.this,"再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                // 第二次 退出程序
                super.onBackPressed();
                finish();
            }

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
                gotoFragment(R.id.content_main, new EnvFragment());
                MainActivity.this.setTitle(R.string.menu_env);
                break;

            // 道路状态
            case R.id.nav_road:
                gotoFragment(R.id.content_main, new RoadFragment());
                MainActivity.this.setTitle(R.string.menu_road);
                break;

            // 城市公交
            case R.id.nav_bus:
                gotoFragment(R.id.content_main, new BusFragment());
                MainActivity.this.setTitle(R.string.menu_bus);
                break;

            // 关于
            case R.id.nav_about:
                gotoFragment(R.id.content_main, new AboutFragment());
                MainActivity.this.setTitle(R.string.menu_about);
                break;

            // 联系客服
            case R.id.nav_help:
                // 跳转到拨号界面
                // 需添加电话拨号权限 android.permission.CALL_PHONE
                Uri uri = Uri.parse("tel:10086");
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
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
