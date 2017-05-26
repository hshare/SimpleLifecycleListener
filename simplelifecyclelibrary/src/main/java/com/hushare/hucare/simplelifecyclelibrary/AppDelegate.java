package com.hushare.hucare.simplelifecyclelibrary;

import android.app.Application;

/**
 * 功能/模块 ：懂就是懂，不懂解释了也不懂
 *
 * @author huzeliang
 * @version 1.0 2017-5-24 10:21:59
 * @see ***
 * @since ***
 */
public class AppDelegate {
    private Application mApplication;
    private ActivityLifecycleControl activityLifecycleControl;

    public AppDelegate(Application application, ActivityLifecycleControl activityLifecycleControl) {
        this.mApplication = application;
        this.activityLifecycleControl = activityLifecycleControl;
    }


    public void onCreate() {
        mApplication.registerActivityLifecycleCallbacks(activityLifecycleControl);
    }


    public void onTerminate() {
        if (activityLifecycleControl != null) {
            mApplication.unregisterActivityLifecycleCallbacks(activityLifecycleControl);
        }
        this.activityLifecycleControl = null;
        this.mApplication = null;
    }


}

