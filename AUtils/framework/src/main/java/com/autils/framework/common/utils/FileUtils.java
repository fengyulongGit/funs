package com.autils.framework.common.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.autils.framework.common.log.L;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

/**
 * Created by fengyulong on 2016/8/19.
 */
public class FileUtils {

    public static boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static boolean isExist(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            return new File(filePath).exists();
        }
        return false;
    }

    public static boolean copyFile(String src, String des) {
        try {
            return copyFile(new FileInputStream(src), new FileOutputStream(des));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean copyFileFromAssets(Context context, String src, String des) {
        try {
            return copyFile(context.getAssets().open(src), new FileOutputStream(des));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean copyFileToData(Context context, String src, String fileName) {
        try {
            return copyFile(new FileInputStream(src), context.openFileOutput(fileName, Activity.MODE_WORLD_READABLE));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean copyFileFromResources(Context context, int resourcesId, String des) {
        try {
            return copyFile(context.getResources().openRawResource(resourcesId), new FileOutputStream(des));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean copyFileFromData(Context context, String fileName, String des) {
        try {
            return copyFile(context.openFileInput(fileName), new FileOutputStream(des));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean copyFile(InputStream is, FileOutputStream os) throws Exception {
        byte[] bytes = new byte[1024];

        while (is.read(bytes) > 0) {
            os.write(bytes);
        }
        os.close();
        is.close();
        return true;
    }

    public static boolean renameFile(String willRenameFilePath, String newNameFilePath) {
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

    public static void makeDirs(String dir) {
        File dirs = new File(dir);
        if (!dirs.exists()) {
            dirs.mkdirs();
        }
    }

    public static String readFileByAssets(Context context, String fileName) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);

            byte[] bb = new byte[inputStream.available()];
            inputStream.read(bb);
            inputStream.close();

            return new String(bb);
        } catch (Exception e) {
            L.e(e);
        }
        return "";
    }

    public static byte[] readFileByteArray(String filePath) {
        FileInputStream fis = null;
        byte[] bb = null;
        try {
            fis = new FileInputStream(filePath);
            bb = new byte[fis.available()];
            fis.read(bb);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fis)
                    fis.close();
            } catch (Exception e2) {
            }
        }

        return bb;
    }

    public static String readFile(String filePath) {
        String content = "";
        try {
            content = new String(readFileByteArray(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void write(String content, String path) {

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            fos.write(content.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public static void writeByAppend(String content, String path) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true)));
            out.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteFile(String filePath) {
        try {
            File f = new File(filePath);
            if (f.exists() && f.isFile()) {
                f.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deletFolder(File file) {
        if (file != null) {
            if (file.exists() && file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File f : files) {
                        deletFolder(f);
                        f.delete();
                    }
                }
            } else {
                file.delete();
            }
        }
    }

    /***
     * 获取缓存大小
     */
    public static String getFileSize(String filepath) {
        File file = new File(filepath);
        String size = formatFileSize(getFileSize(file));
        return size;
    }

    /***
     * 获取缓存大小
     */
    public static long getFileSize(File file) {
        long size = 0;

        if (file == null) {
            return size;
        }

        File filest[] = file.listFiles();
        if (filest == null) {
            return size;
        }
        for (int i = 0; i < filest.length; i++) {
            if (filest[i].isDirectory()) {
                size = size + getFileSize(filest[i]);
            } else {
                size = size + filest[i].length();
            }
        }
        return size;

    }

    /***
     * 转换文件大小
     */
    public static String formatFileSize(long files) {
        DecimalFormat df = new DecimalFormat("##0.00");
        String fileSizeString = "";
        if (files == 0.00) {
            fileSizeString = "0M";
        } else if (files < 1048576) {
            fileSizeString = df.format((double) files / 1024) + "KB";
        } else if (files < 1073741824) {
            fileSizeString = df.format((double) files / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) files / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    public static String getSuffix(String name) {
        if ((name != null) && (name.lastIndexOf(".") > 0)) {
            return name.substring(name.lastIndexOf(".") + 1);
        }
        return "";
    }

    public static String getFolder(String name) {
        if (name != null) {
            if ((name.lastIndexOf("/") > 0) && (name.lastIndexOf("\\") > 0)) {
                if (name.lastIndexOf("\\") > name.lastIndexOf("/")) {
                    return name.substring(0, name.lastIndexOf("\\") + 1);
                }
                return name.substring(0, name.lastIndexOf("/") + 1);
            }

            if (name.lastIndexOf("/") > 0)
                return name.substring(0, name.lastIndexOf("/") + 1);
            if (name.lastIndexOf("\\") > 0) {
                return name.substring(0, name.lastIndexOf("\\") + 1);
            }
        }

        return "";
    }

    public static String getShortNameNoSuffix(String name) {
        if (name == null) {
            return "";
        }
        return name.substring(getFolder(name).length(), name.length() - getSuffix(name).length() - 1);
    }

    public static String getShortName(String name) {
        if (name == null) {
            return "";
        }
        return name.substring(getFolder(name).length());
    }

    public static byte[] file2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static void byte2File(byte[] buf, String filePath) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            file = new File(filePath);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
