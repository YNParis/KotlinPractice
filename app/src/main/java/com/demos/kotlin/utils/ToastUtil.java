package com.demos.kotlin.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.demos.kotlin.R;


/**
 * @Descrpiton:自定义Toast，不会多次弹出
 */
public class ToastUtil {
    private static Toast toast;

    public static void show(Context context, String msg) {
        View view = View.inflate(context, R.layout.dialog_toast, null);
        TextView tvMsg = view.findViewById(R.id.tv_toast_msg);
        tvMsg.setText(msg);
        //防止多次点击按钮出现很多toast一直不消失
        if (toast != null) {
            toast.setView(view);
        } else {
            toast = new Toast(context);
            toast.setView(view);
            toast.setGravity(Gravity.CENTER, 0, 500);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void cancleToast() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }
}
