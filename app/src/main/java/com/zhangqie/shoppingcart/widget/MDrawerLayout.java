package com.zhangqie.shoppingcart.widget;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class MDrawerLayout extends DrawerLayout {
    public MDrawerLayout(Context context) {
        super(context);
    }

    public MDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = ev.getX();
                float y = ev.getY();
                View touchesView = findTopChildUnder((int) x, (int) y);
                if (touchesView != null && isContentView(touchesView) && this.isDrawerOpen(GravityCompat.END)) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                return false;
            default:
                break;
        }
        boolean isIntercept = super.onInterceptTouchEvent(ev);
        return isIntercept;
    }

    /**
     * @param x 触摸点X坐标
     * @param y 触摸点Y坐标
     * @return 返回触摸点所在的子View区域
     */
    public ViewGroup findTopChildUnder(int x, int y) {
        final int childCount = this.getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            ViewGroup child = (ViewGroup) this.getChildAt(i);
            if (x >= child.getLeft() && x < child.getRight() && y >= child.getTop() && y < child.getBottom()) {
                return child;
            }
        }
        return null;
    }

    /**
     * @param childView 子View
     * @return 该子View是否是主界面
     */
    public boolean isContentView(View childView) {
        return ((LayoutParams) childView.getLayoutParams()).gravity == Gravity.NO_GRAVITY;
    }
}
