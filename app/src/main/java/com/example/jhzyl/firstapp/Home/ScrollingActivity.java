package com.example.jhzyl.firstapp.Home;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.jhzyl.firstapp.R;
import com.example.jhzyl.firstapp.ScrollingFragment;
import com.example.jhzyl.firstapp.utils.StatusBarUtils;

import org.greenrobot.eventbus.EventBus;

public class ScrollingActivity extends AppCompatActivity {

    private TabLayout tl_scrolling;
    private ViewPager vp_scrolling;
    private AppBarLayout app_bar;
    public enum ShopAppBarLayoutOpenStatus {
        wholeOpen, other,wholeClose;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        app_bar=findViewById(R.id.app_bar);
        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset== 0) {
                    EventBus.getDefault().post(ShopAppBarLayoutOpenStatus.wholeOpen);
                } else if (Math.abs(verticalOffset)==appBarLayout.getTotalScrollRange()){
                    EventBus.getDefault().post(ShopAppBarLayoutOpenStatus.wholeClose);
                }else {
                    EventBus.getDefault().post(ShopAppBarLayoutOpenStatus.other);
                }
            }
        });

        tl_scrolling=findViewById(R.id.tl_scrolling);
        vp_scrolling=findViewById(R.id.vp_scrolling);
        vp_scrolling.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new ScrollingFragment();
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "tab:"+position;
            }
        });
        tl_scrolling.setupWithViewPager(vp_scrolling);

    }

}
