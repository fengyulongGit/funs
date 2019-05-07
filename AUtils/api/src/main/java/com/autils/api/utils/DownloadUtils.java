package com.autils.api.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by fengyulong on 2018/5/16.
 */
public class DownloadUtils {
    private static DownloadUtils instance;

    private final OkHttpClient okHttpClient;

    private DownloadUtils() {
        okHttpClient = new OkHttpClient();
    }

    public static DownloadUtils getInstance() {
        if (null == instance) {
            synchronized (DownloadUtils.class) {
                if (null == instance) {
                    instance = new DownloadUtils();
                }
            }
        }
        return instance;
    }

    /**
     * @param url      下载连接
     * @param filePath 储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void download(final String url, final String filePath, final OnDownloadListener listener) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                listener.onDownloadFailed();
                LTrace.e(e);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();

                    String tmpFilePath = filePath + ".tmp";

                    fos = new FileOutputStream(new File(tmpFilePath));
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
                        listener.onDownloading(progress);
                    }
                    fos.flush();

                    renameFile(tmpFilePath, filePath);

                    // 下载完成
                    listener.onDownloadSuccess();
                } catch (Exception e) {
                    listener.onDownloadFailed();
                    LTrace.e(e);
                    e.printStackTrace();
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        LTrace.e(e);
                    }
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        LTrace.e(e);
                    }
                }
            }
        });
    }

    private boolean renameFile(String willRenameFilePath, String newNameFilePath) {
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
