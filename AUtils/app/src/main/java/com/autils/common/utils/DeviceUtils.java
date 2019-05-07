package com.autils.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.autils.framework.Launcher;
import com.autils.framework.common.log.L;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by fengyulong on 2019/4/10.
 */
public class DeviceUtils {

    /**
     * 获取MAC地址
     */
    public static String getMacAddress() {
        String mac = "02:00:00:00:00:00";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mac = getMacDefault(Launcher.getInstance().getApplication());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mac = getMacFromFile();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mac = getMacFromHardware();
        }
        return mac;
    }

    /**
     * Android  6.0 之前（不包括6.0）
     * 必须的权限  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     */
    private static String getMacDefault(Context context) {
        String mac = "02:00:00:00:00:00";
        if (context == null) {
            return mac;
        }

        WifiManager wifi = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) {
            return mac;
        }
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
            L.e(e);
        }
        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH);
        }
        return mac;
    }

    /**
     * Android 6.0（包括） - Android 7.0（不包括）
     */
    private static String getMacFromFile() {
        String WifiAddress = "02:00:00:00:00:00";
        try {
            WifiAddress = new BufferedReader(new FileReader(new File("/sys/class/net/wlan0/address"))).readLine();
        } catch (Exception e) {
            L.e(e);
        }
        return WifiAddress;
    }

    /**
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET" />
     */
    private static String getMacFromHardware() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            L.e(e);
        }
        return "02:00:00:00:00:00";
    }

    @SuppressLint("MissingPermission")
    public static String getWIFISSID() {
        String ssid = "unknown id";

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O || Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

            WifiManager mWifiManager = (WifiManager) Launcher.getInstance().getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (mWifiManager != null) {
                WifiInfo info = mWifiManager.getConnectionInfo();
                return info.getSSID().replace("\"", "");
            }
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1) {

            ConnectivityManager connManager = (ConnectivityManager) Launcher.getInstance().getApplication().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connManager != null) {
                NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
                if (networkInfo.isConnected()) {
                    if (networkInfo.getExtraInfo() != null) {
                        return networkInfo.getExtraInfo().replace("\"", "");
                    }
                }
            }
        }
        return ssid;
    }

    /**
     * @param slotId slotId为卡槽Id，它的值为 0、1；
     * @return
     */
    public static String getIMEI(int slotId) {
        try {
            TelephonyManager manager = (TelephonyManager) Launcher.getInstance().getApplication().getSystemService(Context.TELEPHONY_SERVICE);
            Method method = manager.getClass().getMethod("getImei", int.class);
            return (String) method.invoke(manager, slotId);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param slotId slotId为卡槽Id，它的值为 0、1；
     * @return
     */
    public static String getIMSI(int slotId) {
        try {
            TelephonyManager manager = (TelephonyManager) Launcher.getInstance().getApplication().getSystemService(Context.TELEPHONY_SERVICE);
            Method method = manager.getClass().getMethod("getSubscriberId", int.class);
            return (String) method.invoke(manager, slotId);
        } catch (Exception e) {
            L.e(e);
            return "";
        }
    }
}
