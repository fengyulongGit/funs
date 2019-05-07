package com.autils.api.response;

import com.autils.api.ServerClient;
import com.autils.api.response.model.Token;

import io.reactivex.annotations.NonNull;

/**
 * Created by jisuyun on 2017/8/29.
 */

public class TokenResponseFunc extends BaseResponseFunc<Token> {
    @Override
    public Token apply(@NonNull BaseResponse<Token> baseResponse) throws Exception {
        super.apply(baseResponse);
        Token result = baseResponse.data;
        if (null != result) {
            ServerClient.getInstance().getServerToken()
                    .setLogined(true)
                    .setToken(result);
        }
        return result;
    }
}
