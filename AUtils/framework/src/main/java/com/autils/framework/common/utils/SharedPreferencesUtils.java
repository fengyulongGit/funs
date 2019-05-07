package com.autils.framework.common.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.autils.framework.Launcher;

import java.util.Map;
import java.util.Set;

/**
 * Created by sy on 2016/08/19.
 */

public class SharedPreferencesUtils {
    private SharedPreferencesUtils() {
    }

    public static Map<String, ?> getAll() {
        return getAll("");
    }

    public static Map<String, ?> getAll(String shareName) {
        return getSharedPreferences(shareName).getAll();
    }

    public static void putString(String key, String value) {
        putString("", key, value);
    }

    public static void putString(String shareName, String key, String value) {
        getEditor(shareName).putString(StringUtils.getMD5(key), value).commit();
    }

    public static String getString(String key, String defaultValue) {
        return getString("", key, defaultValue);
    }

    public static String getString(String shareName, String key, String defaultValue) {
        return getSharedPreferences(shareName).getString(StringUtils.getMD5(key), defaultValue);
    }

    public static void putStringSet(String key, Set<String> values) {
        putStringSet("", key, values);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void putStringSet(String shareName, String key, Set<String> values) {
        getEditor(shareName).putStringSet(StringUtils.getMD5(key), values).commit();
    }

    public static Set<String> getStringSet(String key, Set<String> defaultValues) {
        return getStringSet("", key, defaultValues);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> getStringSet(String shareName, String key, Set<String> defaultValues) {
        return getSharedPreferences(shareName).getStringSet(StringUtils.getMD5(key), defaultValues);
    }

    public static void putInt(String key, int value) {
        putInt("", key, value);
    }

    public static void putInt(String shareName, String key, int value) {
        getEditor(shareName).putInt(StringUtils.getMD5(key), value).commit();
    }

    public static int getInt(String key, int defaultValue) {
        return getInt("", key, defaultValue);
    }

    public static int getInt(String shareName, String key, int defaultValue) {
        return getSharedPreferences(shareName).getInt(StringUtils.getMD5(key), defaultValue);
    }

    public static void putLong(String key, long value) {
        putLong("", key, value);
    }

    public static void putLong(String shareName, String key, long value) {
        getEditor(shareName).putLong(StringUtils.getMD5(key), value).commit();
    }

    public static long getLong(String key, long defaultValue) {
        return getLong("", key, defaultValue);
    }

    public static long getLong(String shareName, String key, long defaultValue) {
        return getSharedPreferences(shareName).getLong(StringUtils.getMD5(key), defaultValue);
    }

    public static void putFloat(String key, float value) {
        putFloat("", key, value);
    }

    public static void putFloat(String shareName, String key, float value) {
        getEditor(shareName).putFloat(StringUtils.getMD5(key), value).commit();
    }

    public static float getFloat(String key, float defaultValue) {
        return getFloat("", key, defaultValue);
    }

    public static float getFloat(String shareName, String key, float defaultValue) {
        return getSharedPreferences(shareName).getFloat(StringUtils.getMD5(key), defaultValue);
    }

    public static void putBoolean(String key, boolean value) {
        putBoolean("", key, value);
    }

    public static void putBoolean(String shareName, String key, boolean value) {
        getEditor(shareName).putBoolean(StringUtils.getMD5(key), value).commit();
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getBoolean("", key, defaultValue);
    }

    public static boolean getBoolean(String shareName, String key, boolean defaultValue) {
        return getSharedPreferences(shareName).getBoolean(StringUtils.getMD5(key), defaultValue);
    }

    public static boolean contains(String key) {
        return contains("", key);
    }

    public static boolean contains(String shareName, String key) {
        return getSharedPreferences(shareName).contains(StringUtils.getMD5(key));
    }

    public static void remove(String key) {
        remove("", key);
    }

    public static void remove(String shareName, String key) {
        getEditor(shareName).remove(StringUtils.getMD5(key)).commit();
    }

    public static void clear() {
        clear("");
    }

    public static void clear(String shareName) {
        getEditor(shareName).clear().commit();
    }

    private static SharedPreferences.Editor getEditor() {
        return getEditor("");
    }

    private static SharedPreferences.Editor getEditor(String shareName) {
        return getSharedPreferences(shareName).edit();
    }

    private static SharedPreferences getSharedPreferences(String shareName) {
        if (StringUtils.isNullOrEmpty(shareName)) {
            return PreferenceManager.getDefaultSharedPreferences(Launcher.getInstance().getApplication());
        } else {
            return Launcher.getInstance().getApplication().getSharedPreferences(StringUtils.getMD5(shareName), Context.MODE_PRIVATE);
        }
    }
}
