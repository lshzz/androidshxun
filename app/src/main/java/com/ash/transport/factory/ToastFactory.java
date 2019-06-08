package com.ash.transport.factory;

import android.content.Context;
import android.widget.Toast;

/*----------------------------------------------*
 * @package:   com.ash.transport.utils
 * @fileName:  ToastFactory.java
 * @describe:  自定义消息框工厂类
 *----------------------------------------------*
 * @author:    ash
 * @email:     Glaxyinfinite@outlook.com
 * @date:      on 2019-05-21 14:26
 *----------------------------------------------*/
public class ToastFactory {
    // 重载 短消息
    public static void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    // 重载 长消息
    public static void show(Context context, String text, boolean isLong) {
        int length = isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
        Toast.makeText(context, text, length).show();
    }
}
