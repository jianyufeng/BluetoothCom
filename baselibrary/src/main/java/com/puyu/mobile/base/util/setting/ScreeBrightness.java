package com.puyu.mobile.base.util.setting;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/5/30 16:40
 * desc   :  android系统的亮度值取值范围在0~255,数据类型是int型.
 * version: 1.0
 */
public class ScreeBrightness {
    /**
     * 获得系统 当前屏幕亮度的模式
     *
     * @param context
     * @return
     */
    public static int getScreenMode(Context context) {
        int screenMode = 0;
        try {
            screenMode = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return screenMode;
    }

    /**
     * 获得系统 当前屏幕亮度的模式
     * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
     * SCREEN_BRIGHTNESS_MODE_MANUAL=0  为手动调节屏幕亮度
     */
    public static void setScreenMode(Context context, int paramInt) {
        try {
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, paramInt);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    /**
     * 获得系统当前屏幕亮度值  0--255
     */
    public static int getScreenBrightness(Context context) {
        int screenBrightness = 255;
        try {
            screenBrightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception localException) {

        }
        return screenBrightness;
    }

    /**
     * 获得系统 当前屏幕亮度值  0--255
     */
    public static void setScreenBrightness(Context context, int paramInt) {
        try {
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, paramInt);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    /**
     * 设置当前Activity的屏幕亮度值
     * 注意项目中 要没有   WindowManager.LayoutParams.TYPE_SYSTEM_ERROR
     * WindowManager 添加此类型 的 View
     * 如果有，设置失败  可以使用 ScreeBrightness2 请参考说明
     */
    public static void setActivityBrightness(Activity context, int paramInt) {
        Window window = context.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = paramInt * (1f / 255f);
        window.setAttributes(lp);
    }

    /**
     * 获取当前activity的屏幕亮度
     * 注意项目中 要没有   WindowManager.LayoutParams.TYPE_SYSTEM_ERROR
     * WindowManager 添加此类型 的 View
     * 如果有，设置失败  可以使用 ScreeBrightness2 请参考说明
     *
     * @param activity 当前的activity对象
     * @return 亮度值范围为0-0.1f，如果为-1.0，则亮度与全局同步。
     */
    public static float getActivityBrightness(Activity activity) {
        Window localWindow = activity.getWindow();
        WindowManager.LayoutParams params = localWindow.getAttributes();
        return params.screenBrightness;
    }
}

