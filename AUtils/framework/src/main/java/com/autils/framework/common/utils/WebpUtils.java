package com.autils.framework.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.google.webp.libwebp;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by fengyulong on 2018/5/12.
 */
public class WebpUtils {
    static {
        System.loadLibrary("webp");
    }

    private static int NOT_INITIALIZED = -1;
    private static int SUPPORTED = 1;
    private static int NOT_SUPPORTED = 0;

    //why not boolean? we need more states for result caching
    private static int isWebPSupportedCache = NOT_INITIALIZED;

    public static void getDrawableFromWebP(Context context, int resourceId, View view) {
        InputStream rawImageStream = context.getResources().openRawResource(resourceId);
        byte[] bitMapData = streamToBytes(rawImageStream);
        Bitmap webpBitmap = webpToBitmap(bitMapData);
        view.setBackgroundDrawable(new BitmapDrawable(webpBitmap));
    }

    public static Bitmap webpToBitmap(byte[] encoded) {

        int[] width = new int[]{0};
        int[] height = new int[]{0};
        byte[] decoded = libwebp.WebPDecodeARGB(encoded, encoded != null ? encoded.length : 0, width, height);

        int[] pixels = new int[decoded.length / 4];
        ByteBuffer.wrap(decoded).asIntBuffer().get(pixels);

        return Bitmap.createBitmap(pixels, width[0], height[0], Bitmap.Config.ARGB_8888);
    }

    public static byte[] streamToBytes(InputStream inputStream) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];

        try {
            while (inputStream.read(buffer) >= 0) {
                out.write(buffer);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return out.toByteArray();
    }

    public static boolean isWebPSupported() {
        // did we already try to check?
        if (isWebPSupportedCache == NOT_INITIALIZED) {
            //no - trying to decode
            //webp 1x1 transparent pixel with lossless
            byte[] webp1x1 = new byte[]{0x52, 0x49, 0x46, 0x46, 0x1A, 0x00, 0x00, 0x00, 0x57, 0x45, 0x42, 0x50, 0x56, 0x50, 0x38, 0x4C, 0x0D, 0x00, 0x00, 0x00, 0x2F, 0x00, 0x00, 0x00, 0x10, 0x07, 0x10, 0x11, 0x11, (byte) 0x88, (byte) 0x88, (byte) 0xFE, 0x07, 0x00};
            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(webp1x1, 0, webp1x1.length);
                if (bitmap != null) {
                    //webp supported
                    isWebPSupportedCache = SUPPORTED;
                    //don't forget to recycle!
                    bitmap.recycle();
                } else {
                    //bitmap is null = not supported
                    isWebPSupportedCache = NOT_SUPPORTED;
                }
            } catch (Exception e) {
                //we got some exception = not supported
                isWebPSupportedCache = NOT_SUPPORTED;
                e.printStackTrace();
            }

        }
        return isWebPSupportedCache == SUPPORTED;
    }
}
