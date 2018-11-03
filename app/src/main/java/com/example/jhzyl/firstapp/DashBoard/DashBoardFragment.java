package com.example.jhzyl.firstapp.DashBoard;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.andview.refreshview.XRefreshView;
import com.example.jhzyl.firstapp.DashBoard.PagerTab.PagerSlidingTabStrip;
import com.example.jhzyl.firstapp.MainActivity;
import com.example.jhzyl.firstapp.OnChangeStatusTextColorListener;
import com.example.jhzyl.firstapp.R;
import com.example.jhzyl.firstapp.utils.SystemAppUtils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class DashBoardFragment extends Fragment {

    private static final String TAG = "DashBoardFragment";
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private ImageView iv_dash_board_title;
    private boolean isCreated;
    private XRefreshView xrv_refresh_all;
    private AppBarLayout app_bar;
    private CoordinatorLayout cl;
    private View v_board_fragment_status;
    private View t_titles;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
    }

    private static OnChangeStatusTextColorListener onChangeStatusTextColorListener;

    public static DashBoardFragment getInstance(OnChangeStatusTextColorListener listener) {
        onChangeStatusTextColorListener = listener;
        return new DashBoardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dash_board_fragment_layout, null);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreated) {
            Log.i(TAG, "setUserVisibleHint: ");
            if (onChangeStatusTextColorListener != null) {
                onChangeStatusTextColorListener.onChange(false);
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        cl = view.findViewById(R.id.cl);
        t_titles=view.findViewById(R.id.t_titles);
        v_board_fragment_status = view.findViewById(R.id.v_board_fragment_status);
        app_bar = view.findViewById(R.id.app_bar);
        iv_dash_board_title = view.findViewById(R.id.iv_dash_board_title);
        tabs = view.findViewById(R.id.tabs);
        pager = view.findViewById(R.id.pager);

        int statusH = SystemAppUtils.getStatusHeight(getContext());
        v_board_fragment_status.getLayoutParams().height=statusH;

        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.KITKAT&&Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
            app_bar.setPadding(0, statusH, 0, 0);
        }

        LinearLayout childAt = (LinearLayout) tabs.getChildAt(0);
        childAt.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float verticalOffset_abs = Math.abs(verticalOffset);
                float totalScrollRange = app_bar.getTotalScrollRange();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    float expandedPercentage = verticalOffset_abs / totalScrollRange;
                    v_board_fragment_status.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT, getResources().getColor(R.color.colorAccent), expandedPercentage));
                    t_titles.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT, getResources().getColor(R.color.colorAccent), expandedPercentage));
                }
                if (verticalOffset_abs == totalScrollRange) {//完全关闭
                    EventBus.getDefault().post(SuperAwesomeCardFragment.PullState.whileClose);
                } else if (verticalOffset_abs == 0) {//完全打开
                    EventBus.getDefault().post(SuperAwesomeCardFragment.PullState.whileOpen);
                } else {
                    EventBus.getDefault().post(SuperAwesomeCardFragment.PullState.other);
                }

            }
        });

        Picasso.with(getContext()).load("http://inews.gtimg.com/newsapp_match/0/3348583155/0").into(iv_dash_board_title);
        MainActivity activity = (MainActivity) getActivity();

        pager.setAdapter(new MyPagerAdapter(activity.getSupportFragmentManager()));
        tabs.setViewPager(pager);


    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {
                "A____A", "BB", "C____C"
                , "本草纲目植物"
                , "E____E", "F____F", "G____G", "H____H"
        };

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
//            if (position==TITLES.length-1){
//                return new SuperAwesomeCardFragment2();
//            }
            return SuperAwesomeCardFragment.newInstance(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }
}
