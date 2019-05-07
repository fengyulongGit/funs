package com.autils.framework.common.utils;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.autils.framework.common.log.L;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengyulong on 2018/11/7.
 */
public class TypefaceUtils {

    private static final Map<String, Typeface> sCachedFonts = new HashMap<>();

    public static Typeface load(final AssetManager assetManager, final String filePath) {
        synchronized (sCachedFonts) {
            try {
                if (!sCachedFonts.containsKey(filePath)) {
                    final Typeface typeface = Typeface.createFromAsset(assetManager, filePath);
                    sCachedFonts.put(filePath, typeface);
                    return typeface;
                }
            } catch (Exception e) {
                L.e(e);
                sCachedFonts.put(filePath, null);
                return null;
            }
            return sCachedFonts.get(filePath);
        }
    }

    public static Typeface load(final String filePath) {
        synchronized (sCachedFonts) {
            try {
                if (!sCachedFonts.containsKey(filePath)) {
                    final Typeface typeface = Typeface.createFromFile(filePath);
                    sCachedFonts.put(filePath, typeface);
                    return typeface;
                }
            } catch (Exception e) {
                L.e(e);
                sCachedFonts.put(filePath, null);
                return null;
            }
            return sCachedFonts.get(filePath);
        }
    }

    /**
     * Is the passed in typeface one of ours?
     *
     * @param typeface nullable, the typeface to check if ours.
     * @return true if we have loaded it false otherwise.
     */
    public static boolean isLoaded(Typeface typeface) {
        return typeface != null && sCachedFonts.containsValue(typeface);
    }

    private TypefaceUtils() {
    }
}
