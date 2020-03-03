package com.autils.common.utils;

import java.io.File;

/**
 * Created by fengyulong on 2019/4/12.
 */
public class FileTidying500Utils {

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

    private static void moveFile(File file, String descDirectory) {
        if (file.isFile()) {
            String name = file.getName();
            if (name.endsWith("js")
                    || name.endsWith("torrent")
                    || name.endsWith("DS_Store")
            ) {
                return;
            }

            String[] fileInfo = getFileInfo(name);
            String toPrefix = fileInfo[0];
            String toSuffix = fileInfo[1];

            new File(descDirectory).mkdirs();
            File newFile = new File(descDirectory + "/" + toPrefix + toSuffix);
            for (int i = 1; newFile.exists() && i < Integer.MAX_VALUE; i++) {
                newFile = new File(descDirectory + "/" + toPrefix + '(' + i + ')' + toSuffix);
            }

            if (!file.renameTo(newFile)) {
                System.out.println(file.getAbsolutePath());
            }

        } else if (file.isDirectory()) {
            tidying(file);
        } else {
            System.out.println(file.getAbsolutePath());
        }
    }

    public static void tidying(File file) {
        if (!file.isDirectory()) {
            return;
        }
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            moveFile(files[i], "/Users/island/长大" + (i / 500));
        }
    }

    public static void main(String[] args) {
        tidying(new File("/Users/island/picture/长大"));
    }
}
