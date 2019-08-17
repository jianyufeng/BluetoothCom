package com.puyu.mobile.base.app;

import android.app.Application;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/5/23 15:17
 * desc   :
 * version: 1.0
 */
public abstract class BaseApplication extends Application {
    public static int colorStatusBar;
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
        initBase();
    }

    protected abstract void init();

    private void initBase() {
        //注册管理 Activity
        ActivityLifeManager.getInstance().init(this);
        setCrashHandler();//设置异常收集
    }

    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    /**
     * crash异常监听，每个app都需要重写
     */
    protected abstract void setCrashHandler();
}
