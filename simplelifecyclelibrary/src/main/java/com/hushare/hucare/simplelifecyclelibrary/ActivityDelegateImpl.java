package com.hushare.hucare.simplelifecyclelibrary;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.TextView;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 懂就是懂，不懂解释了也不懂
 */

public class ActivityDelegateImpl implements ActivityDelegate {
    private Activity mActivity;
    private IActivity iActivity;
    private Unbinder mUnbinder;

    public ActivityDelegateImpl(Activity activity) {
        this.mActivity = activity;
        this.iActivity = (IActivity) activity;
    }


    public void onCreate(Bundle savedInstanceState) {
        if (iActivity.useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().register(mActivity);//注册到事件主线
        try {
            int layoutResID = iActivity.getLayout();
            if (layoutResID != 0)//如果initView返回0,框架则不会调用setContentView()
                mActivity.setContentView(layoutResID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(mActivity);
        iActivity.initView();
        initTopBar();
        iActivity.initData();
    }

    private void initTopBar() {
        if (mActivity instanceof IActivity) {
            if (mActivity.findViewById(R.id.tvTopBarTitle) != null) {
                ((TextView) mActivity.findViewById(R.id.tvTopBarTitle)).setText(mActivity.getTitle());
            }
            if (mActivity.findViewById(R.id.rlTopBarBack) != null) {
                mActivity.findViewById(R.id.rlTopBarBack).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mActivity.onBackPressed();
                    }
                });
            }
        }
    }

    public void onStart() {

    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onStop() {

    }

    public void onSaveInstanceState(Bundle outState) {

    }


    public void onDestroy() {
        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        if (iActivity.useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().unregister(mActivity);
        this.mUnbinder = null;
        this.iActivity = null;
        this.mActivity = null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    protected ActivityDelegateImpl(Parcel in) {
        this.mActivity = in.readParcelable(Activity.class.getClassLoader());
        this.iActivity = in.readParcelable(IActivity.class.getClassLoader());
        this.mUnbinder = in.readParcelable(Unbinder.class.getClassLoader());
    }

    public static final Creator<ActivityDelegateImpl> CREATOR = new Creator<ActivityDelegateImpl>() {
        @Override
        public ActivityDelegateImpl createFromParcel(Parcel source) {
            return new ActivityDelegateImpl(source);
        }

        @Override
        public ActivityDelegateImpl[] newArray(int size) {
            return new ActivityDelegateImpl[size];
        }
    };
}
