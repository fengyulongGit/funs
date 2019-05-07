package com.autils.framework.ui.base.mvp;

import android.support.annotation.UiThread;

/**
 * Created by fengyulong on 2018/5/11.
 */
public interface IPresenter<V extends IView> {
    /**
     * Set or attach the view to this presenter
     */
    @UiThread
    void attachView(V view);

    /**
     * Will be called if the view has been destroyed. Typically this method will be invoked from
     * <code>Activity.detachView()</code> or <code>Fragment.onDestroyView()</code>
     */
    @UiThread
    void detachView();
}
