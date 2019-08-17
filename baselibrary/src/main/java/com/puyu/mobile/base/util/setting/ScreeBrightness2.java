package com.puyu.mobile.base.util.setting;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/5/30 20:16
 * desc   : 网上大部分文章是通过Activity调节屏幕亮度的，但是这种亮度调节方法存在一个隐藏的bug，
 * 就是当如果当前屏幕上又一个悬浮窗，并且悬浮窗的type为WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;的时候，
 * 无论你怎么在activity中设置屏幕亮度都是无效的。猜测原因应该是由于当前的悬浮窗与设置亮度的activity
 * 处于同一个viewgroup或者悬浮窗处于更高一级的viewgroup，导致activity设置屏幕亮度无效。解决方法很简单，
 * 自己定义一个更高优先级的悬浮窗进行亮度设置，然后再移除悬浮窗就可以了。
 * 作者：bluejoy345
 * 来源：CSDN
 * 原文：https://blog.csdn.net/bluejoy345/article/details/17009283
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 * version: 1.0
 */
public class ScreeBrightness2 {
    private static ScreeBrightness2 mBrightnessSet;

    private ScreeBrightness2(Context context) {
        init(context);
    }


    public static ScreeBrightness2 getInstance(Context context) {
        if (mBrightnessSet == null) {
            mBrightnessSet = new ScreeBrightness2(context);
        }
        return mBrightnessSet;
    }


    public void setScreenBrightness(int brightness) {
        setBrightness(brightness);
    }


    /**
     * 初始化悬浮窗
     */
    private void init(Context context) {
        floatView = new TextView(context.getApplicationContext());
        windowManager = (WindowManager) context.getApplicationContext().getSystemService(
                Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

        params.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明

        params.width = 0;
        params.height = 0;
        params.screenBrightness = 1;
        windowManager.addView(floatView, params);
    }

    private TextView floatView;
    private WindowManager windowManager;
    private WindowManager.LayoutParams params;

    /**
     * 创建悬浮窗,通过悬浮窗设置亮度
     */
    private void setBrightness(int brightness) {
        params.screenBrightness = brightness * (1f / 255f);
        windowManager.updateViewLayout(floatView, params);
    }

    /**
     * 当跳转到其他页面需要移除
     */
    public void removeFloatView() {
        if (floatView != null)
            windowManager.removeView(floatView);
    }
}
