package com.autils.framework.ui.base.mvp;

import android.support.annotation.UiThread;

/**
 * Created by fengyulong on 2018/5/11.
 */
public interface ILoadView extends IView {

    int TYPE_NETWORK = 1;
    int TYPE_SERVICE = 2;

    @UiThread
    void showLoading(String content);

    @UiThread
    void showLoading();

    @UiThread
    void showLoading(int resourceId);

    @UiThread
    void dismissLoading();

    @UiThread
    void error(Throwable e);

    @UiThread
    void next();

    @UiThread
    void showNetWorkError(int type, String message);

    @UiThread
    void showContentView();

}
