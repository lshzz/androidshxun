package com.ash.transport.ui.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import com.ash.transport.factory.ToastFactory;

/*----------------------------------------------*
 * @package:   com.ash.transport.ui.widget
 * @fileName:  EditDialog.java
 * @describe:  带编辑框的对话框 小部件
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-06-03 20:24
 *----------------------------------------------*/
public class EditDialog {

    // 定义 事件监听器接口
    public interface OnListener {
        // 接收编辑库输入字符串
        void onAfter(String input);
    }

    // 定义 显示 静态方法
    public static void show(final Context context, String title, final EditDialog.OnListener onAfter) {
        // 初始化一个编辑框
        final EditText et = new EditText(context);

        new AlertDialog.Builder(context).setTitle(title)
                .setView(et)    // 给对话框视图添加一个编辑框
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();

                        if (input.equals("")) {
                            ToastFactory.show(context, "内容不能为空！");
                        } else {
                            // 传入 编辑框输入字符串 给外部接口实现回调
                            onAfter.onAfter(input);
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();    // 立即显示对话框
    }
}
