package com.autils.api;

/**
 * Created by fengyulong on 2018/5/24.
 */
public class ServerConfig {
    private boolean isDebug;
    private String host_api;
    private String port_api;
    private String h5_host;
    private String h5_port;
    private String appname;
    private String configversion;
    private String device;

    public boolean isDebug() {
        return isDebug;
    }

    public ServerConfig setDebug(boolean debug) {
        isDebug = debug;
        return this;
    }

    public String getHost_api() {
        return String.format("%s:%s/", host_api, port_api);
    }

    public String getH5_host() {
        return String.format("%s:%s/", h5_host, h5_port);
    }

    public String getAppname() {
        return appname;
    }

    public ServerConfig setAppname(String appname) {
        this.appname = appname;
        return this;
    }

    public String getConfigversion() {
        return configversion;
    }

    public ServerConfig setConfigversion(String configversion) {
        this.configversion = configversion;
        return this;
    }

    public String getDevice() {
        return device;
    }

    public ServerConfig setDevice(String device) {
        this.device = device;
        return this;
    }

    private ServerConfig() {
    }

    public static ServerConfig getDefaultConfig(boolean isDebug) {
        //TODO 发布时屏蔽debug状态
//        isDebug = false;
//        isDebug = true;
        ServerConfig config = new ServerConfig();
        config.isDebug = isDebug;
        config.host_api = isDebug ? "http://dituiapi.test.jisucloud.cn" : "https://dituiapi.jisujie.com";
        config.port_api = isDebug ? "80" : "443";
        config.h5_host = isDebug ? "http://dituiclient.test.jisucloud.cn" : "https://dituiclient.jisujie.com";
        config.h5_port = isDebug ? "80" : "443";
        config.appname = "autils";
        config.configversion = "1.0.0";
        config.device = "android";
        return config;
    }

}
