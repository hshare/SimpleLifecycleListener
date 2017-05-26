package com.hushare.hucare.simplelifecyclelibrary;

import android.os.Bundle;
import android.os.Parcelable;

/**
 * 懂就是懂，不懂解释了也不懂
 */

public interface ActivityDelegate extends Parcelable {
    String ACTIVITY_DELEGATE = "activity_delegate";

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Bundle outState);

    void onDestroy();
}
