package com.autils.api.response;

import com.autils.api.exception.ApiException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by jisuyun on 2017/4/23.
 */

public class BaseResponseFunc<T> implements Function<BaseResponse<T>, T> {
    @Override
    public T apply(@NonNull BaseResponse<T> baseResponse) throws Exception {
        int code = baseResponse.code;
        if (!BaseResponse.isSuccess(code)) {
            throw new ApiException(code, baseResponse.desc);
        }
        T t = baseResponse.data;
        if (t == null) {
            throw new ApiException(code, "");
        }
        return t;
    }
}