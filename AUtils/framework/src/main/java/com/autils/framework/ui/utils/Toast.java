package com.autils.framework.ui.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.autils.framework.R;

/**
 * Created by fengyulong on 2018/5/14.
 */
public class Toast {
    public static void toast(Context context, int message) {
        toast(context, context.getResources().getString(message));
    }

    public static void toast(Context context, String message) {
        if (null != message) {
            android.widget.Toast toast = new android.widget.Toast(context);
            View view = LayoutInflater.from(context).inflate(R.layout.view_toast, null);
            TextView tv = (TextView) view.findViewById(R.id.toast_message);
            tv.setText(message);
            toast.setView(view);
            toast.setDuration(android.widget.Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
