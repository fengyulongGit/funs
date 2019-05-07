package com.autils.framework.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.autils.framework.R;
import com.autils.framework.ui.view.dialog.ConfirmDialog;

import java.lang.reflect.Field;


/**
 * Created by fengyulong on 2018/5/15.
 */
public class UIUtils {
    public static void showOpenPermissionDialog(final Context context, String permissionName) {
        new ConfirmDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.permission))
                .setMessage(context.getResources().getString(R.string.open_permission, permissionName))
                .setNegativeButton(context.getResources().getString(R.string.cancel), null, context.getResources().getColor(R.color.text_333))
                .setPositiveButton(context.getResources().getString(R.string.setting_now), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
//                        context.startActivity(intent);
                        AppUtils.gotoPermission(context);
                    }
                })
                .create().show();
    }

    public static int[] getScreenSize(Resources resources) {
        int width = resources.getDisplayMetrics().widthPixels;
        int height = resources.getDisplayMetrics().heightPixels;
        int[] result = new int[2];
        result[0] = width;
        result[1] = height;
        return result;
    }

    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager inputMethodManager = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) {
            return;
        }

        String[] viewArray = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field filed;
        Object filedObject;

        for (String view : viewArray) {
            try {
                filed = inputMethodManager.getClass().getDeclaredField(view);
                if (!filed.isAccessible()) {
                    filed.setAccessible(true);
                }
                filedObject = filed.get(inputMethodManager);
                if (filedObject != null && filedObject instanceof View) {
                    View fileView = (View) filedObject;
                    if (fileView.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        filed.set(inputMethodManager, null); // 置空，破坏掉path to gc节点
                    } else {
                        break;// 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public static int dip2px(float dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, Resources.getSystem().getDisplayMetrics()) + 0.5f);
    }

    public static int statusBarHeight() {
        int result = dip2px(20);
        int resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = Resources.getSystem().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void hideSoftInput(Activity activity) {
        hideSoftInput(activity.getCurrentFocus());
    }

    public static void hideSoftInput(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
