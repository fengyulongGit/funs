package com.autils.framework.common.utils;

import android.os.Environment;

import com.autils.api.utils.StringUtils;

import java.io.File;

/**
 * Created by fengyulong on 2018/5/16.
 */
public class PathUtils {
    private static String SD_CARD;

    public static String SdCard() {
        if (StringUtils.isNullOrEmpty(SD_CARD)) {
            SD_CARD = Environment.getExternalStorageDirectory().toString();
        }
        return SD_CARD;
    }

    public static void setSdCard(String sdCard) {
        SD_CARD = sdCard;
    }

    public static String ROOT() {
        return returnFilePath(SdCard() + "/autils/", "");
    }

    private static String APK() {
        return ROOT() + "apk/";
    }

    public static String APKFilePath(String name) {
        return returnFilePath(APK(), name);
    }

    private static String Gallery() {
        return returnFilePath(SdCard() + "/Pictures/autils", "");
    }

    public static String GalleryFilePath(String name) {
        return returnFilePath(Gallery(), name);
    }

    private static String Cache() {
        return ROOT() + "Cache/";
    }

    public static String CacheFilePath(String name) {
        return returnFilePath(Cache(), name);
    }

    private static String returnFilePath(String fileDir, String fileName) {
        try {
            File fontsDir = new File(fileDir);
            if (!fontsDir.exists()) {
                fontsDir.mkdirs();
            }
            return fileDir + fileName;
        } catch (Exception e) {
            return "";
        }
    }
}
