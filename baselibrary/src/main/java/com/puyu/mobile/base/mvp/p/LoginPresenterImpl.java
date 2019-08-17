package com.puyu.mobile.base.mvp.p;

import com.puyu.mobile.base.mvp.Contract.LoginContract;
import com.puyu.mobile.base.mvp.m.ModelLogin;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/6/17 19:59
 * desc   :   例子 如：登录
 * version: 1.0
 */
public class LoginPresenterImpl extends BasePresenter<LoginContract.ILoginView>
        implements LoginContract.ILoginPresenter {

    private LoginContract.ILoginModel iLoginModel;



    public LoginPresenterImpl(LoginContract.ILoginView mView) {
        super(mView);
        iLoginModel = new ModelLogin();
    }


    @Override
    public void login() {
        if (isViewAttached()) {
            handleLogin();
        }
    }

    private void handleLogin() {
        LoginContract.ILoginView view = getView();
        String username = view.getUserName();
        String password = view.getPassword();
        iLoginModel.getData(username, password);
    }

    /**
     * 重写P层需要的生命周期，进行相关逻辑操作
     */
    @Override
    public void onMvpResume() {
        super.onMvpResume();
    }
}
