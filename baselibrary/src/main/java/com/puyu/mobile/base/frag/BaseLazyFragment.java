package com.puyu.mobile.base.frag;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.puyu.mobile.base.util.DialogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/5/23 21:18
 * desc   : 基类Fragment  实现了懒加载（需要setUserVisibleHint（true））
 * version: 1.0
 */
public abstract class BaseLazyFragment extends Fragment {
    protected Context mContext;
    private View mView;
    //下面两个主要是懒加载使用
    private boolean isViewCreated; //view 是否创建
    private boolean isInitData;  //是否已经调用 InitData
    private boolean isViewHidden;
    private Unbinder mUnBinder;
    private Dialog mDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        isViewCreated = true;
        if (mView == null) {
            mView = inflater.inflate(getLayoutId(), container, false);
        } else {  //防止多次添加 view
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (null != parent) {
                parent.removeView(mView);
            }
        }
        mUnBinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {  //如果fragment可见
            if (!isInitData) {
                isInitData = true;
                preInitData();
                initData();
            }
            repeatVisible();
        } else {   //不可见
            if (!lazy()) {  // 不懒加载， 主要是看不可见的fragment是调用初始化方法等
                if (!isInitData) {
                    isInitData = true;
                    preInitData();
                    initData();
                }
                repeatVisible();
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {  //可见
            if (isViewCreated) { // view创建重新绑定，
                if (!isInitData) {  // 没有调用initData（调用一次初始化）
                    isInitData = true;
                    preInitData();
                    initData();
                }
                repeatVisible();//重复
            }
        } else {  //不可见
            if (isViewCreated) {  //View没有销毁
                if (!isViewHidden) {
                    onViewHidden();
                }
            }
        }
    }

    protected void onViewHidden() {
        isViewHidden = true;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        //移除dialog
        if (mDialog != null) {
            dismissProgress();
        }
    }

    protected boolean lazy() { /*是否使用懒加载 不使用则会在第一次创建视图的时候依次调用
     onActivityCreated（）中的方法： preInitData(); initData();  repeatVisible();*/
        return true;
    }

    protected abstract void initData();


    protected void preInitData() {
    }

    //懒加载的重新可见
    protected void repeatVisible() {
        isViewHidden = false;
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
        mDialog = DialogUtil.getInstance().showProgressDialog(mContext, text, mCancelable);
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
