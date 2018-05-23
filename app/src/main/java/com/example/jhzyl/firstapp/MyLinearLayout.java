package com.example.jhzyl.firstapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MyLinearLayout extends LinearLayout {
    private ViewDragHelper mViewDragHelper;

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mViewDragHelper = ViewDragHelper.create(this, 3f, new ViewDragCallBack());
    }

    /**
     * 是否拦截子view的触摸事件
     */
    private boolean intercept = true;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return mViewDragHelper.shouldInterceptTouchEvent(ev);
        if (!intercept) {
            return false;
        } else {
            return true;
        }
    }

    private ImageView ll_header;
    private View rg_Tab;
    private ViewPager pager;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ll_header = (ImageView) findViewById(R.id.iv_dash_board_title);

        rg_Tab = findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
    }

    private class ViewDragCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child.getId() == pager.getId();
        }

        /**
         * 处理水平方向上的拖动
         *
         * @param child 拖动的View
         * @param left  移动到x轴的距离
         * @param dx    建议的移动的x距离
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //两个if主要是让view在ViewGroup中
            if (left < getPaddingLeft()) {
                return getPaddingLeft();
            }

            if (left > getWidth() - child.getMeasuredWidth()) {
                return getWidth() - child.getMeasuredWidth();
            }
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
//            int header_h = ll_header.getMeasuredHeight();
//            int tabs_h = rg_Tab.getMeasuredHeight();
//
//            if (child.getId() == pager.getId()) {//如果拖拽的是内容
//                if (top < header_h) {
//                    return header_h;
//                } else if (top > (header_h + tabs_h)) {
//                    return (header_h + tabs_h);
//                }
//            }
            return top;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);

            if (changedView.getId() == pager.getId()) {
//                ll_header.offsetTopAndBottom(dy);
//                rg_Tab.offsetTopAndBottom(dy);
                pager.offsetTopAndBottom(dy);
            }

        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
//           if (releasedChild.getId() == pager.getId()) {
//                if (rg_Tab.getTranslationY() != 0) {
//                    mViewDragHelper.settleCapturedViewAt(0, rg_Tab.getHeight());
//                }
//            }
//            invalidate();
        }
    }
//    @Override
//    public void computeScroll() {
//        super.computeScroll();
//        if (mViewDragHelper.continueSettling(true)) {//如果还在惯性滑动
//            ViewCompat.postInvalidateOnAnimation(this);
//        }
//    }
}
