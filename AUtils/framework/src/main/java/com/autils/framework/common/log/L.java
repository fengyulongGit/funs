package com.autils.framework.common.log;

import android.text.TextUtils;
import android.util.Log;

public class L {

    private static String customTagPrefix = "";
    private static boolean IS_SHOW_LOG = false;
    private static String mGlobalTag;
    private static boolean mIsGlobalTagEmpty = true;

    private L() {
    }

    public static void init(boolean isShowLog) {
        IS_SHOW_LOG = isShowLog;
    }

    public static void init(boolean isShowLog, String tag) {
        IS_SHOW_LOG = isShowLog;
        mGlobalTag = tag;
        mIsGlobalTagEmpty = TextUtils.isEmpty(mGlobalTag);
    }


    private static String generateTag() {
        if (mIsGlobalTagEmpty) {
            String tag = "%s.%s(L:%d)";
            StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
            String callerClazzName = caller.getClassName();
            callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
            tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
            tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
            return tag;
        } else {
            return mGlobalTag;
        }
    }

    public static void d(String content) {
        if (TextUtils.isEmpty(content)) {
            content = "";
        }
        if (!IS_SHOW_LOG) return;

        Log.d(generateTag(), content);
    }

    public static void d(Throwable tr) {
        if (!IS_SHOW_LOG) return;

        Log.d(generateTag(), null, tr);
    }

    public static void e(String content) {
        if (TextUtils.isEmpty(content)) {
            content = "";
        }
        if (!IS_SHOW_LOG) return;

        Log.e(generateTag(), content);
    }

    public static void e(Throwable tr) {
        if (!IS_SHOW_LOG) return;
        Log.e(generateTag(), null, tr);
    }

    public static void i(String content) {
        if (TextUtils.isEmpty(content)) {
            content = "";
        }
        if (!IS_SHOW_LOG) return;
        Log.i(generateTag(), content);
    }

    public static void i(Throwable tr) {
        if (!IS_SHOW_LOG) return;
        Log.i(generateTag(), null, tr);
    }

    public static void v(String content) {
        if (TextUtils.isEmpty(content)) {
            content = "";
        }
        if (!IS_SHOW_LOG) return;
        Log.v(generateTag(), content);
    }

    public static void v(Throwable tr) {
        if (!IS_SHOW_LOG) return;
        Log.v(generateTag(), null, tr);
    }

    public static void w(String content) {
        if (TextUtils.isEmpty(content)) {
            content = "";
        }
        if (!IS_SHOW_LOG) return;
        Log.w(generateTag(), content);
    }

    public static void w(Throwable tr) {
        if (!IS_SHOW_LOG) return;
        Log.w(generateTag(), tr);
    }

    public static void wtf(String content) {
        if (TextUtils.isEmpty(content)) {
            content = "";
        }
        if (!IS_SHOW_LOG) return;
        Log.wtf(generateTag(), content);
    }

    public static void wtf(Throwable tr) {
        if (!IS_SHOW_LOG) return;
        Log.wtf(generateTag(), tr);
    }
}
