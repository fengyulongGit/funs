package com.autils.framework.ui.view.imageselector;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.autils.framework.R;

import java.util.ArrayList;

/**
 * 图片选择器
 * Created by nereo on 16/3/17.
 */
public class CameraTaker {

    public static final String EXTRA_RESULT = MultiImageSelectorActivity.EXTRA_RESULT;

    private boolean mShowCamera = true;
    private int mMaxCount = 9;
    private int mMode = MultiImageSelectorActivity.MODE_MULTI;
    private ArrayList<String> mOriginData;
    private static CameraTaker sSelector;

    private CameraTaker() {
    }


    public static CameraTaker create() {
        if (sSelector == null) {
            sSelector = new CameraTaker();
        }
        return sSelector;
    }

    public void start(Activity activity, int requestCode) {
        if (hasPermission(activity)) {
            activity.startActivityForResult(createIntent(activity), requestCode);
        } else {
            Toast.makeText(activity, R.string.mis_error_no_permission, Toast.LENGTH_SHORT).show();
        }
    }

    public void start(Fragment fragment, int requestCode) {
        if (hasPermission(fragment.getContext())) {
            fragment.startActivityForResult(createIntent(fragment.getContext()), requestCode);
        } else {
            Toast.makeText(fragment.getContext(), R.string.mis_error_no_permission, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // Permission was added in API Level 16
            return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private Intent createIntent(Context context) {
        Intent intent = new Intent(context, CametaTakerActivity.class);
        return intent;
    }
}
