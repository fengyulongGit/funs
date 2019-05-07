package com.autils.framework.ui.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.autils.framework.R;
import com.autils.framework.common.utils.WebpUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by fengyulong on 2018/5/12.
 */
@SuppressLint("AppCompatCustomView")
public class WebpImageView extends ImageView {
    private boolean NATIVE_WEB_P_SUPPORT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;

    public WebpImageView(Context context) {
        super(context);
        init(context, null);
    }

    public WebpImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WebpImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.webp);

        int webpSourceResourceID = a.getResourceId(R.styleable.webp_webp_src, 0);
        a.recycle();

        InputStream inputStream = getResources().openRawResource(webpSourceResourceID);
        byte[] bytes = streamToBytes(inputStream);

        Bitmap bitmap = null;
        if (!NATIVE_WEB_P_SUPPORT) {
            bitmap = WebpUtils.webpToBitmap(bytes);
        } else {
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }

        // Prefer the BitmapFactory decoder on post JB_MR2 OSs
        setImageBitmap(bitmap);
    }

    @SuppressLint("ResourceType")
    @Override
    public void setBackgroundResource(int resid) {
        InputStream inputStream = getResources().openRawResource(resid);
        byte[] bytes = streamToBytes(inputStream);

        Bitmap bitmap = null;
        if (!NATIVE_WEB_P_SUPPORT) {
            bitmap = WebpUtils.webpToBitmap(bytes);
        } else {
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        setImageBitmap(bitmap);
    }

    private byte[] streamToBytes(InputStream inputStream) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        try {
            while (inputStream.read(bytes) != -1) {
                baos.write(bytes);
                bytes = new byte[1024];
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
