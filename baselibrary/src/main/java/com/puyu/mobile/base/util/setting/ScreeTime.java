package com.puyu.mobile.base.util.setting;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/5/31 10:43
 * desc   :   获取和设置屏幕息屏时间
 * version: 1.0
 */
public class ScreeTime {
    public static float getScreeOffTime(Context context) {
        //获取屏幕息屏时间
        float m = 0;
        try {
            m = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        Log.d("TAG", "get: " + m);
        return m;
    }

    //设置屏幕息屏时间
    public static boolean setScreeOffTime(Context context, int time) {
        boolean b = Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, time);
        Log.d("TAG", "set: " + b);
        return b;
    }

}
