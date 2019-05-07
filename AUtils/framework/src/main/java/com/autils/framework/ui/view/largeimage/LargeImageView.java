package com.autils.framework.ui.view.largeimage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by fengyulong on 2018/11/6.
 */
public class LargeImageView extends SurfaceView {

    //region SurfaceHolder.Callback constructors
    public LargeImageView(Context context) {
        super(context);
        init(context);
    }

    public LargeImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public LargeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
    }
    //endregion


}
