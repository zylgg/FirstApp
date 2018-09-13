package com.example.jhzyl.firstapp.Home.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.example.jhzyl.firstapp.Home.HomeSuperFragment;
import com.example.jhzyl.firstapp.Home.HomeTestListFragment;
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
            Log.i("notifi", "getItem: ");
            return HomeSuperFragment.getInstance(position,onVisibilityTitleListener);
        }else if (position==stringArray.length-1){
            return HomeTestListFragment.getInstance(position,onVisibilityTitleListener);
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

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (position==0||position==stringArray.length-1)return;
        super.destroyItem(container, position, object);
    }
}
