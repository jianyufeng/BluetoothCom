package com.puyu.mobile.base.mvp.m;

import com.puyu.mobile.base.mvp.Contract.LoginContract;

import java.util.ArrayList;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/6/19 14:16
 * desc   :负责的是数据以及业务逻辑
 * version: 1.0
 */
public class ModelLogin implements LoginContract.ILoginModel {


    @Override
    public ArrayList<String> getData(String name, String psw) {
        return new ArrayList<>();
    }
}
