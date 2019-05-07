package com.autils.framework.ui.view.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by fengyulong on 2018/10/12.
 */
public class ScaleImageView extends ImageView {
    private int initWidth;
    private int initHeight;

    public static final int MATCH_MODE_WIDTH = 1;
    public static final int MATCH_MODE_HEIGHT = 2;
    public static final int MATCH_MODE_BOTH = 3;
    private int matchMode = MATCH_MODE_WIDTH;

//    private int maxWidth;
//    private int maxHeight;

    public ScaleImageView(Context context) {
        this(context, null);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setInitSize(int initWidth, int initHeight) {
        this.initWidth = initWidth;
        this.initHeight = initHeight;
    }

    public void setMatchMode(int matchMode) {
        this.matchMode = matchMode;
    }

//    public void setMaxSize(int maxWidth, int maxHeight) {
//        this.maxWidth = maxWidth;
//        this.maxHeight = maxHeight;
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (initWidth > 0 && initHeight > 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);

            if (width > 0 && MATCH_MODE_WIDTH == matchMode) {
                float scale = (float) initHeight / (float) initWidth;
                height = (int) ((float) width * scale);
            } else if (height > 0 && MATCH_MODE_HEIGHT == matchMode) {
                float scale = (float) initWidth / (float) initHeight;
                width = (int) ((float) height * scale);
            } else if (MATCH_MODE_BOTH == matchMode) {
                float scale = (float) initHeight / (float) initWidth;
                int desHeight = (int) ((float) width * scale);
                if (desHeight < height) {
                    height = desHeight;
                } else {
                    scale = (float) initWidth / (float) initHeight;
                    int desWidth = (int) ((float) height * scale);
                    if (desWidth < width) {
                        width = desWidth;
                    }
                }
            }

            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        //        else if (MATCH_MODE_BOTH == matchMode && maxWidth > 0 && maxHeight > 0) {
//            int width = maxWidth;
//            int height = maxHeight;
//
//            float scale = (float) initHeight / (float) initWidth;
//            int desHeight = (int) ((float) width * scale);
//            if (desHeight < height) {
//                height = desHeight;
//            } else {
//                scale = (float) initWidth / (float) initHeight;
//                int desWidth = (int) ((float) height * scale);
//                if (desWidth < width) {
//                    width = desWidth;
//                }
//            }
//
//            setMeasuredDimension(width, height);
//        }
    }
}
