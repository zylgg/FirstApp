package com.example.jhzyl.firstapp.Home;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jhzyl.firstapp.Home.adapter.HomeThemeAdapter;
import com.example.jhzyl.firstapp.MainActivity;
import com.example.jhzyl.firstapp.OnChangeStatusTextColorListener;
import com.example.jhzyl.firstapp.R;
import com.example.jhzyl.firstapp.utils.SystemAppUtils;

public class HomeFragment extends Fragment implements OnVisibilityTitleListener, View.OnClickListener {

    private static final String TAG = "HomeFragment";
    View ll_home_tab;
    TabLayout tl_home_tab;
    ViewPager vp_home_content;
    TextView tv_home_theme_title;
    private int scroll_max_height = 56 * 3;
    private boolean isCreated;
    private static OnChangeStatusTextColorListener onChangeStatusTextColorListener;
    private View view;
    private WindowManager wm;
    private boolean showWm = true;//默认是应该显示悬浮通知栏
    private WindowManager.LayoutParams params;

    public static HomeFragment getInstance(OnChangeStatusTextColorListener listener) {
        onChangeStatusTextColorListener = listener;
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment_layout, null);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreated) {
            Log.i(TAG, "setUserVisibleHint: ");
            if (onChangeStatusTextColorListener != null) {
                onChangeStatusTextColorListener.onChange(true);
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        ll_home_tab = view.findViewById(R.id.ll_home_tab);
        tv_home_theme_title = view.findViewById(R.id.tv_home_theme_title);
        tv_home_theme_title.setOnClickListener(this);

        scroll_max_height = tv_home_theme_title.getLayoutParams().height;
        if (Build.VERSION.SDK_INT >= 19) {
            tv_home_theme_title.getLayoutParams().height = scroll_max_height + SystemAppUtils.dip2px(getContext(), 25);//25dp是状态栏高度

            tv_home_theme_title.setPadding(0, SystemAppUtils.dip2px(getContext(), 25), 0, 0);
        }
        tl_home_tab = view.findViewById(R.id.tl_home_tab);
        vp_home_content = view.findViewById(R.id.vp_home_content);


        final TransitionDrawable drawable= (TransitionDrawable) ResourcesCompat.getDrawable(getResources(),R.drawable.transition1,null);
        final TransitionDrawable drawable2= (TransitionDrawable) ResourcesCompat.getDrawable(getResources(),R.drawable.transition2,null);
        ll_home_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_home_tab.setBackgroundDrawable(drawable);
                drawable.startTransition(2000);
            }
        });

        MainActivity activity = (MainActivity) getActivity();
        vp_home_content.setAdapter(new HomeThemeAdapter(activity, this, activity.getSupportFragmentManager()));
        tl_home_tab.setupWithViewPager(vp_home_content);
    }

    @Override
    public void hide() {
        if (ll_home_tab.getTranslationY() == 0)
            createTranslate(false);
    }

    @Override
    public void open() {
        if (ll_home_tab.getTranslationY() == (-scroll_max_height))
            createTranslate(true);
    }

    private void createTranslate(final boolean is_open) {
        final int colorAccent = getContext().getResources().getColor(R.color.colorAccent);

        ValueAnimator animation = ValueAnimator.ofInt(0, scroll_max_height);
        animation.setDuration(250);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = animation.getAnimatedFraction();
                if (is_open) {//向下 打开
                    ll_home_tab.setTranslationY(scroll_max_height * (animatedValue - 1));
                    tv_home_theme_title.setTextColor(ColorUtils.blendARGB(colorAccent, Color.WHITE, animatedValue));

                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) vp_home_content.getLayoutParams();
                    layoutParams.topMargin = (int) (scroll_max_height * (animatedValue - 1));
                    vp_home_content.setLayoutParams(layoutParams);
//                    vp_home_content.setPadding(0,(int) (-scroll_max_height * animatedValue),0,0);
//                    vp_home_content.setY(scroll_max_height * (animatedValue - 1));
                } else {//向上 隐藏
                    ll_home_tab.setTranslationY(-scroll_max_height * animatedValue);
                    tv_home_theme_title.setTextColor(ColorUtils.blendARGB(Color.WHITE, colorAccent, animatedValue));

                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) vp_home_content.getLayoutParams();
                    layoutParams.topMargin = (int) (-scroll_max_height * animatedValue);
                    vp_home_content.setLayoutParams(layoutParams);
//                    vp_home_content.setPadding(0, (int) (-scroll_max_height * animatedValue),0,0);
//                    vp_home_content.setY(-scroll_max_height * animatedValue);
                }

            }
        });
        animation.start();
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), ScrollingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        NotificationUtils notificationUtils = new NotificationUtils(getContext());
        notificationUtils.sendNotification("测试标题", "测试内容", intent);
    }

}
