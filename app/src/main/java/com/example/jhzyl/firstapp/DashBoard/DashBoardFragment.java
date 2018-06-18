package com.example.jhzyl.firstapp.DashBoard;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.example.jhzyl.firstapp.DashBoard.PagerTab.PagerSlidingTabStrip;
import com.example.jhzyl.firstapp.Home.HomeFragment;
import com.example.jhzyl.firstapp.MainActivity;
import com.example.jhzyl.firstapp.OnChangeStatusTextColorListener;
import com.example.jhzyl.firstapp.R;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

public class DashBoardFragment extends Fragment {

    private static final String TAG ="DashBoardFragment";
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private ImageView iv_dash_board_title;
    private boolean isCreated;
    private XRefreshView xrv_refresh_all;
    private AppBarLayout app_bar;
    private CoordinatorLayout cl;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated=true;
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
        if (isVisibleToUser&&isCreated){
            Log.i(TAG, "setUserVisibleHint: ");
            if (onChangeStatusTextColorListener!=null){
                onChangeStatusTextColorListener.onChange(false);
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        cl=view.findViewById(R.id.cl);

        app_bar=view.findViewById(R.id.app_bar);
        iv_dash_board_title = view.findViewById(R.id.iv_dash_board_title);
        tabs = view.findViewById(R.id.tabs);
        LinearLayout childAt = (LinearLayout) tabs.getChildAt(0);
        childAt.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);

        pager = view.findViewById(R.id.pager);
        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.i(TAG, "verticalOffset: "+verticalOffset);
                if (Math.abs(verticalOffset)==iv_dash_board_title.getHeight()){
//                    Toast.makeText(getActivity(),"完全关闭",Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(SuperAwesomeCardFragment.PullState.whileClose);
                }else if (verticalOffset==0){
//                    Toast.makeText(getActivity(),"完全打开",Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(SuperAwesomeCardFragment.PullState.whileOpen);
                }else{
                    EventBus.getDefault().post(SuperAwesomeCardFragment.PullState.other);
                }

            }
        });
        Picasso.with(getContext()).load("http://inews.gtimg.com/newsapp_match/0/3348583155/0").into(iv_dash_board_title);
        pager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager()));
        tabs.setViewPager(pager);

    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {
                "A____A", "BB", "C____C"
//                , "本草纲目植物"
//                , "E____E", "F____F", "G____G", "H____H"
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
            return SuperAwesomeCardFragment.newInstance(position);
        }
    }
}
