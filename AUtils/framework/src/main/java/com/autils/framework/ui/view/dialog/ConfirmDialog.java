package com.autils.framework.ui.view.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.autils.api.utils.StringUtils;

/**
 * Created by fengyulong on 2018/5/16.
 */
public class ConfirmDialog {
    private AlertDialog dialog;
    private Builder builder;

    private ConfirmDialog(Builder builder) {
        this.builder = builder;

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(builder.context)
                .setTitle(builder.title)
                .setMessage(builder.message);
        if (StringUtils.isNotNullOrEmpty(builder.negativeButtonText)) {
            dialogBuilder.setNegativeButton(builder.negativeButtonText, builder.negativeButtonOnClickListener);
        }

        if (StringUtils.isNotNullOrEmpty(builder.positiveButtonText)) {
            dialogBuilder.setPositiveButton(builder.positiveButtonText, builder.positiveButtonOnClickListener);
        }


        if (StringUtils.isNotNullOrEmpty(builder.neutralButtonText)) {
            dialogBuilder.setNeutralButton(builder.neutralButtonText, builder.neutralButtonOnClickListener);
        }

        if (builder.onDismissListener != null) {
            dialogBuilder.setOnDismissListener(builder.onDismissListener);
        }

        dialog = dialogBuilder.create();

        dialog.setCanceledOnTouchOutside(builder.isCanceledOnTouchOutside);

        dialog.setCancelable(builder.isCancelable);

        if (builder.onKeyListener != null) {
            dialog.setOnKeyListener(builder.onKeyListener);
        }
    }

    public void show() {
        dialog.show();

        if (StringUtils.isNotNullOrEmpty(builder.negativeButtonText) && builder.negativeButtonTextColor != 0) {
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(builder.negativeButtonTextColor);
        }

        if (StringUtils.isNotNullOrEmpty(builder.positiveButtonText) && builder.positiveButtonTextColor != 0) {
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(builder.positiveButtonTextColor);
        }

        if (StringUtils.isNotNullOrEmpty(builder.neutralButtonText) && builder.neutralButtonTextColor != 0) {
            dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(builder.neutralButtonTextColor);
        }
    }


    public static class Builder {
        private Context context;
        private String title;
        private String message;

        private String negativeButtonText;
        private DialogInterface.OnClickListener negativeButtonOnClickListener;
        private int negativeButtonTextColor;

        private String positiveButtonText;
        private DialogInterface.OnClickListener positiveButtonOnClickListener;
        private int positiveButtonTextColor;

        private String neutralButtonText;
        private DialogInterface.OnClickListener neutralButtonOnClickListener;
        private int neutralButtonTextColor;

        private boolean isCanceledOnTouchOutside = true;

        private boolean isCancelable = true;

        private DialogInterface.OnKeyListener onKeyListener;

        private DialogInterface.OnDismissListener onDismissListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setNegativeButton(String text, DialogInterface.OnClickListener listener) {
            this.negativeButtonText = text;
            this.negativeButtonOnClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String text, DialogInterface.OnClickListener listener, int color) {
            this.negativeButtonText = text;
            this.negativeButtonOnClickListener = listener;
            this.negativeButtonTextColor = color;
            return this;
        }

        public Builder setPositiveButton(String text, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = text;
            this.positiveButtonOnClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String text, DialogInterface.OnClickListener listener, int color) {
            this.positiveButtonText = text;
            this.positiveButtonOnClickListener = listener;
            this.positiveButtonTextColor = color;
            return this;
        }

        public Builder setNeutralButton(String text, DialogInterface.OnClickListener listener) {
            this.neutralButtonText = text;
            this.neutralButtonOnClickListener = listener;
            return this;
        }

        public Builder setNeutralButton(String text, DialogInterface.OnClickListener listener, int color) {
            this.neutralButtonText = text;
            this.neutralButtonOnClickListener = listener;
            this.neutralButtonTextColor = color;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            isCanceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            isCancelable = cancelable;
            return this;
        }

        public Builder setOnKeyListener(DialogInterface.OnKeyListener listener) {
            this.onKeyListener = listener;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener listener) {
            this.onDismissListener = listener;
            return this;
        }

        public ConfirmDialog create() {
            return new ConfirmDialog(this);
        }
    }
}
