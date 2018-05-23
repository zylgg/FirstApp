package com.example.jhzyl.firstapp.DashBoard;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
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

public class DashBoardFragment extends Fragment {

    private static final String TAG ="DashBoardFragment";
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private ImageView iv_dash_board_title;
    private boolean isCreated;
    private XRefreshView xrv_refresh_all;
    private AppBarLayout app_bar;
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
        app_bar=view.findViewById(R.id.app_bar);
        iv_dash_board_title = view.findViewById(R.id.iv_dash_board_title);
        tabs = view.findViewById(R.id.tabs);
        pager = view.findViewById(R.id.pager);

//        xrv_refresh_all=view.findViewById(R.id.xrv_refresh_all);
//        xrv_refresh_all.setPullRefreshEnable(true);
//        xrv_refresh_all.setPullLoadEnable(false);
//        xrv_refresh_all.disallowInterceptTouchEvent(true);
//        xrv_refresh_all.enableRecyclerViewPullUp(false);

        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                xrv_refresh_all.disallowInterceptTouchEvent(true);
                if (Math.abs(verticalOffset)==iv_dash_board_title.getHeight()){
                    Toast.makeText(getActivity(),"完全关闭",Toast.LENGTH_SHORT).show();
                }else if (verticalOffset==0){
//                    xrv_refresh_all.disallowInterceptTouchEvent(false);
                    Toast.makeText(getActivity(),"完全打开",Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "verticalOffset: "+verticalOffset);
            }
        });
//        xrv_refresh_all.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener(){
//            @Override
//            public void onRefresh(boolean isPullDown) {
//                super.onRefresh(isPullDown);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                       xrv_refresh_all.stopRefresh();
//                       Toast.makeText(getActivity(),"刷新完了",Toast.LENGTH_SHORT).show();
//                    }
//                },1000);
//            }
//        });
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
                "A____A", "BB", "C____C", "DDDDDDDDDDDD",
                "E____E", "F____F", "G____G", "H____H"};

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
