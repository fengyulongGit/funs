package com.autils.framework.ui.view.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.autils.api.ServerClient;
import com.autils.framework.common.log.L;
import com.autils.framework.common.utils.AppUtils;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import java.util.Map;

/**
 * Created by fengyulong on 2018/11/29.
 */
public class X5WebView extends WebView {

    public X5WebView(Context context) {
        this(context, null);
    }

    public X5WebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, false);
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i, boolean b) {
        this(context, attributeSet, i, null, b);
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean b) {
        super(context, attributeSet, i, map, b);
        initWebViews();
    }

    private void initWebViews() {
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
//        settings.setDisplayZoomControls(false);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setUseWideViewPort(true);
        settings.setSupportMultipleWindows(true);//多窗口

        settings.setLoadWithOverviewMode(true);
        // init webview settings
        settings.setAllowContentAccess(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setSavePassword(false);
        settings.setSaveFormData(false);
        settings.setUseWideViewPort(true);
        settings.setGeolocationEnabled(true);
        settings.setAppCacheMaxSize(Long.MAX_VALUE);
        settings.setPluginState(WebSettings.PluginState.ON_DEMAND);

        settings.setGeolocationEnabled(true);
        String dir = this.getContext().getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setGeolocationDatabasePath(dir);

        settings.setDomStorageEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(0);//WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        String userAgent = settings.getUserAgentString();
        userAgent += " " + ServerClient.getInstance().getServerConfig().getAppname() + "_android_" + AppUtils.getVersionName(getContext());
        settings.setUserAgentString(userAgent);
        L.i("userAgent " + userAgent);


        // settings.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        // settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        addJavascriptInterface(null, "");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            dealJavascriptLeak();
        }
        setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
        IX5WebViewExtension ix5 = getX5WebViewExtension();
        if (ix5 != null) {
            ix5.setScrollBarFadingEnabled(false);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void dealJavascriptLeak() {
        removeJavascriptInterface("searchBoxJavaBridge_");
        removeJavascriptInterface("accessibility");
        removeJavascriptInterface("accessibilityTraversal");
    }

//    @Override
//    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
//        boolean ret = super.drawChild(canvas, child, drawingTime);
//        canvas.save();
//        Paint paint = new Paint();
//        paint.setColor(0x7fff0000);
//        paint.setTextSize(24.f);
//        paint.setAntiAlias(true);
//        if (getX5WebViewExtension() != null) {
//            canvas.drawText(this.getContext().getPackageName() + "-pid:"
//                    + android.os.Process.myPid(), 10, 50, paint);
//            canvas.drawText(
//                    "X5  Core:" + QbSdk.getTbsVersion(this.getContext()), 10,
//                    100, paint);
//        } else {
//            canvas.drawText(this.getContext().getPackageName() + "-pid:"
//                    + android.os.Process.myPid(), 10, 50, paint);
//            canvas.drawText("Sys Core", 10, 100, paint);
//        }
//        canvas.drawText(Build.MANUFACTURER, 10, 150, paint);
//        canvas.drawText(Build.MODEL, 10, 200, paint);
//        canvas.restore();
//        return ret;
//    }
}
