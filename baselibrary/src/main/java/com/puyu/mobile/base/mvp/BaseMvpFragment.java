package com.puyu.mobile.base.mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.puyu.mobile.base.R;
import com.puyu.mobile.base.frag.BaseLazyFragment;
import com.puyu.mobile.base.mvp.p.IPresenter;
import com.puyu.mobile.base.mvp.v.IBaseView;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/6/20 1:09
 * desc   :
 * version: 1.0
 */
public abstract class BaseMvpFragment<P extends IPresenter> extends BaseLazyFragment implements IBaseView {
    protected P mPresenter;
    private ProgressDialog mProgressDialog;

    protected abstract P createPresenter();

    @Override
    protected void preInitData() {
        mPresenter = createPresenter();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onMvpStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onMvpResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.onMvpPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onMvpStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onMvpDetachView(false);
            mPresenter.onMvpDestroy();
        }
    }
    @Override
    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
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
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErr() {
        showToast(getResources().getString(R.string.api_error_msg));
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
