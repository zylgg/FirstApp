package com.example.jhzyl.firstapp.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.jhzyl.firstapp.utils.SystemAppUtils;

public class MoveTextView extends TextView {
    public MoveTextView(Context context) {
        super(context);
    }

    public MoveTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    WindowManager.LayoutParams mWindowParams;
    private WindowManager mWindowManager;

    public void setmWindowParams(WindowManager.LayoutParams mWindowParams) {
        this.mWindowParams = mWindowParams;
    }

    public void setmWindowManager(WindowManager mWindowManager) {
        this.mWindowManager = mWindowManager;
    }

    private float mRawX, mRawY, mStartX, mStartY;

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        int titleHeight = SystemAppUtils.getStatusHeight(getContext());
//
//        // 当前值以屏幕左上角为原点
//        mRawX = event.getRawX();
//        mRawY = event.getRawY() - titleHeight;
//
//        final int action = event.getAction();
//
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                // 以当前父视图左上角为原点
//                mStartX = event.getX();
//                mStartY = event.getY();
//
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                updateWindowPosition();
//                break;
//
//            case MotionEvent.ACTION_UP:
//                updateWindowPosition();
//                break;
//        }
//
//        // 消耗触摸事件
//        return true;
//    }

    /**
     * 更新窗口参数，控制浮动窗口移动
     */
    private void updateWindowPosition() {
        // 更新坐标
        mWindowParams.x = (int) (mRawX - mStartX);
        mWindowParams.y = (int) (mRawY - mStartY);
        // 使参数生效
        mWindowManager.updateViewLayout(this, mWindowParams);
    }
}
