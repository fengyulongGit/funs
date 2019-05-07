package com.autils.framework.ui.base.mvp;

import com.autils.api.DataSource;
import com.autils.api.ServerClient;
import com.autils.api.exception.ApiException;
import com.autils.api.response.BaseResponse;
import com.autils.framework.common.log.L;
import com.autils.framework.data.db.DbHelper;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengyulong on 2018/5/11.
 */
public class RxPresenter<V extends ILoadView> extends BasePresenter<V> {

    public CompositeDisposable mCompositeDisposable;
    public DataSource dataSource;
    public DbHelper db;

    @Override
    public void attachView(V view) {
        super.attachView(view);
        mCompositeDisposable = new CompositeDisposable();
        dataSource = ServerClient.getInstance().dataSource();

        db = DbHelper.getInstance();
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }

    public <T> Disposable DoObserver(Observable<T> observable, DisposableObserver<T> disposableObserver) {
        return DoObserver(observable, null, disposableObserver);
    }

    public <T> Disposable DoObserver(Observable<T> observable, final Object loadingMessage, DisposableObserver<T> disposableObserver) {
        return observable.throttleFirst(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (getView() != null) {
                            if (loadingMessage == null) {
                                getView().showLoading();
                            } else {
                                if (loadingMessage instanceof String) {
                                    getView().showLoading(loadingMessage.toString());
                                } else if (loadingMessage instanceof Integer) {
                                    getView().showLoading((Integer) loadingMessage);
                                } else {
                                    getView().showLoading();
                                }
                            }
                        }
                    }
                })
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (getView() != null) {
                            getView().dismissLoading();
                        }
                    }
                }).subscribeWith(disposableObserver);
    }

    public void addCompositeDisposable(Disposable disposable) {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

    public abstract class RxObserver<T> extends DisposableObserver<T> {

        @Override
        public void onNext(T t) {
            try {
                next(t);
            } catch (Exception e) {
                onError(e);
            }
        }

        protected abstract void next(T t);

        @Override
        public void onError(Throwable e) {
            if (e instanceof ApiException) {
                int code = ((ApiException) e).getResultCode();
                if (BaseResponse.isSuccess(code)) {
                    onNext(null);
                    return;
                }
            }
            if (getView() != null) {
                getView().error(e);
            }
            L.e(e);
        }

        @Override
        public void onComplete() {

        }
    }
}
