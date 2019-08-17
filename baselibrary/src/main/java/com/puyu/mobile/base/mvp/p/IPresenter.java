package com.puyu.mobile.base.mvp.p;

import com.puyu.mobile.base.mvp.v.IBaseView;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/6/17 19:44
 * desc   :  * 控制器接口：
 * desc   : * 定义P层生命周期与 V层同步
 * version: 1.0
 */
public interface IPresenter<V extends IBaseView> {
    void onMvpAttachView(V view);

    void onMvpStart();

    void onMvpResume();

    void onMvpPause();

    void onMvpStop();

    void onMvpDetachView(boolean retainInstance);

    void onMvpDestroy();
}
