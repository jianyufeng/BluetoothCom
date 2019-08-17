package com.puyu.mobile.base.mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.puyu.mobile.base.R;
import com.puyu.mobile.base.act.BaseActivity;
import com.puyu.mobile.base.mvp.p.IPresenter;
import com.puyu.mobile.base.mvp.v.IBaseView;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/6/17 19:49
 * desc   :
 * version: 1.0
 * * MVP的Activity基类：
 * * 纯粹的 MVP 包装，不要增加任何View层基础功能
 * * 如果要添加基类功能，请在{@link BaseActivity} 中添加
 */
public abstract class BaseMvpActivity<P extends IPresenter> extends BaseActivity implements IBaseView {
    protected P mPresenter;
    private ProgressDialog mProgressDialog;

    protected abstract P createPresenter();


    @Override
    protected void preInit() {
        mPresenter = createPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onMvpStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onMvpResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.onMvpPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onMvpStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onMvpDetachView(false);
            mPresenter.onMvpDestroy();
        }
    }

    @Override
    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErr() {
        showToast(getResources().getString(R.string.api_error_msg));
    }

    @Override
    public Context getContext() {
        return BaseMvpActivity.this;
    }
}
