package com.example.jhzyl.firstapp.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.jhzyl.firstapp.R;

import java.util.List;
import java.util.Random;

public class LooperLayout extends FrameLayout {
    private List<String> tipList;
    private int curTipIndex = 0;
    private long lastTimeMillis;
    private static final int ANIM_DELAYED_MILLIONS = 2 * 1000;
    /**
     * 动画持续时长
     */
    private static final int ANIM_DURATION = 1 * 1000;
    private View tv_tip_out, tv_tip_in;
    private static final String TIP_PREFIX = "是我老婆 ";
    private Animation anim_out, anim_in;

    public LooperLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTipFrame();
        initAnimation();
    }

    private void initTipFrame() {
        tv_tip_out = newTextView();
        tv_tip_in = newTextView();
        addView(tv_tip_in);
        addView(tv_tip_out);
    }

    private View newTextView() {
        View view = View.inflate(getContext(), R.layout.looper_layout_item, null);
        return view;
    }

    private void initAnimation() {
        anim_out = newAnimation(0, -1);
        anim_in = newAnimation(1, 0);
        anim_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                updateTip_AndPlayAnimation_WithCheck();
            }
        });
    }

    private Animation newAnimation(float fromYValue, float toYValue) {
        Animation anim = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, fromYValue, Animation.RELATIVE_TO_SELF, toYValue);
        anim.setDuration(ANIM_DURATION);
        anim.setStartOffset(ANIM_DELAYED_MILLIONS);
        anim.setInterpolator(new DecelerateInterpolator());
        return anim;
    }

    private void updateTip_AndPlayAnimation_WithCheck() {
        //如果切换时，上一个布局还没加载出来，不切换
        if (System.currentTimeMillis() - lastTimeMillis < 1000) {
            return;
        }
        lastTimeMillis = System.currentTimeMillis();
        updateTip_AndPlayAnimation();
    }

    private void updateTip_AndPlayAnimation() {
        if (curTipIndex % 2 == 0) {//为了实现顺序[一出一进]一个周期
            updateTip(tv_tip_out);
            tv_tip_in.startAnimation(anim_out);
            tv_tip_out.startAnimation(anim_in);
            this.bringChildToFront(tv_tip_in);
        } else {
            //第一次滚动时，再进
            updateTip(tv_tip_in);
            tv_tip_out.startAnimation(anim_out);
            tv_tip_in.startAnimation(anim_in);
            this.bringChildToFront(tv_tip_out);
        }
    }

    private void updateTip(View tipView) {
        String tip = getNextTip();
        if (!TextUtils.isEmpty(tip)) {
            TextView tv_qgp_mine_fragment_logistics_Remarked = tipView.findViewById(R.id.tv_qgp_mine_fragment_logistics_Remarked);
            tv_qgp_mine_fragment_logistics_Remarked.setText(tip + TIP_PREFIX);
        }
    }

    /**
     * 获取下一条消息
     *
     * @return
     */
    private String getNextTip() {
        if (isListEmpty(tipList)) return null;
        return tipList.get(curTipIndex++ % tipList.size());
    }

    private static boolean isListEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public void setTipList(List<String> tipList) {
        this.tipList = tipList;
        curTipIndex = 0;
        //第一次滚动时，先出
        updateTip(tv_tip_out);
        updateTip_AndPlayAnimation();
    }
}