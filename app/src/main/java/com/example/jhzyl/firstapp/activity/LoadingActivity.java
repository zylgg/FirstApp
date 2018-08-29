package com.example.jhzyl.firstapp.activity;

import android.animation.ValueAnimator;
import android.app.Service;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.andview.refreshview.XRefreshView;
import com.example.jhzyl.firstapp.R;
import com.example.jhzyl.firstapp.utils.DensityUtil;
import com.example.jhzyl.firstapp.utils.SystemAppUtils;
import com.example.jhzyl.firstapp.view.JumpLoadingView;
import com.example.jhzyl.firstapp.view.MoveTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoadingActivity extends AppCompatActivity {

    private XRefreshView xrv_loading;
    private ListView iv_loading;
    private List<String> list;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        xrv_loading = findViewById(R.id.xrv_loading);
//        xrv_loading.setVisibility(View.GONE);
        iv_loading = findViewById(R.id.iv_loading);
//        iv_loading.setVisibility(View.GONE);


        xrv_loading.setPullRefreshEnable(true);
        xrv_loading.setPullLoadEnable(false);
        xrv_loading.setPinnedTime(3000);
        xrv_loading.setCustomHeaderView(new JumpLoadingView(this));

        xrv_loading.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                super.onRefresh(isPullDown);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int count = (int) (Math.random() * 5) + 1;
                        List<String> mlists = new ArrayList<>();
                        for (int i = 0; i < count; i++) {
                            mlists.add("新数据" + i);
                        }
                        mlists.addAll(list);

                        list.clear();
                        list.addAll(mlists);
                        adapter.notifyDataSetChanged();
                        xrv_loading.stopRefresh();
                    }
                }, 2000);
            }
        });


        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("item" + i);
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        iv_loading.setAdapter(adapter);

//        floatView();
    }

    private WindowManager.LayoutParams mWindowParams;
    private WindowManager mWindowManager;
    private MoveTextView imageView;

    private void floatView() {
        // 获取Service
        mWindowManager = (WindowManager) getSystemService(Service.WINDOW_SERVICE);

        imageView = new MoveTextView(this);
        imageView.setText("1234567890QWERTY");
        imageView.setBackgroundColor(getResources().getColor(R.color.blue));
        imageView.setOnClickListener(clickListener);

        // 设置窗口类型，一共有三种Application windows, Sub-windows, System windows
        // API中以TYPE_开头的常量有23个
        mWindowParams = new WindowManager.LayoutParams();
        imageView.setmWindowParams(mWindowParams);
        imageView.setmWindowManager(mWindowManager);


        mWindowParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        // 设置期望的bitmap格式
        mWindowParams.format = PixelFormat.RGBA_8888;

        // 以下属性在Layout Params中常见重力、坐标，宽高
        mWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
        mWindowParams.x = 100;
        mWindowParams.y = 100;

        mWindowParams.width = (int) (SystemAppUtils.getScreenWidth(this) * 0.8);
        mWindowParams.height = DensityUtil.dip2px(this, 48);

        // 添加指定视图
        mWindowManager.addView(imageView, mWindowParams);

    }

    private ValueAnimator animator;
    private int fisrt_width = 0;
    private int next_start_value=0;

    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (fisrt_width == 0) {
                fisrt_width = imageView.getWidth();
            }

            if (animator == null) {
                animator = new ValueAnimator();
                animator.setIntValues(fisrt_width, 50);
                next_start_value=50;
                animator.setTarget(imageView);
                animator.setDuration(1500);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int curWidth = (int) animation.getAnimatedValue();

                        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                        layoutParams.width = curWidth;
                        imageView.setRight(curWidth);
                    }
                });
            } else {
                if (!animator.isRunning()) {
                    if (next_start_value==50){
                        animator.setIntValues(50, fisrt_width);
                        next_start_value=fisrt_width;
                    }else{
                        animator.setIntValues(fisrt_width, 50);
                        next_start_value=50;
                    }
                }
            }
            if (!animator.isRunning()) {
                animator.start();
            }
        }
    };


}
