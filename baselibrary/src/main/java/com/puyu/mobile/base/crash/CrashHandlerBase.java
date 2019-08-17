package com.puyu.mobile.base.crash;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import com.puyu.mobile.base.app.ActivityLifeManager;
import com.puyu.mobile.base.app.BaseApplication;
import com.puyu.mobile.base.constant.ConstantBase;
import com.puyu.mobile.base.util.APPVersionUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/5/23 15:43
 * desc   : 异常收集文件
 * version: 1.0
 */
public abstract class CrashHandlerBase implements Thread.UncaughtExceptionHandler {
    private Context mContext;

    /**
     * 异常发生时，系统回调的函数，我们在这里处理一些操作
     */
    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        // 显示异常信息
        showCrashToast();
        ex.printStackTrace();
        if (CrashConstant.SAVE) {
            savaInfoToSD(mContext, ex);
        }
        //退出activity
        ActivityLifeManager.getInstance().finishAllActivity();
        Intent intent = new Intent(BaseApplication.getInstance().getApplicationContext(), getRestartActivity());
        PendingIntent restartIntent = PendingIntent.getActivity(BaseApplication.getInstance().getApplicationContext(),
                0, intent, PendingIntent.FLAG_ONE_SHOT);
        //重启应用
        AlarmManager mgr = (AlarmManager) BaseApplication.getInstance().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis(), restartIntent); // 重启应用
        //退出程序
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    protected abstract Class<?> getRestartActivity();

    private void showCrashToast() {
        (new Thread() {
            public void run() {
                Looper.prepare();
                Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "很抱歉,程序出现异常,即将重启", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException var2) {

        }

    }

    public void setCrashHanler(Context context) {
        this.mContext = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private HashMap<String, String> obtainSimpleInfo(Context context) {
        LinkedHashMap<String, String> map = new LinkedHashMap();
        map.put("APP", ConstantBase.APP_NAME); //APP名字
        map.put("Brand", "" + Build.BRAND); //产品品牌	Meizu
        map.put("Model", "" + Build.MODEL); //M351
        map.put("SDK_Version", "" + Build.VERSION.SDK_INT);//Android API版本（String类型）
        map.put("versionName", APPVersionUtil.getVersionName(context));
        map.put("versionCode", "" + APPVersionUtil.getVersionCode(context));
        map.put("crashTime", this.paserTime(System.currentTimeMillis()));
        return map;
    }

    private String obtainExceptionInfo(Throwable throwable) {
        StringWriter mStringWriter = new StringWriter();
        PrintWriter mPrintWriter = new PrintWriter(mStringWriter);
        throwable.printStackTrace(mPrintWriter);
        mPrintWriter.close();
        return mStringWriter.toString();
    }

    private String savaInfoToSD(Context context, Throwable ex) {
        String fileName = null;
        StringBuffer sb = new StringBuffer();
        Iterator var5 = this.obtainSimpleInfo(context).entrySet().iterator();

        while (var5.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) var5.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }

        sb.append(this.obtainExceptionInfo(ex));
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(CrashConstant.CRASH_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            try {
                fileName = dir.toString() + File.separator + this.paserTime(System.currentTimeMillis()) + ".txt";
                FileOutputStream fos = new FileOutputStream(fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
            } catch (Exception var9) {
                var9.printStackTrace();
            }
        }

        return fileName;
    }

    private String paserTime(long milliseconds) {
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String times = format.format(new Date(milliseconds));
        return times;
    }
}
