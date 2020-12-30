package com.example.jhzyl.firstapp.Home

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.example.jhzyl.firstapp.R
import com.example.jhzyl.firstapp.ScrollingFragment
import com.example.jhzyl.firstapp.TestKotlin_builder
import com.example.jhzyl.firstapp.databinding.ActivityScrollingBinding
import org.greenrobot.eventbus.EventBus

class ScrollingActivity : AppCompatActivity() {
    private var tl_scrolling: TabLayout? = null
    private var vp_scrolling: ViewPager? = null
    private var app_bar: AppBarLayout? = null

    enum class ShopAppBarLayoutOpenStatus {
        wholeOpen, other, wholeClose
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(inflate.root)

        app_bar = findViewById(R.id.app_bar)
        app_bar!!.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) {
                EventBus.getDefault().post(ShopAppBarLayoutOpenStatus.wholeOpen)
            } else if (Math.abs(verticalOffset) == appBarLayout.totalScrollRange) {
                EventBus.getDefault().post(ShopAppBarLayoutOpenStatus.wholeClose)
            } else {
                EventBus.getDefault().post(ShopAppBarLayoutOpenStatus.other)
            }
        })
        tl_scrolling = findViewById(R.id.tl_scrolling)
        vp_scrolling = findViewById(R.id.vp_scrolling)
        vp_scrolling!!.setAdapter(object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return ScrollingFragment()
            }

            override fun getCount(): Int {
                return 3
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return "tab:$position"
            }
        })
        tl_scrolling!!.setupWithViewPager(vp_scrolling)


        inflate.tvScrollingTitle.setOnClickListener{  v ->
            TestKotlin_builder.Builder(this).setText("滚动页面点击").create().show();
        }
    }
}