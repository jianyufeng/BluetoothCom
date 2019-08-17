package com.puyu.mobile.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/5/29 14:59
 * desc   :
 * version: 1.0
 */
public class ScrollLinearLayout extends RelativeLayout {

    /**
     * 滑动监听
     */
    private OnScrollListener onScrollListener;

    public ScrollLinearLayout(Context context) {
        super(context);
    }

    public ScrollLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * scroll in from right
     */
    public void beginScrollFromRight() {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                0, Animation.RELATIVE_TO_SELF,
                -1, Animation.RELATIVE_TO_SELF,
                0, Animation.RELATIVE_TO_SELF,
                0);

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
                params.rightMargin = 0;
                setLayoutParams(params);
            }
        });
        translateAnimation.setDuration(300);
        startAnimation(translateAnimation);
    }

    /**
     * scroll out hide right
     */
    public void beginScrollHideRight() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
                params.rightMargin = -getWidth();
                setLayoutParams(params);
            }
        });
        translateAnimation.setDuration(300);
        startAnimation(translateAnimation);
    }


    /**
     * scroll in from left
     */
    public void beginScrollFromLeft() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
                params.leftMargin = 0;
                setLayoutParams(params);
            }
        });
        translateAnimation.setDuration(300);
        startAnimation(translateAnimation);
    }

    /**
     * scroll out hide left
     */
    public void beginScrollHideLeft() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
                params.leftMargin = -getWidth();
                setLayoutParams(params);
            }
        });
        translateAnimation.setDuration(300);
        startAnimation(translateAnimation);
    }

    /**
     * scroll in from bottom
     */
    public void beginScrollFromBottom() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1);

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
                params.bottomMargin = 0;
                setLayoutParams(params);
            }
        });
        translateAnimation.setDuration(300);
        startAnimation(translateAnimation);
    }

    /**
     * scroll out hide bottom
     */
    public void beginScrollHideBottom() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
                params.bottomMargin = -getHeight();
                setLayoutParams(params);
            }
        });
        translateAnimation.setDuration(300);
        startAnimation(translateAnimation);
    }

    /**
     * scroll in from bottom
     */
    public void beginScrollFromTop() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
                params.topMargin = 0;
                setLayoutParams(params);
            }
        });
        translateAnimation.setDuration(300);
        startAnimation(translateAnimation);
    }

    /**
     * scroll out hide top
     */
    public void beginScrollHideTop() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1);

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
                params.topMargin = -getHeight();
                setLayoutParams(params);
            }
        });
        translateAnimation.setDuration(300);
        startAnimation(translateAnimation);
    }

    @Override
    public void computeScroll() {
        // TODO Auto-generated method stub
        super.computeScroll();
        if (onScrollListener != null) {
            onScrollListener.computeScroll();
        }
    }

    public OnScrollListener getOnScrollListener() {
        return onScrollListener;
    }

    /**
     * 滑动监听
     */
    public void setOnScrollListener(OnScrollListener listener) {
        this.onScrollListener = listener;
    }

    /**
     * 滑动监听
     *
     * @author LangK
     */
    public interface OnScrollListener {
        public void computeScroll();
    }
}
