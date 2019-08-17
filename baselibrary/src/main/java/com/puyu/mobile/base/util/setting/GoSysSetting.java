package com.puyu.mobile.base.util.setting;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/5/31 9:47
 * 嵌套类
 * 类	Settings.Global	全球系统设置，包含喜好总是适用于所有定义的用户相同。 
 * 类	Settings.NameValueTable	名称/值设置表的共同基础。 
 * 类	Settings.Secure	安全系统设置，包含系统首选项，应用程序可以读取，但不允许写。 
 * 类	Settings.SettingNotFoundException	 
 * 类	Settings.System	系统设置，包含各种系统偏好。 
 * ---------------------
 * 常量
 * 串	ACTION_ACCESSIBILITY_SETTINGS	活动操作：辅助功能模块的显示设置。
 * 串	ACTION_ADD_ACCOUNT	活动操作：屏幕显示添加帐户创建一个新的帐户。
 * 串	ACTION_AIRPLANE_MODE_SETTINGS	活动操作：显示设置，以允许进入/离开飞机模式。
 * 串	ACTION_APN_SETTINGS	活动操作：显示设置允许您配置的APN。
 * 串	ACTION_APPLICATION_DETAILS_SETTINGS	活动行动：一个特定的应用程序的详细信息的显示屏幕。
 * 串	ACTION_APPLICATION_DEVELOPMENT_SETTINGS	活动操作：显示设置，以允许应用程序开发相关的设置配置。
 * 串	ACTION_APPLICATION_SETTINGS	活动操作：显示设置允许您配置应用程序相关的设置。
 * 串	ACTION_BLUETOOTH_SETTINGS	活动操作：显示设置允许您配置蓝牙。
 * 串	ACTION_DATA_ROAMING_SETTINGS	活动操作：显示设置为2G/3G选择。
 * 串	ACTION_DATE_SETTINGS	活动操作：显示设置允许您配置的日期和时间。
 * 串	ACTION_DEVICE_INFO_SETTINGS	活动操作：显示一般的设备信息设置（序列号，软件版本，电话号码等）。
 * 串	ACTION_DISPLAY_SETTINGS	活动操作：显示设置允许您配置的显示。
 * 串	ACTION_INPUT_METHOD_SETTINGS	活动操作：显示设置来配置，尤其是允许用户启用输入法输入法。
 * 串	ACTION_INPUT_METHOD_SUBTYPE_SETTINGS	活动操作：显示设置启用/禁用输入法亚型。
 * 串	ACTION_INTERNAL_STORAG​​E_SETTINGS	活动操作：显示内部存储的设置。
 * 串	ACTION_LOCALE_SETTINGS	活动操作：显示设置允许您配置语言环境。
 * 串	ACTION_LOCATION_SOURCE_SETTINGS	活动操作：显示设置，以允许配​​置当前的位置来源。
 * 串	ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS	活动操作：显示设置来管理所有的应用程序。
 * 串	ACTION_MANAGE_APPLICATIONS_SETTINGS	活动操作：显示设置来管理安装的应用程序。
 * 串	ACTION_MEMORY_CARD_SETTINGS	活动操作：显示设置的内存卡存储。
 * 串	ACTION_NETWORK_OPERATOR_SETTINGS	活动操作：选择网络运营商的显示设置。
 * 串	ACTION_NFCSHARING_SETTINGS	活动动作：显示的NFC共享设置。
 * 串	ACTION_NFC_SETTINGS	活动操作：显示NFC设置。
 * 串	ACTION_PRIVACY_SETTINGS	活动操作：显示设置，以允许配​​置隐私选项。
 * 串	ACTION_QUICK_LAUNCH_SETTINGS	活动操作：显示设置，以允许配​​置的快速启动快捷键。
 * 串	ACTION_SEARCH_SETTINGS	活动操作：显示设置全局搜索。
 * 串	ACTION_SECURITY_SETTINGS	活动操作：显示设置，以允许配​​置的安全性和位置隐私。
 * 串	ACTION_SETTINGS	活动操作：显示系统设置。
 * 串	ACTION_SOUND_SETTINGS	活动操作：显示设置，以允许配​​置声音和音量。
 * 串	ACTION_SYNC_SETTINGS	活动操作：显示设置，以允许配​​置同步设置。
 * 串	ACTION_USER_DICTIONARY_SETTINGS	活动操作：显示设置来管理用户输入字典。
 * 串	ACTION_WIFI_IP_SETTINGS	活动操作：显示设置允许您配置一个静态IP地址的Wi-Fi。
 * 串	ACTION_WIFI_SETTINGS	活动操作：显示设置，以允许配​​置的Wi-Fi。
 * 串	ACTION_WIRELESS_SETTINGS	活动操作：显示设置允许您配置，例如Wi-Fi，蓝牙和移动网络的无线监控。
 * 串	管理局	 
 * 串	EXTRA_AUTHORITIES	活动额外限制可用的选项给定权限的基础上推出的活动。
 * 串	EXTRA_INPUT_METHOD_ID	 
 * ---------------------
 * 来源：CSDN
 * 原文：https://blog.csdn.net/mp624183768/article/details/76042929
 * desc   :
 * version: 1.0
 */
public class GoSysSetting {
    /**
     * @param activity
     * 语言设置
     */
    public static void startSet(Activity activity, String action) {
        Intent it = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        it.putExtra("extra_prefs_show_button_bar", true);//是否显示button bar
        it.putExtra("extra_prefs_set_next_text", "完成");
        it.putExtra("extra_prefs_set_back_text", "返回");
        //it.putExtra("wifi_enable_next_on_connect", true);
        activity.startActivity(it);
    }
}
