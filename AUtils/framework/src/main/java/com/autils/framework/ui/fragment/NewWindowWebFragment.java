package com.autils.framework.ui.fragment;

import android.os.Bundle;
import android.os.Message;

import com.autils.framework.ui.base.BaseWebViewFragment;
import com.autils.framework.ui.view.webview.WebInterface;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by fengyulong on 2018/5/16.
 */
public class NewWindowWebFragment extends BaseWebViewFragment {

    private WebInterface webInterface;

    @Override
    public WebInterface getWebInterface() {
        return webInterface;
    }

    public void setWebInterface(WebInterface webInterface) {
        this.webInterface = webInterface;
    }

    private Message message;

//    @Override
//    protected int layoutTitleResID() {
//        return R.layout.layout_title_fix_status_bar;
//    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        WebView.WebViewTransport transport = (WebView.WebViewTransport) message.obj;
        transport.setWebView(web_view);
        message.sendToTarget();
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public boolean isRoot() {
        return false;
    }

    @Override
    public String getTitleText() {
        return "极速借";
    }

    public static NewWindowWebFragment newInstance() {
        Bundle bundle = new Bundle();

        NewWindowWebFragment fragment = new NewWindowWebFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
