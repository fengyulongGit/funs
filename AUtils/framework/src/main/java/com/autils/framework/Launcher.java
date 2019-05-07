package com.autils.framework;

import android.app.Application;
import android.content.Intent;
import android.support.annotation.ColorInt;

import com.autils.api.ServerClient;
import com.autils.api.ServerConfig;
import com.autils.api.ServerToken;
import com.autils.api.response.model.SellerInfo;
import com.autils.api.response.model.Token;
import com.autils.framework.common.network.NetStatusInterceptor;
import com.autils.framework.data.db.DbHelper;

/**
 * Created by fengyulong on 2018/5/10.
 */
public class Launcher {
    private static Launcher instance;

    private Launcher() {
    }

    /**
     * 单一实例
     */
    public static Launcher getInstance() {
        if (instance == null) {
            synchronized (Launcher.class) {
                if (instance == null) {
                    instance = new Launcher();
                }
            }
        }
        return instance;
    }

    private boolean isDebug = false;
    private Application application;
    private boolean isOfficial = false;

    private int titleBgColor;

    private Intent loginIntent;

    public Launcher setLoginIntent(Intent loginIntent) {
        this.loginIntent = loginIntent;
        return this;
    }

    public Intent getLoginIntent() {
        return loginIntent;
    }

    public Launcher setTitleBgColor(@ColorInt int color) {
        titleBgColor = color;
        return this;
    }

    public Application getApplication() {
        return application;
    }

    public Launcher setDebug(boolean debug) {
        isDebug = debug;
        return this;
    }

    public Launcher setApplication(Application application) {
        this.application = application;
        return this;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public boolean isOfficial() {
        return application.getPackageName().startsWith("com.autils");
    }

    public String officialUrl() {
        if ("com.autils".equals(application.getPackageName())) {
            return "https://www.jisujie.com/download/autils";
        }
        return "";
    }

    public int getTitleBgColor() {
        return application.getResources().getColor(titleBgColor <= 0 ? R.color.transparent : titleBgColor);
    }

    public Launcher initApi(ServerConfig config) {
        ServerToken token = ServerToken.getDefaultToken();

        Token result = DbHelper.getInstance().getToken();
        if (result != null) {
            token.setLogined(true)
                    .setToken(result);
        }

        SellerInfo sellerInfo = DbHelper.getInstance().getSellerInfo();
        if (sellerInfo != null) {
            token.setSellerInfo(sellerInfo);
        }

        ServerClient.getInstance()
                .setServerConfig(config)
                .setServerToken(token)
                .addInterceptorFirst(new NetStatusInterceptor())
                .build();
        return this;
    }

}
