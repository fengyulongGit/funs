package com.autils.framework.common.utils;

import android.graphics.Color;

/**
 * Created by fengyulong on 2018/11/2.
 */
public class ColorUtils {

    public static int parseColor(String colorString) {
        if (colorString.charAt(0) != '#') {
            while (colorString.length() < 6) {
                colorString = "0" + colorString;
            }
            colorString = "#" + colorString;
        }
        return Color.parseColor(colorString);
    }

    public static int parseColor(long color) {
        return parseColor(Long.toHexString(color));
    }

    public static int parseColor(int color) {
        return parseColor(Integer.toHexString(color));
    }
}
