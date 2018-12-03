package com.example.admin.applicationlifecycle;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

public class App extends Application {

    public static final String TAG = "App-->";

    @Override
    public void onCreate() {
        super.onCreate();
        AppLifeCycleListener.listerForForeground(this);
        AppLifeCycleListener.listerForScreenTurningOff(this);
    }

    @Override
    public void onTerminate() {
        Log.d(TAG,"onTerminate");
        super.onTerminate();
        AppLifeCycleListener.unRegisterScreenOffReceiver(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d(TAG,"onTrimMemory");
        AppLifeCycleListener.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
