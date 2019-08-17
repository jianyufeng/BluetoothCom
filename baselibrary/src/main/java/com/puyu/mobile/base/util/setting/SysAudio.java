package com.puyu.mobile.base.util.setting;

import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/6/4 10:59
 * desc   : View 音效使能  系统静音
 * version: 1.0
 */
public class SysAudio {

    //普通View的点击音效是否使能
    public static boolean getViewClicEnable(Context context) {
        int b = 0;
        try {
            b = Settings.System.getInt(context.getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED); // 点击音效
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return b == 1;
    }

    //设置普通View的点击音效是否使能
    public static boolean getViewClicEnable(Context context, boolean enable) {
        boolean b = Settings.System.putInt(context.getContentResolver(),
                Settings.System.SOUND_EFFECTS_ENABLED, enable ? 1 : 0);
        return b;
    }


    /**
     * 设置系统静音
     * 静音和振动两个中有一个发生变化时就需调用setRingerMode对状态进行设置，如下几种：
     * RINGER_MODE_SILENT 静音,且无振动
     * RINGER_MODE_VIBRATE 静音,但有振动
     * RINGER_MODE_NORMAL 正常声音,振动开关由setVibrateSetting决定.
     * 权限：ACCESS_NOTIFICATION_POLICY
     * NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
     * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
     * && !notificationManager.isNotificationPolicyAccessGranted()) {
     * Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
     * context.startActivity(intent);
     * return;
     * }
     *
     * @param context
     * @param enable
     */
    public static void setSysSilent(Context context, boolean enable) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (enable) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        } else {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
        audioManager.getStreamVolume(AudioManager.STREAM_RING);
    }

    /**
     * 调节（加减） 获取 对应类型下的音量
     *
     * @param streamType ：要调整的音频流类型。类型有以下几种：STREAM_VOICE_CALL（电话的音频流），STREAM_SYSTEM（系统声音的音频流），
     *                   STREAM_RING（电话铃声的音频流），STREAM_MUSIC（用于音乐播放的音频流）
     *                   STREAM_ALARM（警报的音频流）区分流类型的目的是让用户能够单独地控制不同的种类的音频
     *                   。但上述音频种类中，大多数都是被系统限制
     *                   。除非应用需要做替换闹钟的铃声的操作，不然的话你只能通过STREAM_MUSIC来播放你的音频。
     *                   也就是说我们最常见操作的就是STREAM_MUSIC这个类型。
     * @param direction  direction ：调整音量的方向。其中： ADJUST_LOWER(减少铃声音量)，
     *                   ADJUST_RAISE(增加铃声音量)或 ADJUST_SAME(保持之前的铃声音量)
     * @param flags      flags ：一个或多个标志。可能这里的标志不是很好理解，是这样，AudioManager提供了一些常量，
     *                   我们可以将这些系统已经准备好的常量设置为这里的flags，比如：
     *                   FLAG_ALLOW_RINGER_MODES（更改音量时是否包括振铃模式作为可能的选项），
     *                   FLAG_PLAY_SOUND（是否在改变音量时播放声音），
     *                   FLAG_REMOVE_SOUND_AND_VIBRATE（删除可能在队列中或正在播放的任何声音/振动（与更改音量有关）），
     *                   FLAG_SHOW_UI（显示包含当前音量的吐司），FLAG_VIBRATE（是否进入振动振铃模式时是否振动）
     *                   <p>
     *                   使用：audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
     */

    public static void adjustStreamVolume(Context context, int streamType, int direction, int flags) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.adjustStreamVolume(streamType, direction, flags);
    }

    //获取当前类型下的音量
    public static void getStreamVolume(Context context, int streamType) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.getStreamVolume(streamType);
    }

    //卸载当前按下的声音
    public static void unloadSoundEffects(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.unloadSoundEffects();
    }


}
