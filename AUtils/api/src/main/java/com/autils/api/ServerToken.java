package com.autils.api;

import com.autils.api.response.model.SellerInfo;
import com.autils.api.response.model.Token;

/**
 * Created by fengyulong on 2018/5/24.
 */
public class ServerToken {
    private boolean isLogined;

    private Token token;
    private SellerInfo sellerInfo;

    public boolean isLogined() {
        return isLogined;
    }

    public ServerToken setLogined(boolean logined) {
        isLogined = logined;
        return this;
    }

    public String getAccess_token() {
        if (token != null) {
            return token.getAccess_token();
        }
        return "";
    }

    public String getRefresh_token() {
        if (token != null) {
            return token.getRefresh_token();
        }
        return "";
    }

    public String getUserId() {
        if (sellerInfo != null) {
            return sellerInfo.getSysUser().getUserId();
        }
        return "";
    }


    public ServerToken setToken(Token token) {
        this.token = token;
        return this;
    }

    public ServerToken setSellerInfo(SellerInfo sellerInfo) {
        this.sellerInfo = sellerInfo;
        return this;
    }

    public static ServerToken getDefaultToken() {
        ServerToken token = new ServerToken();
        token.isLogined = false;
        token.token = null;
        token.sellerInfo = null;
        return token;
    }
}
