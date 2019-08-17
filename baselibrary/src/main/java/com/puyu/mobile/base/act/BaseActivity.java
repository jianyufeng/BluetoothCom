package com.puyu.mobile.base.act;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.puyu.mobile.base.util.DialogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/5/23 15:14
 * desc   :
 * version: 1.0
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    protected Context mContext;
    private Unbinder bind;
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        mContext = this;
        preInit();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        //移除dialog
        if (mDialog != null) {
            dismissProgress();
        }
    }

    protected abstract void init();

    protected void preInit() {
    }


    protected abstract @LayoutRes
    int getLayoutId();

    /**
     * 显示 进度框
     *
     * @param text
     * @param mCancelable
     */
    protected void showProgress(String text, boolean mCancelable) {
        mDialog = DialogUtil.getInstance().showProgressDialog(this, text, mCancelable);
        mDialog.show();
    }

    /**
     * 隐藏进度框
     */
    protected void dismissProgress() {
        if (mDialog != null && mDialog.isShowing()) {
            DialogUtil.getInstance().dismissDialog(mDialog);
        }
    }

}
