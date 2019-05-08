package android.socket;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by fengyulong on 2019-05-07.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        L.init(true, "socket");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
