package com.autils.framework.common.utils;

import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipDescription;
import android.content.Context;

import com.autils.framework.Launcher;

/**
 * 剪切板使用工具类
 * 对应的类：
 * API 11之前： android.text.ClipboardManager
 * API 11之后： android.content.ClipboardManager
 */
public class ClipboardManager {

    /**
     * 过去剪切板内容
     * API 11之前： android.text.ClipboardManager
     * API 11之后： android.content.ClipboardManager
     */
    public static String getContent() {
        String content = "";
        if (android.os.Build.VERSION.SDK_INT > 11) {
            android.content.ClipboardManager cm = (android.content.ClipboardManager) Launcher.getInstance().getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
            if (cm.hasPrimaryClip()) {
                ClipDescription clipDescription = cm.getPrimaryClipDescription();
                if (clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    ClipData cd = cm.getPrimaryClip();
                    Item item = cd.getItemAt(0);
                    content = item.getText().toString().trim();
                    if (StringUtils.isNullOrEmpty(content)) {
                        content = "";
                    }
                }
            }
        } else {
            @SuppressWarnings("deprecation")
            android.text.ClipboardManager c = (android.text.ClipboardManager) Launcher.getInstance().getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
            content = c.getText().toString().trim();
            if (StringUtils.isNullOrEmpty(content)) {
                content = "";
            }
        }

        return content;
    }

    /**
     * 设置剪切板的内容
     *
     * @param content
     */
    public static void setContent(String content) {
        if (android.os.Build.VERSION.SDK_INT > 11) {
            android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) Launcher.getInstance().getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData cd = ClipData.newPlainText("common", content.trim());
            clipboardManager.setPrimaryClip(cd);
        } else {
            @SuppressWarnings("deprecation")
            android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) Launcher.getInstance().getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setText(content.trim());
        }
    }
}
