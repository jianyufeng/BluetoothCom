package com.puyu.mobile.base.constant;

import android.os.Environment;

import java.io.File;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/5/23 16:21
 * desc   : 常亮
 * version: 1.0
 */
public class ConstantBase {
    /**
     * 应用名称
     */
    public static String APP_NAME = "FpiMobile";
    /**
     * 数据库名称
     */
    public static String DATABASE_NAME = "";
    //数据库路径
    public static String DB_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "fpiMobileDb";
    /**
     * 数据库版本
     */
    public static int DATABASE_VERSION = 1;
    /**
     * 数据库资源号 如："R.raw.*"
     */
    public static int DATABASE_RESOURCE = 0;
}
