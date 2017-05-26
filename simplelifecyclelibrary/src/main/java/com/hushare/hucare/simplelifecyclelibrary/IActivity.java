package com.hushare.hucare.simplelifecyclelibrary;

/**
 * 你懂的
 */
public interface IActivity {

    int getLayout();

    void initView();

    void initData();

    boolean useEventBus();
}
