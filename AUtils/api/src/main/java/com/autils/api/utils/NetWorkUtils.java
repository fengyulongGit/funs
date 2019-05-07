package com.autils.api.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class NetWorkUtils {
    public static void donwloadFile(String url, String filePath) {
        donwloadFile(url, filePath, null);
    }

    public static void donwloadFile(final String url, final String filePath, final OnDownloadListener listener) {
        Observable.just(true)
                .subscribeOn(Schedulers.io())
                .map(new Function<Boolean, Boolean>() {
                    @Override
                    public Boolean apply(Boolean aBoolean) {
                        return NetWorkUtils.downloadFile(url, filePath, listener);
                    }
                })
                .observeOn(Schedulers.io())
                .subscribeWith(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Object object) {
                        // 下载完成
                        listener.onDownloadSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 下载失败
                        listener.onDownloadFailed();
                        LTrace.e(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public static void download(final String url, final String filePath, final OnDownloadListener listener) {
        new Thread() {
            @Override
            public void run() {
                downloadFile(url, filePath, listener);
            }
        }.start();
    }

    private static boolean downloadFile(String url, String filePath, OnDownloadListener listener) {
        URL htmlUrl = null;
        InputStream inStream = null;

        String tmpFilePath = filePath + ".tmp";


        boolean flag = false;

        byte[] buffer = new byte[1024];
        int byteread = 0, size = 0;

        try {
            htmlUrl = new URL(url);
            URLConnection connection = htmlUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            inStream = httpConnection.getInputStream();
            FileOutputStream fs = new FileOutputStream(tmpFilePath);

            size = httpConnection.getContentLength();

            int downloadSize = 0;

            if (listener != null) {
                listener.onDownloading(0);
            }

            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
                if (listener != null) {
                    downloadSize += byteread;
                    int progress = (int) (downloadSize * 1.0f / size * 100);
                    listener.onDownloading(progress);
//                    handler.handleMessage(handler.obtainMessage(1, downloadSize));
                }
            }

            fs.flush();
            fs.close();

            flag = renameFile(tmpFilePath, filePath);

            if (listener != null) {
                listener.onDownloadSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onDownloadFailed();
            }
        }

        return flag;
    }

    private static boolean renameFile(String willRenameFilePath, String newNameFilePath) {
        boolean res = false;
        try {
            if (willRenameFilePath == null || willRenameFilePath.length() == 0 || newNameFilePath == null || newNameFilePath.length() == 0) {
                return res;
            }
            File f = new File(willRenameFilePath);
            File f_1 = new File(newNameFilePath);
            res = f.renameTo(f_1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess();

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }

}
