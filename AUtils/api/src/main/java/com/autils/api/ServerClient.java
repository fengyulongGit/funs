package com.autils.api;

import com.autils.api.interceptor.SignInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jisuyun on 2017/8/28.
 */

public final class ServerClient {

    public static final long DEFAULT_CONNECT_TIMEOUT = 30_000;
    public static final long DEFAULT_READ_TIMEOUT = 30_000;
    public static final long DEFAULT_WRITE_TIMEOUT = 30_000;

    private long connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    private long readTimeout = DEFAULT_READ_TIMEOUT;
    private long writeTimeout = DEFAULT_WRITE_TIMEOUT;
    private List<Interceptor> interceptors = new ArrayList<>();

    private static ServerClient instance;

    public static ServerClient getInstance() {
        if (instance == null) {
            synchronized (ServerClient.class) {
                if (instance == null) {
                    instance = new ServerClient();
                }
            }
        }
        return instance;
    }

    public ServerClient connectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public ServerClient readTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public ServerClient writeTimeout(long writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public ServerClient addInterceptor(Interceptor interceptor) {
        this.interceptors.add(interceptor);
        return this;
    }

    public ServerClient addInterceptorFirst(Interceptor interceptor) {
        this.interceptors.add(0, interceptor);
        return this;
    }

    public ServerClient removeAllInterceptors() {
        this.interceptors.clear();
        return this;
    }

    private ServerConfig serverConfig;

    public ServerConfig getServerConfig() {
        if (serverConfig == null) {
            serverConfig = ServerConfig.getDefaultConfig(false);
        }
        return serverConfig;
    }

    public ServerClient setServerConfig(ServerConfig serverConfig) {
        if (serverConfig == null) {
            serverConfig = ServerConfig.getDefaultConfig(false);
        }
        this.serverConfig = serverConfig;
        return this;
    }

    public ServerClient resetConfig(ServerConfig config) {
        setServerConfig(config);
        build();
        return this;
    }

    private ServerToken serverToken;

    public ServerToken getServerToken() {
        if (serverToken == null) {
            serverToken = ServerToken.getDefaultToken();
        }
        return serverToken;
    }

    public ServerClient setServerToken(ServerToken token) {
        if (token == null) {
            token = ServerToken.getDefaultToken();
        }
        this.serverToken = token;
        return this;
    }

    private Retrofit retrofitApi;
    private DataSource dataSource;

    public void build() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        for (Interceptor interceptor : interceptors) {
            okHttpClientBuilder.addInterceptor(interceptor);
        }

        okHttpClientBuilder
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                .addInterceptor(new SignInterceptor());

        final OkHttpClient okHttpClient = okHttpClientBuilder.build();

        retrofitApi = new Retrofit.Builder()
                .baseUrl(this.serverConfig.getHost_api())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        dataSource = new DataSource(retrofitApi);
    }

    public DataSource dataSource() {
        return dataSource;
    }

    public Retrofit getRetrofitApi() {
        return retrofitApi;
    }
}
