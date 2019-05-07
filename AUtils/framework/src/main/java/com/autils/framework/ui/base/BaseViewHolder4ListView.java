package com.autils.framework.ui.base;

import android.annotation.SuppressLint;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * Created by fengyulong on 2018/8/1.
 */
public abstract class BaseViewHolder4ListView<E> {

    protected View itemView;

    public BaseViewHolder4ListView(View itemView) {
        this.itemView = itemView;
        initView();
    }

    public abstract void initView();

    public abstract void initViewData(E e);


    public <T extends View> T $(int viewID) {
        return (T) itemView.findViewById(viewID);
    }

    @SuppressLint("CheckResult")
    public <T extends View> void $clicks(final T t, final View.OnClickListener onClickListener) {
        if (t == null) {
            return;
        }
        RxView.clicks(t)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) {
                        if (onClickListener != null) {
                            onClickListener.onClick(t);
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    public <T extends View> void $longClicks(final T t, final View.OnLongClickListener onLongClickListener) {
        if (t == null) {
            return;
        }

        RxView.longClicks(t)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) {
                        if (onLongClickListener != null) {
                            onLongClickListener.onLongClick(t);
                        }
                    }
                });
    }

}
