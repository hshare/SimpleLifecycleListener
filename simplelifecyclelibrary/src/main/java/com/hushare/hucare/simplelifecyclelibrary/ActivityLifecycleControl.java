package com.hushare.hucare.simplelifecyclelibrary;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 功能/模块 ：懂就是懂，不懂解释了也不懂
 *
 * @author huzeliang
 * @version 1.0 2017-5-24 10:21:59
 * @see ***
 * @since ***
 */
public class ActivityLifecycleControl implements Application.ActivityLifecycleCallbacks {

    private Application application;
    private FragmentLifecycle mFragmentLifecycle;

    public ActivityLifecycleControl(Application application) {
        this.application = application;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        iiiLog("onActivityCreated:" + activity.getClass().getSimpleName(), activity);

        if (activity instanceof IActivity && activity.getIntent() != null) {
            ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
            if (activityDelegate == null) {
                activityDelegate = new ActivityDelegateImpl(activity);
                activity.getIntent().putExtra(ActivityDelegate.ACTIVITY_DELEGATE, activityDelegate);
            }
            activityDelegate.onCreate(savedInstanceState);
        }

        if (activity instanceof FragmentActivity) {

            if (mFragmentLifecycle == null) {
                mFragmentLifecycle = new FragmentLifecycle();
            }

            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(mFragmentLifecycle, true);

        }

    }

    @Override
    public void onActivityStarted(Activity activity) {
        iiiLog("onActivityStarted:" + activity.getClass().getSimpleName(), activity);
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onStart();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        iiLog("onActivityResumed:" + activity.getClass().getSimpleName(), activity);
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onResume();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        iiiLog("onActivityPaused:" + activity.getClass().getSimpleName(), activity);
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onPause();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        iiiLog("onActivityStopped:" + activity.getClass().getSimpleName(), activity);
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onStop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        iiiLog("onActivitySaveInstanceState:" + activity.getClass().getSimpleName(), activity);
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        iiiLog("onActivityDestroyed:" + activity.getClass().getSimpleName(), activity);
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onDestroy();
            activity.getIntent().removeExtra(ActivityDelegate.ACTIVITY_DELEGATE);
        }
        if (activity instanceof FragmentActivity) {
            if (mFragmentLifecycle != null) {
                ((FragmentActivity) activity).getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(mFragmentLifecycle);
            }

        }
    }

    private static void iiLog(String msg, Activity activity) {
        Log.i("hzl-lifecycle", "" + msg);
        FrameLayout view = (FrameLayout) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        TextView textView = new TextView(activity);
        textView.setText(msg);
        view.addView(textView);
    }

    private static void iiiLog(String msg, Activity activity) {
        Log.i("hzl-lifecycle", "" + msg);
    }

    private ActivityDelegate fetchActivityDelegate(Activity activity) {
        ActivityDelegate activityDelegate = null;
        if (activity instanceof IActivity && activity.getIntent() != null) {
            activityDelegate = activity.getIntent().getParcelableExtra(ActivityDelegate.ACTIVITY_DELEGATE);
        }
        return activityDelegate;
    }

    static class FragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks {


        @Override
        public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
            super.onFragmentAttached(fm, f, context);
            iiiLog(f.getClass().getSimpleName() + "-----" + "onFragmentAttached", f.getActivity());
            if (f instanceof IFragment && f.getArguments() != null) {
                FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
                if (fragmentDelegate == null) {
                    fragmentDelegate = new FragmentDelegateImpl(fm, f);
                    f.getArguments().putParcelable(FragmentDelegate.FRAGMENT_DELEGATE, fragmentDelegate);
                }
                fragmentDelegate.onAttach(context);
            }
        }

        @Override
        public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
            super.onFragmentCreated(fm, f, savedInstanceState);
            iiiLog(f.getClass().getSimpleName() + "-----" + "onFragmentCreated", f.getActivity());
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onCreate(savedInstanceState);
            }
        }

        @Override
        public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState);
            iiiLog(f.getClass().getSimpleName() + "-----" + "onFragmentViewCreated", f.getActivity());
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onCreateView(v, savedInstanceState);
            }
        }

        @Override
        public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
            super.onFragmentActivityCreated(fm, f, savedInstanceState);
            iiiLog(f.getClass().getSimpleName() + "-----" + "onFragmentActivityCreated", f.getActivity());
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onActivityCreate(savedInstanceState);
            }
        }

        @Override
        public void onFragmentStarted(FragmentManager fm, Fragment f) {
            super.onFragmentStarted(fm, f);
            iiiLog(f.getClass().getSimpleName() + "-----" + "onFragmentStarted", f.getActivity());
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onStart();
            }
        }

        @Override
        public void onFragmentResumed(FragmentManager fm, Fragment f) {
            super.onFragmentResumed(fm, f);
            iiiLog(f.getClass().getSimpleName() + "-----" + "onFragmentResumed", f.getActivity());
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onResume();
            }
        }

        @Override
        public void onFragmentPaused(FragmentManager fm, Fragment f) {
            super.onFragmentPaused(fm, f);
            iiiLog(f.getClass().getSimpleName() + "-----" + "onFragmentPaused", f.getActivity());
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onPause();
            }
        }

        @Override
        public void onFragmentStopped(FragmentManager fm, Fragment f) {
            super.onFragmentStopped(fm, f);
            iiiLog(f.getClass().getSimpleName() + "-----" + "onFragmentStopped", f.getActivity());
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onStop();
            }
        }

        @Override
        public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
            super.onFragmentViewDestroyed(fm, f);
            iiiLog(f.getClass().getSimpleName() + "-----" + "onFragmentViewDestroyed", f.getActivity());
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onDestroyView();
            }
        }

        @Override
        public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
            super.onFragmentDestroyed(fm, f);
            iiiLog(f.getClass().getSimpleName() + "-----" + "onFragmentDestroyed", f.getActivity());
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onDestroy();
            }
        }

        @Override
        public void onFragmentDetached(FragmentManager fm, Fragment f) {
            super.onFragmentDetached(fm, f);
            iiiLog(f.getClass().getSimpleName() + "-----" + "onFragmentDetached", f.getActivity());
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onDetach();
                f.getArguments().clear();
            }
        }

        private FragmentDelegate fetchFragmentDelegate(Fragment fragment) {
            if (fragment instanceof IFragment) {
                return fragment.getArguments() == null ? null : (FragmentDelegate) fragment.getArguments().getParcelable(FragmentDelegate.FRAGMENT_DELEGATE);
            }
            return null;
        }
    }
}
