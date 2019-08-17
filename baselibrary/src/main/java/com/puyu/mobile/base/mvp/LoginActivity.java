package com.puyu.mobile.base.mvp;

import com.puyu.mobile.base.mvp.Contract.LoginContract;
import com.puyu.mobile.base.mvp.p.LoginPresenterImpl;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/6/17 20:03
 * desc   :
 * version: 1.0
 */
public class LoginActivity extends BaseMvpActivity<LoginContract.ILoginPresenter> implements LoginContract.ILoginView {
    @Override
    protected LoginContract.ILoginPresenter createPresenter() {

        return new LoginPresenterImpl(this);
    }

    @Override
    protected void init() {
        mPresenter.login();
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }


    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void onLoginSeccess() {

    }

    @Override
    public void onLoginFails() {

    }

    @Override
    public void showData(String data) {

    }
}
