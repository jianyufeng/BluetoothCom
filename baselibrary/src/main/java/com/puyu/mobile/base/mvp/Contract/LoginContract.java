package com.puyu.mobile.base.mvp.Contract;

import com.puyu.mobile.base.mvp.p.IPresenter;
import com.puyu.mobile.base.mvp.v.IBaseView;

import java.util.ArrayList;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/6/17 19:56
 * desc   : * 契约接口类：
 * * P层与 V层接口定义
 * version: 1.0
 */
public class LoginContract {
    public interface ILoginModel {
        ArrayList<String> getData(String name, String psw);
    }

    public interface ILoginView extends IBaseView {
        String getUserName();

        String getPassword();

        void onLoginSeccess();

        void onLoginFails();

        /**
         * 当数据请求成功后，调用此接口显示数据
         *
         * @param data 数据源
         */
        void showData(String data);
    }

    public interface ILoginPresenter extends IPresenter<ILoginView> {
        /**
         * 登录
         */
        void login();
    }
}
