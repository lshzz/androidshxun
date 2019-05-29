package com.ash.transport.ui.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import com.ash.transport.factory.ToastFactory;

public class EditDialog {

    public interface OnListener {
        void onAfter(String input);
    }

    public static void show(final Context context, String title, final EditDialog.OnListener onAfter) {
        final EditText et = new EditText(context);

        new AlertDialog.Builder(context).setTitle(title)
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();

                        if (input.equals("")) {
                            ToastFactory.show(context, "内容不能为空！");
                        } else {
                            onAfter.onAfter(input);
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
}
