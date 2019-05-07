package com.autils.ui.application;

import com.autils.BuildConfig;
import com.autils.api.ServerConfig;
import com.autils.framework.Launcher;
import com.autils.framework.common.log.L;
import com.autils.framework.common.utils.AppManager;
import com.autils.framework.common.utils.AppUtils;
import com.autils.framework.ui.base.BaseApplication;
import com.autils.ui.activity.MainActivity;

/**
 * Created by fengyulong on 2019/4/11.
 */
public class AUtilsApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        ServerConfig config = ServerConfig.getDefaultConfig(BuildConfig.DEBUG);
        config.setConfigversion(AppUtils.getVersionName(this));
        config.setAppname("autils");

        Launcher.getInstance()
                .setApplication(this)
                .setDebug(BuildConfig.DEBUG)
                .setLoginIntent(null)
                .initApi(config);

        L.init(BuildConfig.DEBUG, "autils");
        AppManager.getAppManager().setMainClass(MainActivity.class);
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
