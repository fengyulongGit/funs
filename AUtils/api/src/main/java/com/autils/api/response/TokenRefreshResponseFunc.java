package com.autils.api.response;

import com.autils.api.ServerClient;
import com.autils.api.exception.ApiException;
import com.autils.api.response.model.Token;

import io.reactivex.annotations.NonNull;

/**
 * Created by jisuyun on 2017/8/29.
 */

public class TokenRefreshResponseFunc extends BaseResponseFunc<Token> {
    @Override
    public Token apply(@NonNull BaseResponse<Token> baseResponse) throws Exception {
        int code = baseResponse.code;
        if (!BaseResponse.isSuccess(code)) {
            code = BaseResponse.CODE_TOKEN;
            throw new ApiException(code, baseResponse.desc);
        }
        if (baseResponse.data == null) {
            throw new ApiException(code, "");
        }
        Token result = baseResponse.data;
        if (result != null) {
            ServerClient.getInstance().getServerToken()
                    .setLogined(true)
                    .setToken(result);
        }
        return result;
    }
}
