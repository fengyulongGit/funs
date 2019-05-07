package com.autils.framework.common.network;

import com.autils.api.exception.ApiException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by fengyulong on 2018/5/10.
 */
public class NetStatusInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain == null) {
            throw new IOException();
        }
        if (NetworkMonitor.isConnected()) {
            return chain.proceed(chain.request());
        } else {
            throw new ApiException(ApiException.STATUS_CODE_NO_NETWORK, "无法访问网络");
        }
    }
}
