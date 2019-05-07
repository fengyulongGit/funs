package com.autils.framework.ui.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;

/**
 * Created by fengyulong on 2018/5/12.
 */
public class BadgeDrawerArrowDrawable extends DrawerArrowDrawable {
    public BadgeDrawerArrowDrawable(Context context) {
        super(context);

        init();
    }

    private final float SIZE_FACTOR = 0.1F;
    private final float HALF_SIZE_FACTOR = 0.05F;

    private Paint backgroundPaint = new Paint();
    private Paint textPaint;
    private String text;
    private boolean isEnabled;

    private void init() {
        backgroundPaint.setColor(Color.RED);
        backgroundPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(SIZE_FACTOR * getIntrinsicHeight());
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (!isEnabled) {
            return;
        }

        Rect bounds = getBounds();
        float x = (1 - HALF_SIZE_FACTOR) * bounds.width();
        float y = HALF_SIZE_FACTOR * bounds.height();
        canvas.drawCircle(x, y, SIZE_FACTOR * bounds.width(), backgroundPaint);

        if (this.text == null || this.text.length() == 0) {
            return;
        }

        Rect textBounds = new Rect();
        textPaint.getTextBounds(this.text, 0, this.text.length(), textBounds);
        canvas.drawText(this.text, x, y + textBounds.height() / 2, textPaint);
    }


    public void setText(String text) {
        if (this.text == null ? text != null : !this.text.equals(text)) {
            this.text = text;
            invalidateSelf();
        }
    }

    public void setEnabled(boolean enabled) {
        if (this.isEnabled != enabled) {
            this.isEnabled = enabled;
            invalidateSelf();
        }
    }

    public int getBackgroundColor() {
        return backgroundPaint.getColor();
    }

    public void setBackgroundColor(int color) {
        if (backgroundPaint.getColor() != color) {
            backgroundPaint.setColor(color);
            invalidateSelf();
        }
    }

    public int getTextColor() {
        return textPaint.getColor();
    }

    public void setTextColor(int color) {
        if (textPaint.getColor() != color) {
            textPaint.setColor(color);
            invalidateSelf();
        }
    }
}
