package com.autils.framework.common.thirdparty;

import android.content.Context;

import com.autils.framework.Launcher;

import java.lang.reflect.Method;

/**
 * Created by fengyulong on 2018/5/11.
 */
public class UmengTraceHelper {
    private static void trace(Context context, String methodNameStr, String string) {
        if (Launcher.getInstance().isOfficial()) {
            try {
                Method[] methods = Class.forName("com.umeng.analytics.MobclickAgent").getDeclaredMethods();
                for (Method method : methods) {
                    if (method.getName().equals(methodNameStr)) {
                        if (context != null) {
                            method.invoke(null, context);
                        } else {
                            method.invoke(null, string);
                        }
                    }
                }
            } catch (Exception e) {
//                L.e(e);
            }
        }
    }

    public static void onPause(Context context) {
        trace(context, "onPause", null);
    }

    public static void onResume(Context context) {
        trace(context, "onResume", null);
    }

    public static void onPageStart(String string) {
        trace(null, "onPageStart", string);
    }

    public static void onPageEnd(String string) {
        trace(null, "onPageEnd", string);
    }

    public static void onKillProcess(Context context) {
        trace(context, "onKillProcess", null);
    }
}
