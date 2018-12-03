package com.example.admin.applicationlifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import static android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN;

/**
 * 监听应用是在前台还是后台
 */
public class AppLifeCycleListener {
    private static boolean isBackground = true;
    private static ScreenOffReceiver screenOffReceiver;

    /**
     * 监听activity的生命周期
     *
     * @param application
     */
    public static void listerForForeground(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (isBackground) {
                    isBackground = false;
                    notifyForeground();
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    /**
     * 注册灭屏的广播
     *
     * @param application
     */
    public static void listerForScreenTurningOff(Application application) {
        screenOffReceiver = new ScreenOffReceiver();
        IntentFilter screenStateFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        application.registerReceiver(screenOffReceiver, screenStateFilter);
    }

    /**
     * 注销灭屏的广播
     *
     * @param application
     */
    public static void unRegisterScreenOffReceiver(Application application) {
        if (screenOffReceiver != null) {
            application.unregisterReceiver(screenOffReceiver);
        }
    }

    /**
     * 清理内存时的处理
     *
     * @param level
     */
    public static void onTrimMemory(int level) {
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            isBackground = true;
            notifyBackground();
        }
    }

    private static void notifyForeground() {
        // This is where you can notify listeners, handle session tracking, etc
    }

    private static void notifyBackground() {
        // This is where you can notify listeners, handle session tracking, etc
    }

    public static boolean isBackground() {
        return isBackground;
    }

    /**
     * 灭屏广播
     */
    public static class ScreenOffReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(App.TAG, "ScreenOffReceiver onReceive");
            isBackground = true;
            notifyBackground();
        }
    }
}
