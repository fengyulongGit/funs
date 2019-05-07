package com.autils.framework.ui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

import com.autils.framework.common.log.L;
import com.autils.framework.common.utils.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;

/**
 * Created by fengyulong on 2018/10/12.
 */
public class ImageLoader {
    public static void load(Context context, String url, ImageView iv) {
        if (StringUtils.isNullOrEmpty(url)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .crossFade()// 设置淡入淡出效果，默认300ms，可以传参
                .diskCacheStrategy(DiskCacheStrategy.ALL)//让Glide既缓存全尺寸图片，下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存
                .into(iv);
    }

    public static void load(Context context, File file, ImageView iv) {
        Glide.with(context)
                .load(file)
                .crossFade()// 设置淡入淡出效果，默认300ms，可以传参
                .diskCacheStrategy(DiskCacheStrategy.ALL)//让Glide既缓存全尺寸图片，下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存
                .into(iv);
    }

    public static void load(Context context, String url, ImageView iv, int placeholder) {
        if (StringUtils.isNullOrEmpty(url)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)//让Glide既缓存全尺寸图片，下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出，重新调整大小，然后缓存
                .placeholder(placeholder)
                .into(iv);
    }

    public static void load(Context context, int resId, ImageView iv) {
        Glide.with(context)
                .load(resId)
                .crossFade()
                .into(iv);
    }

    public static Bitmap load(Context context, String url) {
        if (StringUtils.isNullOrEmpty(url)) {
            return null;
        }
        try {
            return Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            L.e(e);
        }
        return null;
    }

    public static String cachePath(Context context, String url) {
        if (StringUtils.isNullOrEmpty(url)) {
            return "";
        }

        try {
            FutureTarget<File> futureTarget = Glide.with(context).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
            return futureTarget.get().getAbsolutePath();
        } catch (Exception e) {
            L.e(e);
        }
        return "";
    }

    public static void loadCircle(Context context, int resId, ImageView iv) {
        Glide.with(context)
                .load(resId)
                .crossFade()
                .skipMemoryCache(true)
                .transform(new CircleTransform(context))
                .into(iv);
    }

    public static void loadCircle(Context context, String url, ImageView iv) {
        if (StringUtils.isNullOrEmpty(url)) {
            return;
        }
        Glide.with(context)
                .load(url)
                .crossFade()
                .skipMemoryCache(true)
                .transform(new CircleTransform(context))
                .into(iv);
    }
}

class CircleTransform extends BitmapTransformation {

    public CircleTransform(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}