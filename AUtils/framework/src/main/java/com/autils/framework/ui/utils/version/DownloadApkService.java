package com.autils.framework.ui.utils.version;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.autils.api.utils.NetWorkUtils;
import com.autils.framework.R;

import java.io.File;

/**
 * Created by fengyulong on 2018/5/16.
 */
public class DownloadApkService extends Service {
    final int NOTIFICATION_ID = 1;

    final int MAX_PROGRESS = 100;
    final int STEP_PROGRESS = 5;

    public static final int DOWNLOAD_ING = 0;// 下载中
    public static final int DOWNLOAD_FAIL = 1; // 失败
    public static final int DOWNLOAD_FINISH = 2; // 完成

    private int AKT_PROGRESS = 0;
    private long allsize;
    private int percent;

    private String downloadUrl;
    private String fileName;
    private String title;

    private Notification nf;
    private RemoteViews rv;
    private NotificationManager notificationManager;
    String APKFilePath = "";

    public static boolean isDownloading = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        if (isDownloading) {
            return;
        }
        downloadUrl = intent.getStringExtra("downloadUrl");
        fileName = intent.getStringExtra("fileName");
        title = intent.getStringExtra("title");
        APKFilePath = intent.getStringExtra("filePath");
        new File(APKFilePath).getParentFile().mkdirs();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nf = new Notification(android.R.drawable.stat_sys_download, title + "下载中...", System.currentTimeMillis());
        rv = new RemoteViews(this.getPackageName(), R.layout.apk_download_notification);
        rv.setImageViewResource(R.id.notifyImage, android.R.drawable.stat_sys_download);
        rv.setProgressBar(R.id.customProgressBar, MAX_PROGRESS, AKT_PROGRESS, false);
        rv.setTextViewText(R.id.notifyText, "0%");
        rv.setTextViewText(R.id.notifyMsg, title + "下载中...");
        nf.contentView = rv;

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(), 0);
        nf.contentIntent = contentIntent;

        notificationManager.notify(NOTIFICATION_ID, nf);

        NetWorkUtils.download(downloadUrl, APKFilePath, new NetWorkUtils.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                isDownloading = false;

                nf = new Notification(android.R.drawable.stat_sys_download_done, title + "下载完成，点击安装", System.currentTimeMillis());
                rv = new RemoteViews(DownloadApkService.this.getPackageName(), R.layout.apk_install_notification);
                rv.setImageViewResource(R.id.notifyImage, android.R.drawable.stat_sys_download_done);
                rv.setTextViewText(R.id.notifyMsg, title + "下载完成，点击安装");
                nf.contentView = rv;

                Intent intent = new Intent(DownloadApkService.this, InstallAPKActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("fileName", fileName);
                intent.putExtra("filePath", APKFilePath);
                intent.putExtra("NOTIFICATION_ID", NOTIFICATION_ID);

                PendingIntent contentIntent = PendingIntent.getActivity(DownloadApkService.this, 0, intent, 0);
                nf.contentIntent = contentIntent;

                notificationManager.notify(NOTIFICATION_ID, nf);
                AKT_PROGRESS = 0;

                AppInstallUtils.installApk(DownloadApkService.this, APKFilePath, fileName);
                stopSelf();
            }

            @Override
            public void onDownloading(int progress) {
                isDownloading = true;

                rv.setProgressBar(R.id.customProgressBar, MAX_PROGRESS, progress, false);
                rv.setTextViewText(R.id.notifyText, progress + "%");
                notificationManager.notify(NOTIFICATION_ID, nf);
            }

            @Override
            public void onDownloadFailed() {
                isDownloading = false;

                nf = new Notification(android.R.drawable.stat_sys_download_done, title + "下载失败！", System.currentTimeMillis());
                rv = new RemoteViews(DownloadApkService.this.getPackageName(), R.layout.apk_install_notification);
                rv.setImageViewResource(R.id.notifyImage, android.R.drawable.stat_sys_download_done);
                rv.setTextViewText(R.id.notifyMsg, title + "下载失败！");
                nf.contentView = rv;

                Intent intent2 = new Intent();

                PendingIntent contentIntent2 = PendingIntent.getActivity(DownloadApkService.this, 0, intent2, 0);
                nf.contentIntent = contentIntent2;

                notificationManager.notify(NOTIFICATION_ID, nf);
                AKT_PROGRESS = 0;

                stopSelf();
            }
        });
    }
}
