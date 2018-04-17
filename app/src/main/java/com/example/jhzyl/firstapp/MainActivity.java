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

import com.example.jhzyl.firstapp.DashBoard.DashBoardFragment;
import com.example.jhzyl.firstapp.Home.HomeFragment;
import com.example.jhzyl.firstapp.adapter.MyVpAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ViewPager vp_content;
    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    vp_content.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    vp_content.setCurrentItem(1);
                    return true;
            }
            return false;
        }
    };
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            navigation.getMenu().getItem(position).setChecked(true);
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

        vp_content = findViewById(R.id.vp_content);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        MyVpAdapter myVpAdapter = new MyVpAdapter(getSupportFragmentManager());
        myVpAdapter.addFragment(new HomeFragment());
        myVpAdapter.addFragment(new DashBoardFragment());
        vp_content.setAdapter(myVpAdapter);
        vp_content.addOnPageChangeListener(onPageChangeListener);

    }



}
