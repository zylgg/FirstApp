package com.example.jhzyl.firstapp.Home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.jhzyl.firstapp.R;

public class HomeThemeAdapter extends FragmentStatePagerAdapter {
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
