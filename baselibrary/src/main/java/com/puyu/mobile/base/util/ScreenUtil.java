package com.puyu.mobile.base.util;

import android.content.Context;
import android.view.WindowManager;

import com.puyu.mobile.base.app.BaseApplication;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/5/27 20:55
 * desc   : 手机屏幕的尺寸信息
 * version: 1.0
 */
public class ScreenUtil {
    static float density;
    static int screenWidth, screenHeight, barHeight;

    /**
     * 返回屏幕密度
     *
     * @return
     */
    public static float getDensity() {
        if (density == 0) {
            density = BaseApplication.getInstance().getResources().getDisplayMetrics().density;
        }
        return density;
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @param （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(float pxValue) {
        return (int) (pxValue / getDensity() + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(float dipValue) {
        return (int) (dipValue * getDensity() + 0.5f);
    }

    /**
     * 获取宽度
     */
    public static int getWidth() {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) BaseApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
            screenWidth = wm.getDefaultDisplay().getWidth();
        }
        return screenWidth;
    }

    /**
     * 获取屏幕高度
     */
    public static int getHeight() {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) BaseApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
            screenHeight = wm.getDefaultDisplay().getHeight();
        }
        return screenHeight;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        if (barHeight == 0) {
            int resourceId = BaseApplication.getInstance().getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                barHeight = BaseApplication.getInstance().getResources().getDimensionPixelSize(resourceId);
            }
        }
        return barHeight;
    }
}
