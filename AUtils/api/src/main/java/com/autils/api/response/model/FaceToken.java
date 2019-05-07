package com.autils.api.response.model;

import java.io.Serializable;

/**
 * Created by fengyulong on 2018/6/20.
 */
public class FaceToken implements Serializable {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
