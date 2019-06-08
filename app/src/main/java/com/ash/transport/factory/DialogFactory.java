package com.ash.transport.factory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.ash.transport.config.AppConfig;
import com.ash.transport.ui.widget.EditDialog;
import com.ash.transport.utils.Session;

/*----------------------------------------------*
 * @package:   com.ash.transport.utils
 * @fileName:  DialogFactory.java
 * @describe:  对话框工厂类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-21 15:08
 *----------------------------------------------*/
public class DialogFactory {
    private static Dialog dialog = null;

    // 定义 监听器 接口
    public interface OnListener {
        void onAfter();
    }

    // 定义 显示对话框 方法
    public static void showDialog(Context context, String title, String msg,
                                  final OnListener onAfter) {

        // Dialog 通过构建者模式 创建
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);    // 设置标题
        builder.setMessage(msg);    // 设置内容
        builder.setNegativeButton("取消", null);  // 设置取消按钮

        // 忽略按钮
        //builder.setNeutralButton("忽略", null);

        // 设置确定按钮 并且监听点击事件
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 交给外部实现接口处理
                onAfter.onAfter();
            }
        });

        // 通过设置好的构建者对象创建 Dialog
        dialog = builder.create();

        // 如果 dialog 没有显示
        if (!dialog.isShowing()) {

            // Dialog 透明度
            //Window window = dialog.getWindow();
            //WindowManager.LayoutParams lp = window.getAttributes();
            //lp.alpha = 0.8f;
            //window.setAttributes(lp);

            // 则把 dialog 显示出来
            dialog.show();
        }
    }

    // 定义 显示单选对话框 方法
    public static void showSelectDialog(final Context context, String title, String[] items,
                                        final EditDialog.OnListener onAfter) {

        // Dialog 通过构建者模式 创建
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // 设置标题
        builder.setTitle(title);

        int checkItem = 0;  // 初始化默认选中第一项

        // 如果当前服务器为本地仿真沙盘
        if (!Session.ipFlag.equals(AppConfig.IP_REMOTE)) {
            // 默认选中第二项
            checkItem = 1;
        }

        // 设置子项选择事件
        builder.setSingleChoiceItems(items, checkItem, new DialogInterface.OnClickListener() {

            // 重写 DialogInterface.OnClickListener 接口事件
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    // 选择 远端虚拟沙盘
                    case 0:
                        // 储存IP地址
                        Session.ip = AppConfig.IP_DEFAULT;      // 47.106.226.220
                        Session.ipFlag = AppConfig.IP_REMOTE;   // remote
                        ToastFactory.show(context, "使用远端虚拟沙盘：" + AppConfig.IP_DEFAULT, true);
                        break;

                    // 选择 本地仿真沙盘
                    case 1:
                        // 显示带编辑框的对话框 并交给外部实现接口处理
                        EditDialog.show(context, "设置IP地址", onAfter);
                        break;
                }

                dialog.dismiss();           // 单选对话框需要手动关闭
            }
        });

        // 通过设置好的构建者对象创建 Dialog
        dialog = builder.create();

        // 如果 dialog 没有显示
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }
}
