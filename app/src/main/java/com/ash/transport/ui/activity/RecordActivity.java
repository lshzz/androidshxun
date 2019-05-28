package com.ash.transport.ui.activity;

import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ash.transport.R;
import com.ash.transport.dao.RecordDao;
import com.ash.transport.model.RecordInfo;
import com.ash.transport.ui.adapter.RecordsAdapter;

import java.util.List;

/*----------------------------------------------*
 * @package:   com.ash.transport.ui.activity
 * @fileName:  RecordActivity.java
 * @describe:  充值历史记录活动类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-23 17:27
 * @继承关系:   RecordActivity ← BaseActivity ← [AppCompatActivity]
 *----------------------------------------------*/
public class RecordActivity extends BaseActivity implements View.OnClickListener {
    private ListView lvRecords;         // 记录 列表视图
    private TextView tvTrip;            // 提示 文本框
    private RecordDao recordDao;        // record表访问器

    private List<RecordInfo> records;   // 充值记录类 集合数组


    // 重写父类抽象方法 设置布局ID
    @Override
    protected int setLayoutId() {
        return R.layout.activity_record;
    }

    // 重写父类抽象方法 初始化视图
    @Override
    protected void initView() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        RecordActivity.this.setTitle("充值历史");

        lvRecords = findViewById(R.id.lv_records);
        tvTrip = findViewById(R.id.tv_no_records);
    }

    // 重写父类抽象方法 初始化数据
    @Override
    protected void initData() {

        // 使用record表的访问器从数据库获取全部记录
        recordDao = new RecordDao(RecordActivity.this);
        records = recordDao.selectAll();

        // 如有记录则填充ListView并显示，否则隐藏ListView，显示提示TextView
        if (!records.isEmpty()) {
            lvRecords.setAdapter(new RecordsAdapter(RecordActivity.this, records));

            lvRecords.setVisibility(View.VISIBLE);
            tvTrip.setVisibility(View.GONE);
        } else {
            lvRecords.setVisibility(View.GONE);
            tvTrip.setVisibility(View.VISIBLE);
        }

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

        }
    }
}
