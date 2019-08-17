package com.puyu.mobile.bluetoothcom;

import android.Manifest;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.puyu.mobile.base.act.BaseActivity;
import com.puyu.mobile.base.util.StringUtil;
import com.puyu.mobile.base.util.ToastUtil;
import com.puyu.mobile.bluetoothcom.bean.ClientLinkError;
import com.puyu.mobile.bluetoothcom.bean.ClientLinkSuccess;
import com.puyu.mobile.bluetoothcom.bean.CommunicateError;
import com.puyu.mobile.bluetoothcom.bean.ReceiveMsgBean;
import com.puyu.mobile.bluetoothcom.bean.ServerFinishBean;
import com.puyu.mobile.bluetoothcom.bean.SeverErrorModelBean;
import com.puyu.mobile.bluetoothcom.bean.SeverStarted;
import com.puyu.mobile.bluetoothcom.server.ChatController;
import com.puyu.mobile.bluetoothcom.util.HexConvert;
import com.puyu.mobile.bluetoothcom.util.ToastyUtils;
import com.puyu.mobile.bluetoothcom.view.MyTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {
    private final Typeface LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL);
    private Typeface currentTypeface = LOADED_TOAST_TYPEFACE;
    //收到的状态指令
    private static final String kaiji_code_return = "AA 01 82 45 BB";
    private static final String guanji_code_return = "AA 02 82 45 BB";
    private static final String sahngya_code_return = "AA 03 82 45 BB"; //
    private static final String sahnya_tanqi_code_return = "AA 00 82 45 BB";
    //发送指令
    private static final String kaiji_code = "AA 01 45 82 BB";
    private static final String guanji_code = "AA 02 45 82 BB";
    private static final String sahngya_code = "AA 03 45 82 BB"; //
    private static final String sahnya_tanqi_code = "AA 00 45 82 BB";
    BluetoothAdapter bluetoothAdapter;
    @BindView(R.id.bt_lianjie)
    Button lianjie;
    @BindView(R.id.toast_root)
    View toastLayout;
    @BindView(R.id.toast_icon)
    ImageView toastIcon;
    @BindView(R.id.toast_text)
    TextView toastTextView;
    @BindView(R.id.msg)
    TextView msg;

    @BindView(R.id.bt_kaiji)
    Button btnKaiJi;
    @BindView(R.id.bt_guanji)
    Button btnGuanJi;
    @BindView(R.id.tv_sahngya)
    MyTextView tvShangYa;
    @BindView(R.id.tv_state_daiji)
    TextView tvStateDaiJi;
    @BindView(R.id.tv_state_gaunji)
    TextView tvStateGuanJi;
    @BindView(R.id.tv_state_sahngya)
    TextView tvStateShangYa;
    @BindView(R.id.tv_state_yunxing)
    TextView tvStateYunXing;
    @BindView(R.id.tv_sahngya_tip)
    TextView tvShangYaTip;
    boolean keyikongzhi = false;
    boolean cancel = false;

    Dialog dialog;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!cancel && keyikongzhi) {
                ChatController.getInstance().sendMessage(HexConvert.hexStringToBytes(sahngya_code));
                tvShangYa.postDelayed(runnable, 1000);
            }

        }
    };

    /**
     * 显示新消息
     *
     * @param newMsg
     */
    public void updateDataView(String newMsg) {
        Drawable d = ContextCompat.getDrawable(mContext, R.mipmap.ok);
        d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
        switch (newMsg.trim()) {
         /*   case kaiji_code:
                String rk = kaiji_code_return.replace(" ", "");
                ChatController.getInstance().sendMessage(HexConvert.hexStringToBytes(rk));
                break;
            case guanji_code:
                String rg = guanji_code_return.replace(" ", "");
                ChatController.getInstance().sendMessage(HexConvert.hexStringToBytes(rg));
                break;
            case sahngya_code:
                String sy = sahngya_code_return.replace(" ", "");
                ChatController.getInstance().sendMessage(HexConvert.hexStringToBytes(sy));
                break;
            case sahnya_tanqi_code:
                String syt = sahnya_tanqi_code_return.replace(" ", "");
                ChatController.getInstance().sendMessage(HexConvert.hexStringToBytes(syt));
                break;*/
            case kaiji_code_return: //运行
                setSheBeiState(true, false, false, false);
                break;
            case guanji_code_return: //关机
                setSheBeiState(false, false, false, true);
                break;
            case sahngya_code_return: //上压
                setSheBeiState(false, true, false, false);
                break;
            case sahnya_tanqi_code_return: //待机
                setSheBeiState(false, false, true, false);
                break;
            default:
                setSheBeiState(false, false, false, false);
                break;

        }
        msg.setText(newMsg);
    }

    public void setSheBeiState(boolean yunxing, boolean shangya, boolean daiji, boolean guanji) {
        Drawable d = ContextCompat.getDrawable(mContext, R.mipmap.ok);
        d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
        tvStateYunXing.setCompoundDrawables(yunxing ? d : null, null, null, null);
        tvStateShangYa.setCompoundDrawables(shangya ? d : null, null, null, null);
        tvStateDaiJi.setCompoundDrawables(daiji ? d : null, null, null, null);
        tvStateGuanJi.setCompoundDrawables(guanji ? d : null, null, null, null);
    }

    @Override
    protected void init() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            ToastUtil.error("系统无蓝牙", true);
            finish();
            return;
        }
        EventBus.getDefault().register(this);
        stateNormal("未连接设备");
        tvShangYa.setDownUpLister(new MyTextView.DownUpLister() {
            @Override
            public void downLister() {
                if (canSend()) {
                    tvShangYaTip.playSoundEffect(SoundEffectConstants.NAVIGATION_DOWN);
                    GradientDrawable bg = (GradientDrawable) tvShangYa.getBackground();
                    bg.setColor(Color.parseColor("#FF6E40"));
                    ChatController.getInstance().sendMessage(HexConvert.hexStringToBytes(sahngya_code));
                    tvShangYaTip.setText("抬起待机");
                    cancel = false;
                    tvShangYa.postDelayed(runnable, 1000);
                }
            }

            @Override
            public void upLister() {
                tvShangYa.removeCallbacks(runnable);
                cancel = true;
                if (canSend()) {
                    tvShangYaTip.playSoundEffect(SoundEffectConstants.NAVIGATION_UP);
                    GradientDrawable bg = (GradientDrawable) tvShangYa.getBackground();
                    bg.setColor(Color.parseColor("#ff00ff"));
                    tvShangYaTip.setText("按下上压");
                    //待机
                    ChatController.getInstance().sendMessage(HexConvert.hexStringToBytes(sahnya_tanqi_code));
                }
            }
        });
        tvStateDaiJi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number++;
                if (number > 10) {
                    msg.setVisibility(View.VISIBLE);
                }else {
                    msg.setVisibility(View.GONE);
                }
            }
        });
    }

    int number;

    public boolean canSend() {
        if (!keyikongzhi) {
            ToastUtil.error("设备未连接", true);
        }
        return keyikongzhi;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkBluetoothAndLocationPermission();
        }
        // 蓝牙未打开，询问打开
        if (!bluetoothAdapter.isEnabled()) {
            Intent turnOnBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOnBtIntent, Params.REQUEST_ENABLE_BT);
        }
      /*  // 蓝牙已开启
        if (bluetoothAdapter.isEnabled()) {
            // 默认开启服务线程监听
            ChatController.getInstance().stopChart();
            ChatController.getInstance().waitingForFriends(bluetoothAdapter, null);//等待客户端来连接
        }*/
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkBluetoothAndLocationPermission() {
        //判断是否有访问位置的权限，没有权限，直接申请位置权限
        if ((ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                || (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, Params.MY_PERMISSION_REQUEST_CONSTANT);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean grantedLocation = true;
        if (requestCode == Params.MY_PERMISSION_REQUEST_CONSTANT) {
            for (int i : grantResults) {
                if (i != PackageManager.PERMISSION_GRANTED) {
                    grantedLocation = false;
                }
            }
        }
        if (!grantedLocation) {
            ToastUtil.error("权限未授权,不能使用", true);
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onServerError(SeverStarted e) { //自己作为服务端 （服务启动等待连接）
        if (e != null && e.code == Params.MSG_Server_start) {
//            ToastUtil.error("服务启动异常", true);
//            keyikongzhi = false;
            setSheBeiState(false, false, false, false);
//            stateSuccess("服务启动", true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onServerError(SeverErrorModelBean e) { //自己作为服务端 （服务启动异常）
        if (e != null && e.code == Params.MSG_Server_ERROR) {
//            ToastUtil.error("服务启动异常", true);
            keyikongzhi = false;
            setSheBeiState(false, false, false, false);
            stateError("服务启动异常", true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onServerError(ServerFinishBean e) { // （自己作为服务端）服务断开
        if (e != null && e.code == Params.MSG_Server_finish) {
//            ToastUtil.error("服务断开", true);
            keyikongzhi = false;
            setSheBeiState(false, false, false, false);
            stateError("服务断开", true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receivemsgbean(ReceiveMsgBean e) {  //收到新消息（1、客户端发来；2服务端发来）
        if (e != null && e.code == Params.Receive_msg) {
//            ToastUtil.success(e.msg, true);
            updateDataView(e.msg);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void clientlinkerror(ClientLinkError e) { //自己作为客户端  去连接服务端
        if (e != null && e.code == Params.click_link_error) {
            ToastUtil.error("连接失败,设备未打开", true);
            stateError("连接失败,设备未打开", true);
            keyikongzhi = false;
            setSheBeiState(false, false, false, false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void clientlinksuccess(ClientLinkSuccess e) {  //（客户 和 服务）1、客户端连接服务端成功  / 2、服务端等待连接客户端成功
        if (e != null && e.code == Params.click_link_success) {
            ToastUtil.success("连接成功", true);
            if (dialog != null) dialog.dismiss();
            keyikongzhi = true;
            if (e.device != null) {
                stateSuccess(getString(R.string.state_success,
                        StringUtil.isEmpty(e.device.getName()) ?
                                e.device.getAddress() : e.device.getName()), true);

            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void communicateerror(CommunicateError e) { //已经连接设备 但是交流中异常断开（获取、读、写流失败,）（1、自己断开，2、服务器断开）
        if (e != null && e.code == Params.communicate_link_error) {
            ToastUtil.error("连接被断开", true);
            stateError("连接被断开", true);
            keyikongzhi = false;
            setSheBeiState(false, false, false, false);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Params.REQUEST_ENABLE_BT: {
                if (resultCode == RESULT_OK) {
                   ToastUtil.normal("请先连接设备");
                }
                break;
            }
        }
    }
    @OnClick({R.id.bt_lianjie, R.id.bt_duankai, R.id.bt_kaiji, R.id.bt_guanji})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt_lianjie:
                // 蓝牙未打开，询问打开
                if (!bluetoothAdapter.isEnabled()) {
                    Intent turnOnBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnOnBtIntent, Params.REQUEST_ENABLE_BT);
                    return;
                }
                dialog = new BlueDialog(this, "", "", null, null);
                dialog.show();
                break;
            case R.id.bt_duankai:
                ChatController.getInstance().stopChart();
                break;
            case R.id.bt_kaiji:
                tvShangYa.removeCallbacks(runnable);
                cancel = true;
                if (canSend()) {
                    ChatController.getInstance().sendMessage(HexConvert.hexStringToBytes(kaiji_code));
                }
                break;
            case R.id.bt_guanji:
                tvShangYa.removeCallbacks(runnable);
                cancel = true;
                if (canSend()) {
                    ChatController.getInstance().sendMessage(HexConvert.hexStringToBytes(guanji_code));
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (tvShangYa != null) {
            tvShangYa.removeCallbacks(runnable);
        }
        cancel = true;
    }


    public void setState(@NonNull Context context, @NonNull CharSequence message, Drawable icon,
                         @ColorInt int tintColor, @ColorInt int textColor,
                         boolean withIcon, boolean shouldTint) {
        ToastyUtils.getDrawable(context, R.drawable.ic_check_white_24dp);
        Drawable drawableFrame;
        if (shouldTint)
            drawableFrame = ToastyUtils.tint9PatchDrawableFrame(context, tintColor);
        else
            drawableFrame = ToastyUtils.getDrawable(context, R.drawable.toast_frame);
        ToastyUtils.setBackground(toastLayout, drawableFrame);

        if (withIcon) {
            ToastyUtils.setBackground(toastIcon, true ? ToastyUtils.tintIcon(icon, textColor) : icon);
        } else {
            toastIcon.setVisibility(View.GONE);
        }
        toastTextView.setText(message);
        toastTextView.setTextColor(textColor);
        toastTextView.setTypeface(currentTypeface);
        toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

    }

    /***弹出成功消息**@param text 需要显示的消息*@param isShowIcon是否需要显示图标 默认显示* */
    public void stateSuccess(@NonNull String message, Boolean isShowIcon) {
        setState(mContext, message, ToastyUtils.getDrawable(mContext, R.drawable.ic_check_white_24dp),
                ToastyUtils.getColor(mContext, R.color.successColor), ToastyUtils.getColor(mContext, R.color.defaultTextColor),
                isShowIcon, true);

    }

    /***弹出错误消息*@param text 需要显示的消息  *@param isShowIcon 是否需要显示图标 默认显示* */
    public void stateError(@NonNull String message, Boolean withIcon) {
        setState(mContext, message, ToastyUtils.getDrawable(mContext, R.drawable.ic_clear_white_24dp),
                ToastyUtils.getColor(mContext, R.color.errorColor), ToastyUtils.getColor(mContext, R.color.defaultTextColor),
                withIcon, true);
    }

    /***弹出一般消息*@param text 需要显示的消息* */
    public void stateNormal(@NonNull String message) {
        setState(mContext, message, null, ToastyUtils.getColor(mContext, R.color.normalColor),
                ToastyUtils.getColor(mContext, R.color.defaultTextColor),
                false, true);
    }

}
