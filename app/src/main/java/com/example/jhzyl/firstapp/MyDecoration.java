package com.example.jhzyl.firstapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


/**
 * Created by wnw on 16-5-22.
 */

public class MyDecoration extends RecyclerView.ItemDecoration {
    public static final String TAG = "MyDecoration";
    private Context mContext;
    private Drawable mDivider;
    private Drawable mDivider_vertical;
    private int mOrientation;
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    //我们通过获取系统属性中的listDivider来添加，在系统中的AppTheme中设置
    public static final int[] ATRRS = new int[]{
            android.R.attr.listDivider
    };

    public MyDecoration(Context context) {
        this(context, -1, -1);
    }

    public MyDecoration(Context context, int orientation) {
        this(context, orientation, -1);
    }

    public MyDecoration(Context context, int orientation, int resid) {
        this.mContext = context;
        setOrientation(orientation);
        if (resid == -1) {
            final TypedArray ta = context.obtainStyledAttributes(ATRRS);
            this.mDivider = ta.getDrawable(0);
            ta.recycle();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.mDivider = context.getResources().getDrawable(resid, null);
            } else {
                this.mDivider = context.getResources().getDrawable(resid);
            }
        }
    }

    public void setVerticalLine(int resid) {
        if (mContext == null) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.mDivider_vertical = mContext.getResources().getDrawable(resid, null);
        } else {
            this.mDivider_vertical = mContext.getResources().getDrawable(resid);
        }
    }

    //设置屏幕的方向
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST && orientation != -1) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == HORIZONTAL_LIST) {
            drawVerticalLine(c, parent, state);
        } else if (mOrientation == VERTICAL_LIST) {
            drawHorizontalLine(c, parent, state);
        } else {
            drawVerticalLine(c, parent, state);
            drawHorizontalLine(c, parent, state);
        }
    }

    //画横线, 这里的parent其实是显示在屏幕显示的这部分
    public void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int mSpanCount = 0;

        if (layoutManager instanceof GridLayoutManager) {
            mSpanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        for (int i = 0; i < childCount; i++) {
            RecyclerView.Adapter adapter= parent.getAdapter();
            final View child = parent.getChildAt(i);
            //获得child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            if (mSpanCount > 0) {
                left = child.getLeft();
                right = child.getRight();
            }
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    //画竖线
    public void drawVerticalLine(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int mSpanCount = 0;
        if (layoutManager instanceof GridLayoutManager) {
            mSpanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            //获得child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            if (mSpanCount > 0) {
                top = child.getTop();
                bottom = child.getBottom();
            }
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider_vertical.getIntrinsicWidth();
            mDivider_vertical.setBounds(left, top, right, bottom);
            mDivider_vertical.draw(c);
        }
    }

    //由于Divider也有长宽高，每一个Item需要向下或者向右偏移
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == HORIZONTAL_LIST) {
            //画竖线，就是往右偏移一个分割线的宽度
            outRect.set(0, 0, mDivider_vertical.getIntrinsicWidth(), 0);
        } else {
            //画横线，就是往下偏移一个分割线的高度
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }
    }
}