package com.example.jhzyl.firstapp.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.andview.refreshview.callback.IHeaderCallBack;
import com.example.jhzyl.firstapp.R;


public class JumpLoadingView extends RelativeLayout implements IHeaderCallBack {
    private ImageView people, shadow;
    private ImageView iv_fireworks_anim;

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
        iv_fireworks_anim = view.findViewById(R.id.iv_fireworks_anim);
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
        finished = false;
        people.setVisibility(View.GONE);
        shadow.setVisibility(View.VISIBLE);
        shadow.setScaleX(1);
        shadow.setScaleY(1);
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
            Log.i("headerMovePercent",""+headerMovePercent);
            int measuredHeight = getMeasuredHeight();
            shadow.setTranslationY((float) ((measuredHeight - shadow_h)*headerMovePercent));//减去shadowH,避免脚步shadow移动到外部
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
        shadow.setTranslationY(getMeasuredHeight()-shadow_h);//下滑存在惯性滑动误差，so直接设置shadow在底部

        people_top_y = (getMeasuredHeight() - people_h) / 2.0f;
        people_bottom_y = getMeasuredHeight() - shadow_h / 2.0f - people_h;

        iv_fireworks_anim.setImageDrawable(null);
        iv_fireworks_anim.setVisibility(View.GONE);
        people.setVisibility(View.VISIBLE);
        people.setTranslationY(people_top_y);
    }

    private AnimatorSet people_animatorSet, shadow_animatorSet;

    /**
     * 刷新
     */
    @Override
    public void onStateRefreshing() {
        refreshing = true;
        people.setVisibility(View.VISIBLE);
        //人物跳下-弹起-弹到顶部-顺时针旋转15°-落下-弹起-弹到顶部-逆时针旋转15°  （完成一个跳跃周期）
        ValueAnimator people_animator_a = ObjectAnimator.ofFloat(people, "translationY", people_top_y, people_bottom_y, people_top_y).setDuration(500);
        ValueAnimator rotation_15A = ObjectAnimator.ofFloat(people, "rotation", 0f, 15f).setDuration(120);

        ValueAnimator people_animator_b = ObjectAnimator.ofFloat(people, "translationY", people_top_y, people_bottom_y, people_top_y).setDuration(500);
        ValueAnimator rotation_15B = ObjectAnimator.ofFloat(people, "rotation", 0f, -15f).setDuration(120);
        //加入小人跳跃是的进度监听
        people_animator_a.addUpdateListener(getPeopleJumpUpListener());
        rotation_15A.addUpdateListener(getPeopleJumpUpListener());
        people_animator_b.addUpdateListener(getPeopleJumpUpListener());
        rotation_15B.addUpdateListener(getPeopleJumpUpListener());

        people_animatorSet = new AnimatorSet();
        people_animatorSet.playSequentially(people_animator_a, rotation_15A, people_animator_b, rotation_15B);

        //-----------影子先变大-在变小
        ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(shadow, "scaleX", 1, 1.5f, 1).setDuration(500);
        ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(shadow, "scaleY", 1, 1.5f, 1).setDuration(500);
        AnimatorSet shadow_animationSet1 = new AnimatorSet();
        shadow_animationSet1.play(scaleX1).with(scaleY1);

        ObjectAnimator scaleY0 = ObjectAnimator.ofFloat(shadow, "scaleY", 1, 1).setDuration(120);

        ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(shadow, "scaleX", 1f, 1.5f, 1f).setDuration(500);
        ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(shadow, "scaleY", 1f, 1.5f, 1f).setDuration(500);
        AnimatorSet shadow_animationSet2 = new AnimatorSet();
        shadow_animationSet2.play(scaleX2).with(scaleY2);

        ObjectAnimator scaleY00 = ObjectAnimator.ofFloat(shadow, "scaleY", 1f, 1f).setDuration(120);

        shadow_animatorSet = new AnimatorSet();
        shadow_animatorSet.playSequentially(shadow_animationSet1, scaleY0, shadow_animationSet2, scaleY00);
        //-----------

        people_animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                people_animatorSet.start();
                shadow_animatorSet.start();
            }
        });
        people_animatorSet.start();
        shadow_animatorSet.start();
    }

    /**
     * 记录小人跳跃时更新的y位置   及  上一个位置
     */
    private float peopleUpdatedY = 0f, last_peopleUpdatedY;
    /**
     * [1]:等于1，向上   等于0，水平   等于-1，向下
     */
    private float peopleCurDirection[] = new float[2];

    private ValueAnimator.AnimatorUpdateListener getPeopleJumpUpListener() {
        return new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                peopleUpdatedY = people.getTranslationY();
                if (last_peopleUpdatedY != 0) {
                    peopleCurDirection[0] = peopleUpdatedY;
                    if (peopleUpdatedY > last_peopleUpdatedY) {
                        peopleCurDirection[1] = (-1);
                    } else if (peopleUpdatedY == last_peopleUpdatedY) {
                        peopleCurDirection[1] = 0;
                    } else {
                        peopleCurDirection[1] = 1;
                    }
                }
                if (finished) {//记录已完成时 到达顶部或者底部 才cancel 全部动画
                    if (peopleUpdatedY == people_top_y) {
                        FinishAnimation(true);
                    } else if (peopleUpdatedY == people_bottom_y) {
                        FinishAnimation(false);
                    }
                }
//                Log.i("update", "peopleUpdatedY: "+peopleUpdatedY);
                last_peopleUpdatedY = peopleUpdatedY;
            }
        };
    }

    /**
     * 加载完成
     *
     * @param success
     */
    @Override
    public void onStateFinish(boolean success) {
        finished = true;
        refreshing = false;
    }

    /**
     * 从当前位置落入水中（view底部），然后加载烟花gif动画
     *
     * @param isStartTop 是否从顶部落水
     */
    private void FinishAnimation(boolean isStartTop) {
        if (people_animatorSet != null && people_animatorSet.isRunning()) {
            people_animatorSet.cancel();
            shadow_animatorSet.cancel();

            //加载完 开始小人最后的延续动画
            ValueAnimator peopleEndAnimator = ObjectAnimator.ofFloat(people, "translationY", peopleUpdatedY, getMeasuredHeight() + people_h).setDuration(500);
            ObjectAnimator scaleX2, scaleY2;
            if (isStartTop) {
                scaleX2 = ObjectAnimator.ofFloat(shadow, "scaleX", 1, 1.5f).setDuration(500);
                scaleY2 = ObjectAnimator.ofFloat(shadow, "scaleY", 1, 1.5f).setDuration(500);
            } else {
                scaleX2 = ObjectAnimator.ofFloat(shadow, "scaleX", 1.5f, 0f).setDuration(800);
                scaleY2 = ObjectAnimator.ofFloat(shadow, "scaleY", 1.5f, 0f).setDuration(800);
            }
            //影子延迟缩小的动画
            ObjectAnimator delay_x0= ObjectAnimator.ofFloat(shadow, "scaleX", 1.5f, 0f).setDuration(500);
            ObjectAnimator delay_y0= ObjectAnimator.ofFloat(shadow, "scaleY", 1.5f, 0f).setDuration(500);

            AnimatorSet shadowEndAnimatorSet = new AnimatorSet();
            AnimatorSet.Builder with = shadowEndAnimatorSet.play(scaleX2).with(scaleY2);
            if (isStartTop){//如果是 从顶部落下 影子需要延迟缩小
                delay_x0.setStartDelay(500);
                delay_y0.setStartDelay(500);
                with.with(delay_x0).with(delay_y0);
            }

            AnimatorSet endAnimatorSet = new AnimatorSet();
            endAnimatorSet.playTogether(peopleEndAnimator, shadowEndAnimatorSet);
            endAnimatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ObjectAnimator.ofFloat(people,"rotation",0).start();
                    people.setVisibility(View.GONE);
                    shadow.setVisibility(View.GONE);
                    loadFireWorksAnim();
                }
            });
            endAnimatorSet.start();

        }
    }

    /**
     * 加载烟花动画
     */
    private void loadFireWorksAnim() {
        AnimationDrawable drawable = (AnimationDrawable) getResources().getDrawable(R.drawable.end_fireworks_anim);
        iv_fireworks_anim.setVisibility(View.VISIBLE);
        iv_fireworks_anim.setImageDrawable(drawable);
        drawable.start();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_fireworks_anim.setVisibility(View.GONE);
            }
        }, 1500);
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
