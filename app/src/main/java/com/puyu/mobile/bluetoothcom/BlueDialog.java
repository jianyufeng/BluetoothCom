package com.puyu.mobile.bluetoothcom;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyu.mobile.base.util.ScreenUtil;
import com.puyu.mobile.base.util.ToastUtil;
import com.puyu.mobile.bluetoothcom.server.ChatController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by 16896 on 2018/3/12.
 * 确定创建事件弹框
 */

public class BlueDialog extends Dialog {


    private final MyBtReceiver btReceiver;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.bt_other)
    Button other;
    @BindView(R.id.recyclerView)
    RecyclerView mRecycleView;
    List<BluetoothDevice> deviceList = new ArrayList<>();
    BluetoothAdapter bluetoothAdapter;
    Context context;
    private BaseQuickAdapter<BluetoothDevice, BaseViewHolder> adapter;

    public BlueDialog(@NonNull Context context, String title, String content,
                      View.OnClickListener confirmListener, View.OnClickListener choseMemberListener) {
        super(context);
        this.setCanceledOnTouchOutside(true);
        setCancelable(true);
        this.context = context;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set data = bluetoothAdapter.getBondedDevices();
        deviceList.clear();
        Set<BluetoothDevice> tmp = bluetoothAdapter.getBondedDevices();
        deviceList.addAll(tmp);
        btReceiver = new MyBtReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        context.registerReceiver(btReceiver, intentFilter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_chose_blue);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mRecycleView.setLayoutManager(new LinearLayoutManager(context));
        mRecycleView.addItemDecoration(new DividerItemDecoration(context, LinearLayout.VERTICAL));
        ViewGroup.LayoutParams lp = mRecycleView.getLayoutParams();
        lp.height = (int) (ScreenUtil.getHeight() * 0.5);
        mRecycleView.setLayoutParams(lp);
        adapter = new BaseQuickAdapter<BluetoothDevice, BaseViewHolder>(R.layout.layout_item_bt_device) {
            @Override
            protected void convert(BaseViewHolder helper, BluetoothDevice item) {
                int code = item.getBondState();
                String name = item.getName();
                String mac = item.getAddress();
                String state;
                if (name == null || name.length() == 0) {
                    name = "未命名设备";
                }
                if (code == BluetoothDevice.BOND_BONDED) {
                    state = "已配对设备";
                    helper.setTextColor(R.id.device_state, ContextCompat.getColor(context, R.color.green));
                } else {
                    state = "可用设备";
                    helper.setTextColor(R.id.device_state, ContextCompat.getColor(context, R.color.red));
                }
                if (mac == null || mac.length() == 0) {
                    mac = "未知 mac 地址";
                }
                helper.setText(R.id.device_name, name);
                helper.setText(R.id.device_mac, mac);
                helper.setText(R.id.device_state, state);
            }
        };
        mRecycleView.setAdapter(adapter);
        adapter.setNewData(deviceList);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter a, View view, int position) {
                BluetoothDevice device = adapter.getData().get(position);
                ChatController.getInstance().stopChart();//停止服务器
                peidui(device); //配对
            }
        });
    }

    private void peidui(BluetoothDevice device) {
        if (device.getBondState() == BluetoothDevice.BOND_NONE) {  //未配对，就先配对，再连接
            Method m = null;
            try {
                m = BluetoothDevice.class.getMethod("createBond");
                m.invoke(device);
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtil.error("无法绑定", true);
                return;
            }
        } else if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
            lianjie(device);
        } else if (device.getBondState() == BluetoothDevice.BOND_BONDING) {
            ToastUtil.normal("正在配对");
        }
    }

    private void lianjie(BluetoothDevice device) {
        // 开启客户端线程，连接点击的远程设备
        ChatController.getInstance().startChatWith(device, bluetoothAdapter, null);
    }

    @OnClick(R.id.bt_other)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt_other:
                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                }
                bluetoothAdapter.startDiscovery();
                break;
        }
    }

    /**
     * 广播接受器
     */
    private class MyBtReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
//                ToastUtil.normal("开始搜索 ...");
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
//                ToastUtil.normal("搜索结束");
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (!deviceList.contains(device)) {
                    deviceList.add(device);
                    adapter.notifyDataSetChanged();
                }
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String name = device.getName();
                Log.d("aaa", "device name: " + name);
                int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, -1);
                switch (state) {
                    case BluetoothDevice.BOND_NONE:
                        Log.d("aaa", "BOND_NONE 删除配对");
                        ToastUtil.normal("配对失败");
//                        dismissProgress();
                        break;
                    case BluetoothDevice.BOND_BONDING:
                        Log.d("aaa", "BOND_BONDING 正在配对");
                        ToastUtil.normal("正在配对");
                        break;
                    case BluetoothDevice.BOND_BONDED:
                        Log.d("aaa", "BOND_BONDED 配对成功");
                        ToastUtil.normal("配对成功");
                        lianjie(device);
                        break;
                }
            }
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
    }
}
