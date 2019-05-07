package com.autils.api.response;

import com.autils.api.ServerClient;
import com.autils.api.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by fengyulong on 2018/7/27.
 */
public class RefreshTokenFunction implements Function<Observable<? extends Throwable>, ObservableSource<?>> {
    @Override
    public ObservableSource<?> apply(Observable<? extends Throwable> throwableObservable) {
        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                if (throwable instanceof ApiException) {
                    int code = ((ApiException) throwable).getResultCode();
                    if (BaseResponse.isRefreshToken(code)) {
                        return ServerClient.getInstance().dataSource().token_refresh();
                    }
                }
                return Observable.error(throwable);
            }
        });
    }
}
