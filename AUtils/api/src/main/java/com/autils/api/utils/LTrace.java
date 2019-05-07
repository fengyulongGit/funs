package com.autils.api.utils;

import java.lang.reflect.Method;

/**
 * Created by fengyulong on 2018/5/11.
 */
public class LTrace {
    private LTrace() {
    }

    public static void d(String content) {
        trace("d", content);
    }

    public static void d(Throwable tr) {
        trace("d", tr);
    }

    public static void e(String content) {
        trace("e", content);
    }

    public static void e(Throwable tr) {
        trace("e", tr);
    }

    public static void i(String content) {
        trace("i", content);
    }

    public static void i(Throwable tr) {
        trace("i", tr);
    }

    public static void v(String content) {
        trace("v", content);
    }

    public static void v(Throwable tr) {
        trace("v", tr);
    }

    public static void w(String content) {
        trace("w", content);
    }

    public static void w(Throwable tr) {
        trace("w", tr);
    }

    public static void wtf(String content) {
        trace("wtf", content);
    }

    public static void wtf(Throwable tr) {
        trace("wtf", tr);
    }

    private static void trace(String methodNameStr, String content) {
        trace(methodNameStr, content, String.class);
    }

    private static void trace(String methodNameStr, Throwable tr) {
        trace(methodNameStr, tr, Throwable.class);
    }

    private static void trace(String methodNameStr, Object object, Class... typeClass) {

        try {
            Class L = Class.forName("com.autils.framework.common.log.L");
            Method method = L.getMethod(methodNameStr, typeClass);
            method.invoke(null, object);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
