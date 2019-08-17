package com.puyu.mobile.base.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/5/29 10:09
 * desc   : 自定义状态栏 简单的点击处理
 * version: 1.0
 */
public class StateBarPull {
    //禁止状态栏下拉
    public static void prohibitDropDown(final Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        localLayoutParams.gravity = Gravity.TOP;
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                // this is to enable the notification to recieve touch events
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                // Draws over status bar
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        localLayoutParams.height = ScreenUtil.getStatusBarHeight();
        localLayoutParams.format = PixelFormat.TRANSPARENT;
        View view = new View(context);
        view.setBackgroundColor(Color.RED);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"ssss",Toast.LENGTH_LONG).show();
            }
        });
        manager.addView(view, localLayoutParams);
    }

    public static void allowDropDown(Context context, View view) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.removeView(view);
    }

}
