package com.puyu.mobile.base.util;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.puyu.mobile.base.app.BaseApplication;

import es.dmoral.toasty.Toasty;


/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/6/24 19:45
 * desc   :
 * version: 1.0
 */
public class ToastUtil {
    /***弹出成功消息**@param text 需要显示的消息*@param isShowIcon是否需要显示图标 默认显示* */
    public static void success(@NonNull String text, Boolean isShowIcon) {
        Toasty.success(BaseApplication.getInstance(), text, Toast.LENGTH_SHORT, isShowIcon).show();
    }

    /***弹出错误消息*@param text 需要显示的消息  *@param isShowIcon 是否需要显示图标 默认显示* */
    public static void error(@NonNull String text, Boolean isShowIcon) {
        Toasty.error(BaseApplication.getInstance(), text, Toast.LENGTH_SHORT, isShowIcon).show();
    }

    /***弹出一般消息*@param text 需要显示的消息* */
    public static void normal(@NonNull String text) {
        Toasty.normal(BaseApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
    }

    /***弹出警告消息@param text 需要显示的消息  *@param isShowIcon 是否需要显示图标 默认显示* */
    public static void warning(Context context, @NonNull String text, Boolean isShowIcon) {
        Toasty.warning(BaseApplication.getInstance(), text, Toast.LENGTH_SHORT, isShowIcon).show();
//        TastyToast.makeText(AppApplication.getInstance(),text,TastyToast.LENGTH_LONG,TastyToast.ERROR);
    }


}
