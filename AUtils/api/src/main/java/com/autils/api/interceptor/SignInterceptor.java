package com.autils.api.interceptor;

import com.autils.api.utils.LTrace;
import com.autils.api.utils.StringUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jisuyun on 2017/4/17.
 */
public class SignInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        RequestBody requestBody = request.body();
        if (requestBody instanceof MultipartBody) {
            Response response = chain.proceed(request);
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();

            LTrace.d("| Response code:" + response.code());
            LTrace.d("| Response:" + content);

            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }

        LTrace.d("|" + request.toString());
        LTrace.d("| Request Headers:{ " + request.headers().toString());
        LTrace.d("| Request Params:{ " + StringUtils.bodyToString(request.body()));

        Response response = chain.proceed(request);
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();

        LTrace.d("| Response code:" + response.code());
        LTrace.d("| Response:" + content);

        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
