package com.hucare.hushare.lifecycle;

import android.app.Application;

import com.hushare.hucare.simplelifecyclelibrary.ActivityLifecycleControl;
import com.hushare.hucare.simplelifecyclelibrary.AppDelegate;

/**
 * 功能/模块 ：***
 *
 * @author huzeliang
 * @version 1.0 ${date} ${time}
 * @see ***
 * @since ***
 */
public class MyApplication extends Application {

    private AppDelegate mAppDelegate;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mAppDelegate = new AppDelegate(this, new ActivityLifecycleControl(this));
        this.mAppDelegate.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        this.mAppDelegate.onTerminate();
    }
}
