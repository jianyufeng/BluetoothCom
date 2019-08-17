package com.puyu.mobile.base.util.setting;

import android.content.Context;
import android.os.SystemClock;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/6/4 15:15
 * desc   :  获取手机开机时间 及
 * version: 1.0
 */
public class SysConfig {


    //获取开机运行时间  单位微秒
    public static long getSysRealtime(Context context, int time) {
        long s = System.currentTimeMillis() - SystemClock.elapsedRealtimeNanos() / 1000000;
        return s;
    }

    //开关机
    public static void shuntSys(Context context, int time) {

    }
}
