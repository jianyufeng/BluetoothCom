package com.puyu.mobile.base.mvp.v;

import android.content.Context;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/6/17 19:43
 * desc   :  Activity /fragment 来实现，
 * desc   :  基类MvpActivity/ MvpFragment 已经实现
 * version: 1.0
 */
public interface IBaseView {

    /**
     * 显示加载中
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 数据获取失败
     *
     * @param throwable
     */
    void onError(Throwable throwable);

    /**
     * 显示提示
     *
     * @param msg
     */
    void showToast(String msg);

    /**
     * 显示请求错误提示
     */
    void showErr();

    /**
     * 获取上下文
     *
     * @return 上下文
     */
    Context getContext();
}
