package com.puyu.mobile.bluetoothcom.server;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

import com.puyu.mobile.bluetoothcom.Params;
import com.puyu.mobile.bluetoothcom.bean.ClientLinkError;
import com.puyu.mobile.bluetoothcom.bean.ClientLinkSuccess;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.UUID;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/8/11 11:17
 * desc   :
 * version: 1.0
 */
public class ClientThread extends Thread {
    private static final UUID MY_UUID = UUID.fromString(Params.UUID);
    /**
     * 客户端socket
     */
    private final BluetoothSocket mmSoket;
    /**
     * 要连接的设备
     */
    private final BluetoothDevice mmDevice;
    private BluetoothAdapter mBluetoothAdapter;
    /**
     * 主线程通信的Handler
     */
    private final Handler mHandler;
    /**
     * 发送和接收数据的处理类
     */
    private ConnectedThread mConnectedThread;

    public ClientThread(BluetoothDevice device, BluetoothAdapter bluetoothAdapter, Handler mUIhandler) {
        mmDevice = device;
        mBluetoothAdapter = bluetoothAdapter;
        mHandler = mUIhandler;

        BluetoothSocket tmp = null;
        try {
            // 创建客户端Socket
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            e.printStackTrace();
            EventBus.getDefault().post(new ClientLinkError(Params.click_link_error));
        }
        mmSoket = tmp;
    }

    @Override
    public void run() {
        super.run();
        // 关闭正在发现设备.(如果此时又在查找设备，又在发送数据，会有冲突，影响传输效率)
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        try {
            // 连接服务器
            mmSoket.connect();
        } catch (IOException e) {
            // 连接异常就关闭
            e.printStackTrace();
            EventBus.getDefault().post(new ClientLinkError(Params.click_link_error));
            try {
                mmSoket.close();
            } catch (IOException e1) {
                e.printStackTrace();
            }
            return;
        }

        EventBus.getDefault().post(new ClientLinkSuccess(Params.click_link_success,mmDevice));
        manageConnectedSocket(mmSoket);
    }

    private void manageConnectedSocket(BluetoothSocket mmSoket) {

        // 新建一个线程进行通讯,不然会发现线程堵塞
        mConnectedThread = new ConnectedThread(mmSoket, mHandler);
        mConnectedThread.start();
    }

    /**
     * 关闭当前客户端
     */
    public void cancle() {
        try {
            mmSoket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送数据
     *
     * @param data
     */
    public void sendData(byte[] data) {
        if (mConnectedThread != null) {
            mConnectedThread.write(data);
        }
    }

}
