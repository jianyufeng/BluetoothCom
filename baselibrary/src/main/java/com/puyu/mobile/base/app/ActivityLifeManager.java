package com.puyu.mobile.base.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.Stack;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/5/23 15:45
 * desc   :
 * version: 1.0
 */
public class ActivityLifeManager {
    private static Stack<Activity> mStackActivity;
    private ActivityLifeManager() {
        mStackActivity = new Stack<>();
    }
    public static ActivityLifeManager getInstance() {
        return SingletonHolder.alm;
    }
    private static class SingletonHolder {
        private static final ActivityLifeManager alm = new ActivityLifeManager();
    }
    public void init(Application weApp) {
        weApp.registerActivityLifecycleCallbacks(lifecycleCallbacks);
    }
    private Application.ActivityLifecycleCallbacks lifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            addActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            addActivity(activity);
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
            removeActivity(activity);
        }
    };

    private void removeActivity(Activity activity) {
        if (activity != null) {
            mStackActivity.remove(activity);
        }
    }

    private void addActivity(Activity activity) {
        if (!mStackActivity.contains(activity)) {
            mStackActivity.add(activity);
        }
    }
    /***********************
     * Activity Manager Tools
     ***********************/
    public Activity getLastActivity() {
        Activity activity = mStackActivity.lastElement();
        return activity;
    }

    public boolean isLastActivity(Activity activity) {
        if (activity != null) {
            return getLastActivity() == activity;
        } else {
            return false;
        }
    }

    public boolean isEmpty() {
        return mStackActivity.isEmpty();
    }
    public void finishLastActivity() {
        Activity activity = getLastActivity();
        finishActivity(activity);
    }
    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            mStackActivity.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : mStackActivity) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }
    public void finishAllActivity() {
        for (Activity activity : mStackActivity) {
            if (activity != null) {
                activity.finish();
            }
        }
        mStackActivity.clear();
    }
    public void finishAllActivityExcept(Class<?> cls) {
        for (int i = mStackActivity.size() - 1; i >= 0; i--) {
            Activity act = mStackActivity.get(i);
            if (act != null) {
                if (act.getClass().equals(cls)) {
                    continue;
                } else {
                    finishActivity(act);
                }
            }
        }
    }
    public void finishAllActivityExcept(Activity activity) {
        for (int i = mStackActivity.size() - 1; i >= 0; i--) {
            Activity act = mStackActivity.get(i);
            if (act != null) {
                if (act == activity) {
                    continue;
                } else {
                    finishActivity(act);
                }
            }
        }
    }
    /**
     * 判断activity是否存在
     *
     * @param name
     * @return
     */
    public boolean hasActivity(String name) {
        for (Activity activity : mStackActivity) {
            if (name.equals(activity.getLocalClassName())) {
                return true;
            }
        }
        return false;
    }
    /**
     * 找到activity
     *
     * @param name
     * @return
     */
    public Activity findActivity(String name) {
        for (Activity activity : mStackActivity) {
            if (name.equals(activity.getLocalClassName())) {
                return activity;
            }
        }
        return null;
    }
}
