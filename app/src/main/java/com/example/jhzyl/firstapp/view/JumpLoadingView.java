package com.example.jhzyl.firstapp.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.andview.refreshview.callback.IHeaderCallBack;
import com.example.jhzyl.firstapp.R;


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

    private AnimatorSet people_animatorSet, shadow_animatorSet;

    /**
     * 刷新
     */
    @Override
    public void onStateRefreshing() {
        people.setVisibility(View.VISIBLE);
        //人物跳下-弹起-弹到顶部-顺时针旋转15°-落下-弹起-弹到顶部-逆时针旋转15°  （完成一个跳跃周期）
        ValueAnimator people_animator_a = ObjectAnimator.ofFloat(people, "translationY", people_top_y, people_bottom_y, people_top_y).setDuration(800);

        ValueAnimator rotation_15A = ObjectAnimator.ofFloat(people, "rotation", 0f, 15f).setDuration(150);

        ValueAnimator people_animator_b = ObjectAnimator.ofFloat(people, "translationY", people_top_y, people_bottom_y, people_top_y).setDuration(800);

        ValueAnimator rotation_15B = ObjectAnimator.ofFloat(people, "rotation", 0f, -15f).setDuration(150);
        people_animatorSet = new AnimatorSet();
        people_animatorSet.playSequentially(people_animator_a, rotation_15A, people_animator_b, rotation_15B);

        //-----------影子先变大-在变小
        ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(shadow, "scaleX", 1, 1.5f, 1).setDuration(800);
        ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(shadow, "scaleY", 1, 1.5f, 1).setDuration(800);
        AnimatorSet shadow_animationSet1 = new AnimatorSet();
        shadow_animationSet1.play(scaleX1).with(scaleY1);

        ObjectAnimator scaleY0 = ObjectAnimator.ofFloat(shadow, "scaleY", 1, 1).setDuration(150);

        ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(shadow, "scaleX", 1f, 1.5f, 1f).setDuration(800);
        ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(shadow, "scaleY", 1f, 1.5f, 1f).setDuration(800);
        AnimatorSet shadow_animationSet2 = new AnimatorSet();
        shadow_animationSet2.play(scaleX2).with(scaleY2);

        ObjectAnimator scaleY00 = ObjectAnimator.ofFloat(shadow, "scaleY", 1f, 1f).setDuration(150);

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
     * 加载完成
     *
     * @param success
     */
    @Override
    public void onStateFinish(boolean success) {
        //从当前位置落入水中（view底部），然后加载烟花gif动画
        if (people_animatorSet != null && people_animatorSet.isRunning()) {
            people_animatorSet.cancel();
            shadow_animatorSet.cancel();
        }
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
