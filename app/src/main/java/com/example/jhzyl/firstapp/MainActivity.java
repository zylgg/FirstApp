package com.example.jhzyl.firstapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.jhzyl.firstapp.DashBoard.DashBoardFragment;
import com.example.jhzyl.firstapp.Home.HomeFragment;
import com.example.jhzyl.firstapp.adapter.MyVpAdapter;

public class MainActivity extends AppCompatActivity implements OnChangeStatusTextColorListener {

    private static final String TAG = "MainActivity";
    private ViewPager vp_content;
    private RadioGroup rg_bottom_menu;

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId==R.id.rb_home){
                vp_content.setCurrentItem(0);
            }else{
                vp_content.setCurrentItem(1);
            }
//            MyToast.T(MainActivity.this,"checkedId:"+checkedId);
        }
    };
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int checkedRadioButtonId = rg_bottom_menu.getCheckedRadioButtonId();
            RadioButton lastRB = rg_bottom_menu.findViewById(checkedRadioButtonId);
            lastRB.setChecked(false);
            ((RadioButton)rg_bottom_menu.getChildAt(position)).setChecked(true);
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        StatusBarUtils.setTransparent(this);

        vp_content = findViewById(R.id.vp_content);
        rg_bottom_menu = findViewById(R.id.rg_bottom_menu);
        rg_bottom_menu.setOnCheckedChangeListener(onCheckedChangeListener);

        MyVpAdapter myVpAdapter = new MyVpAdapter(getSupportFragmentManager());
        myVpAdapter.addFragment(HomeFragment.getInstance(this));
        myVpAdapter.addFragment(DashBoardFragment.getInstance(this));
        vp_content.setAdapter(myVpAdapter);
        vp_content.addOnPageChangeListener(onPageChangeListener);
        vp_content.setOffscreenPageLimit(1);

        ((RadioButton)rg_bottom_menu.getChildAt(0)).setChecked(true);
        onChange(true);
    }

    @Override
    public void onChange(boolean isBlack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int color = isBlack ? View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : View.SYSTEM_UI_FLAG_VISIBLE;
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | color);
        }
    }
}

