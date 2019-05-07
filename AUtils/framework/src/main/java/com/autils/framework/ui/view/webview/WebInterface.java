package com.autils.framework.ui.view.webview;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.autils.framework.ui.base.BaseWebViewFragment;
import com.autils.framework.ui.base.mvp.ILoadView;

/**
 * Created by fengyulong on 2018/12/7.
 */
public abstract class WebInterface implements ILoadView {
    protected BaseWebViewFragment fragment;
    protected Context context;

    public void setFragment(BaseWebViewFragment fragment) {
        if (fragment == null) {
            return;
        }
        this.fragment = fragment;
        this.context = fragment.getContext();
    }

    @JavascriptInterface
    public void close() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fragment.close();
            }
        });
    }

    public void runOnUiThread(Runnable action) {
        if (action == null) {
            return;
        }
        fragment.getActivity().runOnUiThread(action);
    }


    @Override
    public void showLoading(String content) {
        fragment.showLoading(content);
    }

    @Override
    public void showLoading() {
        fragment.showLoading();
    }

    @Override
    public void showLoading(int resourceId) {
        fragment.showLoading(resourceId);
    }

    @Override
    public void dismissLoading() {
        fragment.dismissLoading();
    }

    @Override
    public void error(Throwable e) {
        fragment.error(e);
    }

    @Override
    public void next() {
        fragment.next();
    }

    @Override
    public void showNetWorkError(int type, String message) {
        fragment.showNetWorkError(type, message);
    }

    @Override
    public void showContentView() {
        fragment.showContentView();
    }

    public abstract void onDestroyView();

    public WebInterface clone() {
        return new WebInterface() {
            @Override
            public void onDestroyView() {

            }
        };
    }
}
