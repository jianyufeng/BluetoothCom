package com.puyu.mobile.base.mvp.p;

import com.puyu.mobile.base.mvp.v.IBaseView;

import java.lang.ref.WeakReference;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/6/17 19:45
 * desc   : 基类 BasePresenter  使用仿照
 * {@link com.puyu.mobile.base.mvp.p.LoginPresenterImpl}
 * version: 1.0
 */
public class BasePresenter<V extends IBaseView> implements IPresenter<V> {
    private WeakReference<V> viewRef;

    BasePresenter(V mView) {
        onMvpAttachView(mView);
    }

    @Override
    public void onMvpAttachView(V mView) {
        viewRef = new WeakReference<V>(mView);
    }

    protected V getView() {
        return viewRef.get();
    }

    protected boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    @Override
    public void onMvpStart() {

    }

    @Override
    public void onMvpResume() {

    }

    @Override
    public void onMvpPause() {

    }

    @Override
    public void onMvpStop() {

    }

    @Override
    public void onMvpDetachView(boolean retainInstance) {
        if (!retainInstance && viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    @Override
    public void onMvpDestroy() {

    }
}
