package com.example.jhzyl.firstapp.DashBoard;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    private TextView tv_dash_board_title;
    private MutiProgress mp_3;
    private boolean isCreated;
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
        iv_dash_board_title = view.findViewById(R.id.iv_dash_board_title);
        Picasso.with(getContext()).load("http://inews.gtimg.com/newsapp_match/0/3348583155/0").into(iv_dash_board_title);

        tv_dash_board_title = view.findViewById(R.id.tv_dash_board_title);
        mp_3 = view.findViewById(R.id.mp_3);
        mp_3.setCurrNodeNO(5);

        tabs = view.findViewById(R.id.tabs);
        pager = view.findViewById(R.id.pager);
        if (Build.VERSION.SDK_INT >= 19) {
            //25dp是状态栏高度
            tv_dash_board_title.getLayoutParams().height = tv_dash_board_title.getLayoutParams().height + dip2px(getContext(), 25);
            tv_dash_board_title.setPadding(0, dip2px(getContext(), 25), 0, 0);
        }
        tv_dash_board_title.setText("DashBoard");


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
