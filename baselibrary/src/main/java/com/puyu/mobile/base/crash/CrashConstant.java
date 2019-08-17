package com.puyu.mobile.base.crash;

import android.os.Environment;

import java.io.File;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/5/23 15:43
 * desc   : 异常常量
 * version: 1.0
 */
public class CrashConstant {
    public static boolean SAVE = true;
    //crash日志存储路径
    public static String CRASH_ROOT = Environment.getExternalStorageDirectory().toString() + File.separator;
    public static String CRASH_DIR = "fpiMobileCrash";
    public static String CRASH_PATH = CRASH_ROOT + CRASH_DIR;
}
