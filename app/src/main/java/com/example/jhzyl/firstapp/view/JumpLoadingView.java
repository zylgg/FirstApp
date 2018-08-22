package com.example.jhzyl.firstapp.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.callback.IHeaderCallBack;
import com.example.jhzyl.firstapp.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class JumpLoadingView extends RelativeLayout implements IHeaderCallBack {
    private ImageView people, shadow;

    public JumpLoadingView(Context context) {
        super(context);
        initView();
    }

    public JumpLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public JumpLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.jump_loading_view, this);
        people = view.findViewById(R.id.iv_people_jump);
        shadow = view.findViewById(R.id.iv_people_shadow);
    }

    //---------------------------下拉回调方法----------------------------
    private boolean finished = false;
    private boolean refreshing = false;
    private float shadow_h, people_h;

    /**
     * 默认
     */
    @Override
    public void onStateNormal() {
        people.setVisibility(View.GONE);
        shadow.setVisibility(View.VISIBLE);
        shadow_h = shadow.getMeasuredHeight();
        people_h = people.getMeasuredHeight();
    }

    /**
     * 正在拉动
     *
     * @param headerMovePercent
     * @param offsetY
     * @param deltaY
     */
    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY, int deltaY) {
        if (finished || refreshing) return;
        if (headerMovePercent <= 1) {
            shadow.setTranslationY(offsetY - shadow_h);//减去shadowH,避免脚步shadow移动到外部
        }
    }

    /**
     * 小人开始的位置
     */
    private float people_top_y;
    /**
     * 小人最低的位置
     */
    private float people_bottom_y;

    /**
     * 松开执行刷新前 准备
     */
    @Override
    public void onStateReady() {
        people.setVisibility(View.VISIBLE);
        people_top_y = (getMeasuredHeight() - people_h) / 2.0f;
        people_bottom_y = getMeasuredHeight() - shadow_h / 2.0f - people_h;

        people.setTranslationY(people_top_y);
    }

    private ValueAnimator people_animator;
    /**
     * 刷新
     */
    @Override
    public void onStateRefreshing() {
        people.setVisibility(View.VISIBLE);
        //人物跳下-弹起-弹到顶部-顺时针旋转15°-落下-弹起-弹到顶部-逆时针旋转15°  （完成一个跳跃周期）
        people_animator=ObjectAnimator.ofFloat(people,"translationY",people_top_y,people_bottom_y,people_top_y,people_bottom_y);
        people_animator.setDuration(2400);
        people_animator.setInterpolator(new LinearInterpolator());
        people_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();
                float values = (float) animation.getAnimatedValue();
                if (animatedFraction>0.1&&values==people_top_y){
                    ValueAnimator rotationA= ObjectAnimator.ofFloat(people,"rotation",0f,15f);
                    rotationA.start();
                }else if (animatedFraction>0.5&&values==people_top_y){
                    ValueAnimator rotationA= ObjectAnimator.ofFloat(people,"rotation",0f,-15f);
                    rotationA.start();
                }
            }
        });
        people_animator.setRepeatCount(-1);
        people_animator.setRepeatMode(ValueAnimator.REVERSE);
        people_animator.start();
    }

    /**
     * 加载完成
     *
     * @param success
     */
    @Override
    public void onStateFinish(boolean success) {
        //从当前位置落入水中（view底部），然后加载烟花gif动画
        people_animator.end();

    }

    @Override
    public void setRefreshTime(long lastRefreshTime) {

    }

    @Override
    public void hide() {
        setVisibility(View.GONE);
    }

    @Override
    public void show() {
        setVisibility(View.VISIBLE);
    }

    @Override
    public int getHeaderHeight() {
        return getMeasuredHeight();
    }
}
