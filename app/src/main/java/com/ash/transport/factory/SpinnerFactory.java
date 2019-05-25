package com.ash.transport.factory;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ash.transport.R;

/*----------------------------------------------*
 * @package:   com.ash.transport.factory
 * @fileName:  SpinnerFactory.java
 * @describe:  下拉列表菜单工厂类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-21 20:22
 *----------------------------------------------*/
public class SpinnerFactory {
    // 定义 [项目选中事件监听器] 公开接口
    public interface OnItemSelected {
        void onSelected(int position);
    }

    //  初始化
    public static void getSpinner(Context context, Spinner spinner, String[] datas,
                                  final OnItemSelected listener) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.item_spinner, datas);
        adapter.setDropDownViewResource(R.layout.item_spinner);
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //  项目选中事件
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    listener.onSelected(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }
}
