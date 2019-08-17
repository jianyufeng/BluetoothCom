package com.puyu.mobile.base.view;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.puyu.mobile.base.util.ScreenUtil;

/**
 * author : 简玉锋
 * e-mail : yufeng_jian@fpi-inc.com
 * date   : 2019/5/29 10:30
 * desc   : 边沿事件处理
 * version: 1.0
 */
public abstract class EdgeSlideGesture {
    Context context;
    //屏幕宽高
    int sWidth = ScreenUtil.getWidth();
    int sHeight = ScreenUtil.getHeight();
    //按下的点
    PointF down;
    //记录滑动过程中 Y轴滑动的区间
    float minY, maxY;
    //记录滑动过程中 X轴滑动的区间
    float minX, maxX;
    //按下时的时间
    long downTime;
    //边缘判定距离，
    float marginLR = sWidth * 0.035f;
    //边缘判定距离，
    float marginTB =3* ScreenUtil.getStatusBarHeight();
    float marginB = ScreenUtil.getStatusBarHeight();
    //Y轴最大区间范围，即Y轴滑动超出这个范围不触发事件  左右边沿
    float maxYDis = sHeight * 0.2f;
    //X轴最大区间范围，即X轴滑动超出这个范围不触发事件 上下边沿
    float maxXDis = sWidth * 0.2f;
    //X轴最短滑动距离 X轴滑动范围低于此值不触发事件 左右边沿
    float minXDis = sWidth * 0.2f;
    //Y轴最短滑动距离 Y轴滑动范围低于此值不触发事件  上下边沿
    float minYDis = sHeight * 0.1f;
    //是否处于此次滑动事件
    boolean work = false;
    //是否是 上下 边缘
    boolean isTB = false;

    //使否允许滑动的方向
    boolean topEdge;
    boolean bottomEdge;
    boolean leftEdge;
    boolean rightEdge;
    int touchSlop; //最小滚动距离

    public EdgeSlideGesture(Context context, boolean left, boolean right, boolean top, boolean bottom) {
        this.context = context;
        this.topEdge = top;
        this.bottomEdge = bottom;
        this.leftEdge = left;
        this.rightEdge = right;
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public boolean doEventFling(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                PointF down = new PointF(event.getX(), event.getY());;
                //判定是否处于边缘侧滑
                if (down.y < marginTB && topEdge) { //上边沿
                    work = true;
                    isTB = true;
                } else if ((sHeight - down.y) < marginB && bottomEdge) { //下边沿
                    work = true;
                    isTB = true;
                } else if (down.x < marginLR && leftEdge) { //左边沿
                    //左右边沿
                    work = true;
                } else if ((sWidth - down.x) < marginLR && rightEdge) { //右边沿
                    //左右边沿
                    work = true;
                }
                if (work) {
                    //记录下按下的点
                    this.down = down;
                    downTime = System.currentTimeMillis();
                    //初始化最大最小值
                    minY = maxY = down.y;
                    minX = maxX = down.x;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (work) {
                    if (isTB) { //上下边缘   获取左右移动 最大最小值
                        if (event.getX() > this.down.x) {
                            maxX = event.getX();
                        } else {
                            minX = event.getX();
                        }
                    } else { // 左右边缘  获取上下移动的 最大最小值
                        if (event.getY() > this.down.y) {
                            maxY = event.getY();
                        } else {
                            minY = event.getY();
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (work) {
                    //处理
                    handle(new PointF(event.getX(), event.getY()));
                }
                work = false;
                isTB = false;
                break;
        }
        //如果当前处于边缘滑动判定过程中，则消费掉此事件不往下传递。
        return false;
    }

    public boolean handle(PointF up) {
        long upTime = System.currentTimeMillis();
        if (isTB) {
            float tHight = Math.abs(down.y - up.y);//最终 按下 和 抬起 的 Y方向的距离
            //上下边沿  在最大滑动的X方向的内，符合超出滑动Y最小范围， 时间差小于 小于 高度 范围的2.5倍
            if (maxX - minX < maxXDis && tHight > minYDis && (upTime - downTime) / tHight < 2.5) {
                //起点在上边  且监听 上边沿滑动
                if (down.y < marginTB && topEdge) {
                    topEdge();
                    return true;
                }
                //起点在下边 且监听 下边沿滑动
                if ((sHeight - down.y) < marginB && bottomEdge) {
                    bottomEdge();
                    return true;
                }
            }
        } else {
            float tWidth = Math.abs(down.x - up.x);//最终 按下 和 抬起 的 X方向的距离
            //左右边沿  在最大滑动的Y方向的内，符合超出滑动X最小范围， 时间差小于 小于宽度范围的2.5倍
            if (maxY - minY < maxYDis && tWidth > minXDis && (upTime - downTime) / tWidth < 2.5) {
                //起点在左边  且监听 左边沿滑动
                if (down.x < marginLR && leftEdge) {
                    leftEdge();
                    return true;
                }
                //起点在右边  且监听 有边沿 滑动
                if ((sWidth - down.x) < marginLR && rightEdge) {
                    rightEdge();
                    return true;
                }
            }
        }
        return false;
    }


    public void topEdge() {
    }

    public void bottomEdge() {
    }

    public void leftEdge() {
    }


    public void rightEdge() {
    }
}
