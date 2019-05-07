package com.autils.framework.common.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengyulong on 2018/11/1.
 */
public class GalleryUtils {

    public static void insert(Context context, File file) {
        Uri uri = Uri.fromFile(file);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
    }

    public static List<Image> getImages(Context context) {
        List<Image> images = new ArrayList<>();

        String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media._ID};

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_PROJECTION,
                IMAGE_PROJECTION[4] + ">0 AND " + IMAGE_PROJECTION[3] + "=? OR " + IMAGE_PROJECTION[3] + "=? ",
                new String[]{"image/jpeg", "image/png"},
                IMAGE_PROJECTION[2] + " DESC"
        );
        if (cursor == null || cursor.getCount() == 0) {
            return images;
        }

        cursor.moveToFirst();
        do {
            String path = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
            long dateTime = cursor.getLong(cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
            if (!FileUtils.isExist(path)) {
                continue;
            }
            Image image = null;
            if (!TextUtils.isEmpty(name)) {
                image = new Image(path, name, dateTime);
                images.add(image);
            }
        } while (cursor.moveToNext());

        cursor.close();
        return images;
    }

    public static class Image {
        public String path;
        public String name;
        public long time;

        public Image(String path, String name, long time) {
            this.path = path;
            this.name = name;
            this.time = time;
        }

        @Override
        public boolean equals(Object o) {
            try {
                Image other = (Image) o;
                return TextUtils.equals(this.path, other.path);
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
            return super.equals(o);
        }
    }
}
