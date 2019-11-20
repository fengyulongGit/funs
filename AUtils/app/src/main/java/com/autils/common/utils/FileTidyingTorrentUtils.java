package com.autils.common.utils;

import java.io.File;

/**
 * Created by fengyulong on 2019/4/12.
 */
public class FileTidyingTorrentUtils {

    private static String[] getFileInfo(String fileName) {
        int index = fileName.lastIndexOf(".");
        String toPrefix = "";
        String toSuffix = "";
        if (index == -1) {
            toPrefix = fileName;
        } else {
            toPrefix = fileName.substring(0, index);
            toSuffix = fileName.substring(index, fileName.length());
        }
        return new String[]{toPrefix, toSuffix};
    }

    private static void delete(String path) {
        File file = new File(path);
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File f : files) {
                        if (f.isDirectory()) {
                            delete(f.getAbsolutePath());
                        } else {
                            f.delete();
                        }
                    }
                }
                file.delete();
            } else {
                file.delete();
            }
        }
    }

    private static void moveFile(String path, String descDirectory) {
        for (File file : new File(path).listFiles()) {
            if (file.isFile()) {
                String name = file.getName();
                if (!name.endsWith("torrent")) {
                    continue;
                }

                String[] fileInfo = getFileInfo(name);
                String toPrefix = fileInfo[0];
                String toSuffix = fileInfo[1];

                File newFile = new File(descDirectory + "/" + toPrefix + toSuffix);
                for (int i = 1; newFile.exists() && i < Integer.MAX_VALUE; i++) {
                    newFile = new File(descDirectory + "/" + toPrefix + '(' + i + ')' + toSuffix);
                }

                if (!file.renameTo(newFile)) {
                    System.out.println(file.getAbsolutePath());
                }

            } else if (file.isDirectory()) {
                moveFile(file.getAbsolutePath(), descDirectory);
            } else {
                System.out.println(file.getAbsolutePath());
            }
        }
    }

    public static void tidying(File file) {
        if (!file.isDirectory()) {
            return;
        }
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                moveFile(f.getAbsolutePath(), file.getAbsolutePath());
                delete(f.getAbsolutePath());
            }
        }
    }

    public static void main(String[] args) {
        tidying(new File("/Users/island/00000"));
    }
}
