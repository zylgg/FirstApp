package com.example.jhzyl.firstapp.Home.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jhzyl.firstapp.Home.HomeSuperFragment;
import com.example.jhzyl.firstapp.Home.HomeThemeFragment;
import com.example.jhzyl.firstapp.Home.OnVisibilityTitleListener;
import com.example.jhzyl.firstapp.R;

public class HomeThemeAdapter extends FragmentPagerAdapter {
    private Context context;
    private String[] stringArray;
    private OnVisibilityTitleListener onVisibilityTitleListener;
    public HomeThemeAdapter(Context context, OnVisibilityTitleListener listener, FragmentManager fm) {
        super(fm);
        this.context=context;
        this.onVisibilityTitleListener=listener;
        stringArray = context.getResources().getStringArray(R.array.home_theme);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return HomeSuperFragment.getInstance(position,onVisibilityTitleListener);
        }
        return HomeThemeFragment.getInstance(position,onVisibilityTitleListener);
    }

    @Override
    public int getCount() {
        return stringArray.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return stringArray[position];
    }
}
