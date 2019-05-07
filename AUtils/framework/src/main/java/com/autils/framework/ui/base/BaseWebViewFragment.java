package com.autils.framework.ui.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.autils.api.ServerClient;
import com.autils.framework.R;
import com.autils.framework.common.log.L;
import com.autils.framework.common.utils.StringUtils;
import com.autils.framework.common.utils.UIUtils;
import com.autils.framework.ui.fragment.NewWindowWebFragment;
import com.autils.framework.ui.view.fragmentation.SupportFragment;
import com.autils.framework.ui.view.imageselector.CameraTaker;
import com.autils.framework.ui.view.imageselector.MultiImageSelector;
import com.autils.framework.ui.view.webview.WebInterface;
import com.autils.framework.ui.view.webview.WebSchemeFilter;
import com.autils.framework.ui.view.webview.X5WebView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.functions.Consumer;

/**
 * Created by fengyulong on 2018/5/12.
 */
public abstract class BaseWebViewFragment extends BaseFragment {
    private final int TIMER_BEGIN = 0;
    private final int TIMER_END = 1;

    private boolean loadFinish = false;
    private CountDownTimer timer = null;

    private String firstUrl;

    private List<WebSchemeFilter> schemeFilters = new ArrayList<>();
    private List<IWebListener> webListeners = new ArrayList<>();

    protected ProgressBar webview_progress;

    protected X5WebView web_view;

    public ValueCallback<Uri> uploadMessage;
    public ValueCallback<Uri[]> filePathCallback;

    private WebInterface webInterface;

    @Override
    protected int layoutContentResID() {
        return R.layout.layout_webview;
    }

    public abstract WebInterface getWebInterface();

    public String webInterfaceName() {
        return "loanOffline";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initWebViews();

        webInterface = getWebInterface();
        if (webInterface == null) {
            webInterface = new WebInterface() {
                @Override
                public void onDestroyView() {
                }
            };
        }
        webInterface.setFragment(this);

        addJsFunction(webInterface, webInterfaceName());

        setIWebListener(new IWebListener() {
            @Override
            public void onWebLoad() {
            }

            @Override
            public void onReceivedTitle(String title) {
                if (StringUtils.isNotNullOrEmpty(title)) {
                    title = title.split("-")[0];
                }
                setTitleText(title);
            }
        });
    }

    @Override
    protected void initViewBindClick() {
        webview_progress = $(R.id.webview_progress);

        web_view = $(R.id.web_view);
    }

    @Override
    public void left() {
        if (canGoBack()) {
            return;
        }

        super.left();
    }

    @Override
    public boolean onBackPressedSupport() {
        if (canGoBack()) {
            return true;
        }
        return super.onBackPressedSupport();
    }

    public boolean canGoBack() {
        if (web_view.canGoBack()) {
            web_view.goBack();
            setCloseBtnText("关闭");
            setCloseBtnVisibility(true);

            return true;
        }
        return false;
    }

    @Override
    public boolean isCloseBtnVisibility() {
        return true;
    }

    @Override
    public void onDestroyView() {
        if (null != timer)
            timer.cancel();

        if (web_view != null) {
            web_view.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            web_view.clearHistory();

            ((ViewGroup) web_view.getParent()).removeView(web_view);
            web_view.destroy();
            web_view = null;
        }

        if (webInterface != null) {
            webInterface.onDestroyView();
        }

        super.onDestroyView();
    }

    private void initWebViews() {
        web_view.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                L.d("shouldOverrideUrlLoading: " + url);

                if (schemeFilters != null && !schemeFilters.isEmpty()) {
                    for (WebSchemeFilter schemeFilter : schemeFilters) {
                        if (schemeFilter.action(url)) {
                            if (web_view.getUrl().startsWith("taobao://")) {
                                onBackPressedSupport();
                            }
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showLoadView();
                showWebView();
                handler.sendEmptyMessage(TIMER_BEGIN);
                L.i("onPageStarted url " + url);
                if (null != webListeners) {
                    for (IWebListener webListener : webListeners) {
                        webListener.onWebLoad();
                    }
                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadFinish = true;
                if (null != webListeners) {
                    for (IWebListener webListener : webListeners) {
                        webListener.onReceivedTitle(view.getTitle());
                    }
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
//                view.setVisibility(View.GONE);
//                showNetWorkError(!NetworkMonitor.isConnected() ? ILoadView.TYPE_NETWORK : ILoadView.TYPE_SERVICE, "");
            }
        });

        web_view.setWebChromeClient(new WebChromeClient() {
            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMessage, String acceptType) {
                fileCHooser(uploadMessage, null, false);
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMessage) {
                fileCHooser(uploadMessage, null, false);
            }

            // For Android  > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMessage, String acceptType, String capture) {
                fileCHooser(uploadMessage, null, false);
            }

            // For Android  >= 5.0
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             WebChromeClient.FileChooserParams fileChooserParams) {
                fileCHooser(null, filePathCallback, fileChooserParams.isCaptureEnabled());
                return true;
            }

            private void fileCHooser(ValueCallback<Uri> uploadMessage, ValueCallback<Uri[]> filePathCallback, boolean isCamera) {
                BaseWebViewFragment.this.uploadMessage = uploadMessage;
                BaseWebViewFragment.this.filePathCallback = filePathCallback;
                selectImage(isCamera);
            }

            @Override
            public void onReceivedTitle(WebView webView, String title) {
                super.onReceivedTitle(webView, title);

                if (null != webListeners) {
                    for (IWebListener webListener : webListeners) {
                        webListener.onReceivedTitle(title);
                    }
                }
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                NewWindowWebFragment newWindowWebFragment = NewWindowWebFragment.newInstance();
                newWindowWebFragment.setWebInterface(webInterface.clone());
                newWindowWebFragment.setMessage(resultMsg);
                start(newWindowWebFragment);
                return true;
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String s, GeolocationPermissionsCallback geolocationPermissionsCallback) {
                geolocationPermissionsCallback.invoke(s, true, true);
                super.onGeolocationPermissionsShowPrompt(s, geolocationPermissionsCallback);
            }
        });

        WebSchemeFilter phoneCallWebSchemeFilter = new WebSchemeFilter("mailto:", "geo:", "tel:", "taobao:") {
            @Override
            public boolean action(String url) {
                if (filter(url)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                } else {
                    return false;
                }
            }
        };
        schemeFilters.add(phoneCallWebSchemeFilter);

        WebSchemeFilter newtabWebSchemeFilter = new WebSchemeFilter("newtab://") {
            @Override
            public boolean action(String url) {
                if (filter(url)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(deleteAction(url)));
                    startActivity(intent);
                    return true;
                } else {
                    return false;
                }
            }
        };
        schemeFilters.add(newtabWebSchemeFilter);

        WebSchemeFilter pdfWebSchemeFilter = new WebSchemeFilter(".pdf") {
            @Override
            public boolean action(String url) {
                if (contains(url)) {
//                    PDFReaderUtils.loadPDF(url, context);
//                    if (!web_view.canGoBack()) {
//                        BaseWebViewFragment.super.left();
//                    }
                }
                return false;
            }
        };
        schemeFilters.add(pdfWebSchemeFilter);
    }

    @SuppressLint("JavascriptInterface")
    public void addJsFunction(Object any, String name) {
        if (null != web_view) {
            web_view.addJavascriptInterface(any, name);
        }
    }

    public void setIWebListener(IWebListener webListener) {
        webListeners.add(webListener);
    }

    public String getFirstUrl() {
        return firstUrl;
    }

    public void loadUrl(String url) {
        if (StringUtils.isNullOrEmpty(firstUrl)) {
            firstUrl = url;
        }
        if (null != web_view) {
            if (StringUtils.isNotNullOrEmpty(url)
                    && !url.startsWith("http")
                    && !url.startsWith("javascript")) {
                url = "http://" + url;
            }
            web_view.loadUrl(url);
        }
    }

    public void loadFirstUrlWithToken() {
        loadUrlWithToken(firstUrl);
    }

    public void loadUrlWithToken(String url) {
        if (StringUtils.isNullOrEmpty(firstUrl)) {
            firstUrl = url;
        }

        if (!url.contains("?")) {
            url += "?";
        }
        if (!url.endsWith("?")) {
            url += "&";
        }

        TreeMap<String, String> paramsTreeMap = new TreeMap<>();
        paramsTreeMap.put("token", ServerClient.getInstance().getServerToken().getAccess_token());
        paramsTreeMap.put("device", ServerClient.getInstance().getServerConfig().getDevice());

        StringBuilder params = new StringBuilder();

        for (Map.Entry<String, String> entry : paramsTreeMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            params.append(key).append("=").append(value).append("&");
        }
        if (params.length() > 0) {
            params.setLength(params.length() - 1);
        }

        url += params.toString();

        params.setLength(0);


        loadUrl(url);
    }

    public boolean isFirstUrl() {
        return !web_view.canGoBack();
    }

    public void setCacheMode(int mode) {
        if (null != web_view) {
            web_view.getSettings().setCacheMode(mode);
        }
    }

    private void reloadData() {
        showLoadView();
        web_view.reload();
    }

    private void showLoadView() {
        showContentView();
        loadFinish = false;
        if (webview_progress != null) {
            webview_progress.setVisibility(View.VISIBLE);
        }
        if (web_view != null) {
            web_view.setVisibility(View.GONE);
        }
    }

    private void showWebView() {
        showContentView();
        if (web_view != null) {
            web_view.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TIMER_BEGIN: {
                    if (null != timer) {
                        timer.cancel();
                    }
                    initProgress();
                    initTimeCounter();
                }
                case TIMER_END: {
                    if (null != timer) {
                        timer.cancel();
                    }
                }
            }
        }
    };

    private void initTimeCounter() {
        timer = new CountDownTimer(1000 * 10, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (null != webview_progress) {
                    if (webview_progress.getProgress() >= 70) {
                        if (webview_progress.getProgress() <= 95) {
                            webview_progress.setProgress(webview_progress.getProgress() + 1);
                        }
                    } else {
                        webview_progress.setProgress(webview_progress.getProgress() + 4);
                    }
                    if (webview_progress.getProgress() >= 95 && loadFinish) {
                        webview_progress.setVisibility(View.GONE);
                        handler.sendEmptyMessage(TIMER_END);
                    }
                }
            }

            @Override
            public void onFinish() {
                //设置10秒的计时器。基本保证onFinish时 loadFinish = true;
                //if (loadFinish)
                if (null != webview_progress) {
                    webview_progress.setVisibility(View.GONE);
                }
            }
        };

        timer.start();
    }

    private void stopTimer() {
        if (null != timer) {
            timer.cancel();
        }
    }

    private void initProgress() {
        if (null != webview_progress) {
            webview_progress.setProgress(0);
        }
    }

    @Override
    public void retry() {
        reloadData();
    }

    @Override
    public String getTitleText() {
        return "";
    }

    @SuppressLint("CheckResult")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.d(requestCode + " resultCode " + resultCode);

        if (REQUEST_CODE_IMAGE == requestCode) {
            if (SupportFragment.RESULT_OK != resultCode) {
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(Uri.fromFile(null));
                }
                if (filePathCallback != null) {
                    filePathCallback.onReceiveValue(new Uri[]{});
                }
                return;
            }

            ArrayList<String> selectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
            if (selectPath == null || selectPath.isEmpty()) {
                return;
            }

            final String picPath = selectPath.get(0);
            File imgFile = new File(picPath);
            if (!imgFile.exists()) {
                return;
            }

            if (uploadMessage != null) {
                uploadMessage.onReceiveValue(Uri.fromFile(imgFile));
            }
            if (filePathCallback != null) {
                filePathCallback.onReceiveValue(new Uri[]{Uri.fromFile(imgFile)});
            }
        }
    }

    public interface IWebListener {
        void onWebLoad();

        void onReceivedTitle(String title);
    }

    private final int REQUEST_CODE_IMAGE = 1000;

    @SuppressLint("CheckResult")
    private void selectImage(final boolean isCamera) {
        new RxPermissions(getActivity()).request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        ).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (granted) {
                    if (isCamera) {
                        CameraTaker.create().start(BaseWebViewFragment.this, REQUEST_CODE_IMAGE);
                    } else {
                        MultiImageSelector.create()
                                .showCamera(true)
                                .single()
                                .start(BaseWebViewFragment.this, REQUEST_CODE_IMAGE);
                    }
                } else {
                    if (uploadMessage != null) {
                        uploadMessage.onReceiveValue(Uri.fromFile(null));
                    }
                    if (filePathCallback != null) {
                        filePathCallback.onReceiveValue(new Uri[]{});
                    }
                    UIUtils.showOpenPermissionDialog(context, getResources().getString(R.string.permission_storage) + "," + getResources().getString(R.string.permission_camera));
                }
            }
        });
    }
}
