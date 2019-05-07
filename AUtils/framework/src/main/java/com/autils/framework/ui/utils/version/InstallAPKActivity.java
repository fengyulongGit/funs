package com.autils.framework.ui.utils.version;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by fengyulong on 2018/5/16.
 */
public class InstallAPKActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int NOTIFICATION_ID = getIntent().getIntExtra("NOTIFICATION_ID", 0);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);

        final String fileName = getIntent().getStringExtra("fileName");
        final String filePath = getIntent().getStringExtra("filePath");

        AppInstallUtils.installApk(this, filePath, fileName);

        finish();
    }
}
