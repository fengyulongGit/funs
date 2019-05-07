package com.autils.framework.common.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.Fragment;

/**
 * Created by fengyulong on 2018/5/10.
 */
public class AppUtils {

    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            versionName = context.getApplicationContext().getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static int getVersionCode(Context context) {
        int versionCode = -1;
        if (versionCode < 1) {
            try {
                versionCode = context.getApplicationContext().getPackageManager().getPackageInfo(context.getApplicationContext().getPackageName(), 0).versionCode;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return versionCode;
    }

    public static void openTargetApp(Context context, String packageName) {
        try {
            Intent intent = context.getApplicationContext().getPackageManager().getLaunchIntentForPackage(packageName);
            context.getApplicationContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        if (context == null || StringUtils.isNullOrEmpty(packageName)) {
            return false;
        }
        try {
            PackageInfo info = context.getApplicationContext().getPackageManager().getPackageInfo(packageName, 0);
            if (info == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void startApp(Fragment fragment, String packageName, String className, String url, int requestCode) {
        Uri data = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, data);
        intent.setClassName(packageName, className);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void gotoPermission(Context context) {
        gotoMiuiPermission(context);
    }

    /**
     * 跳转到miui的权限管理页面
     */
    private static void gotoMiuiPermission(Context context) {
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        i.setComponent(componentName);
        i.putExtra("extra_pkgname", context.getPackageName());
        try {
            context.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            gotoMeizuPermission(context);
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private static void gotoMeizuPermission(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", context.getPackageName());//BuildConfig.APPLICATION_ID
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            gotoHuaweiPermission(context);
        }
    }

    /**
     * 华为的权限管理页面
     */
    private static void gotoHuaweiPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
            context.startActivity(intent);
        }
    }
}
