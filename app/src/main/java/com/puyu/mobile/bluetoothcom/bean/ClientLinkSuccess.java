package com.puyu.mobile.bluetoothcom.bean;

import android.bluetooth.BluetoothDevice;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/8/16 18:40
 * desc   :
 * version: 1.0
 */
public class ClientLinkSuccess {
    public String code;
    public BluetoothDevice device;
    public ClientLinkSuccess(String code, BluetoothDevice device) {
        this.code = code;
        this.device = device;
    }
}
