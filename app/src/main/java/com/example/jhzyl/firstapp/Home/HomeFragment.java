package com.example.jhzyl.firstapp.Home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.jhzyl.firstapp.MainActivity;
import com.example.jhzyl.firstapp.R;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment implements OnVisibilityTitleListener {
    public static final String TAG = "HomeFragment";

    LinearLayout ll_home_tab;
    TabLayout tl_home_tab;
    ViewPager vp_home_content;
    TextView tv_home_theme_title;
    private int scroll_max_height = 56*3;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment_layout, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        ll_home_tab = view.findViewById(R.id.ll_home_tab);
        tv_home_theme_title = view.findViewById(R.id.tv_home_theme_title);

        scroll_max_height=tv_home_theme_title.getLayoutParams().height;
        if (Build.VERSION.SDK_INT >= 19) {
            tv_home_theme_title.getLayoutParams().height=scroll_max_height+dip2px(getContext(),25);//25dp是状态栏高度

            tv_home_theme_title.setPadding(0,dip2px(getContext(),25),0,0);
        }
        tl_home_tab = view.findViewById(R.id.tl_home_tab);
        vp_home_content = view.findViewById(R.id.vp_home_content);

        MainActivity activity = (MainActivity) getActivity();
        vp_home_content.setAdapter(new HomeThemeAdapter(activity, this, activity.getSupportFragmentManager()));
        tl_home_tab.setupWithViewPager(vp_home_content);
    }
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    private void createTranslate(final boolean is_open) {
        final int colorAccent = getContext().getResources().getColor(R.color.colorAccent);

        ValueAnimator animation = ValueAnimator.ofInt(0, scroll_max_height);
        animation.setDuration(300);
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
                } else {//向上 隐藏
                    ll_home_tab.setTranslationY(-scroll_max_height * animatedValue);
                    tv_home_theme_title.setTextColor(ColorUtils.blendARGB(Color.WHITE, colorAccent, animatedValue));

                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) vp_home_content.getLayoutParams();
                    layoutParams.topMargin = (int) (-scroll_max_height * animatedValue);
                    vp_home_content.setLayoutParams(layoutParams);
                }

            }
        });
        animation.start();
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

}
