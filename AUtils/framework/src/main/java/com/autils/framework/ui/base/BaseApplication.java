package com.autils.framework.ui.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.autils.framework.R;
import com.autils.framework.ui.view.fragmentation.Fragmentation;
import com.tencent.smtt.sdk.QbSdk;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by fengyulong on 2018/10/11.
 */
public abstract class BaseApplication extends Application {
    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fragmentation.builder()
                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
//                .stackViewMode(Fragmentation.BUBBLE)
                .debug(isDebug())
                .install();

        //设置默认字体
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .setDefaultFontPath("fonts/PingFang-Regular.ttf")
                .build()
        );

        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }
        });
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public abstract boolean isDebug();
}
