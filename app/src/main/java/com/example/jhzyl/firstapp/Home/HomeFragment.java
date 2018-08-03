package com.example.jhzyl.firstapp.Home;

import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jhzyl.firstapp.Home.adapter.HomeThemeAdapter;
import com.example.jhzyl.firstapp.MainActivity;
import com.example.jhzyl.firstapp.OnChangeStatusTextColorListener;
import com.example.jhzyl.firstapp.R;
import com.example.jhzyl.firstapp.SystemAppUtils;

import static android.content.Context.NOTIFICATION_SERVICE;

public class HomeFragment extends Fragment implements OnVisibilityTitleListener, View.OnClickListener {

    private static final String TAG = "HomeFragment";
    LinearLayout ll_home_tab;
    TabLayout tl_home_tab;
    ViewPager vp_home_content;
    TextView tv_home_theme_title;
    private int scroll_max_height = 56 * 3;
    private boolean isCreated;
    private static OnChangeStatusTextColorListener onChangeStatusTextColorListener;
    private View view;
    private WindowManager wm;
    private boolean showWm = true;//默认是应该显示悬浮通知栏
    private WindowManager.LayoutParams params;

    public static HomeFragment getInstance(OnChangeStatusTextColorListener listener) {
        onChangeStatusTextColorListener = listener;
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment_layout, null);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreated) {
            Log.i(TAG, "setUserVisibleHint: ");
            if (onChangeStatusTextColorListener != null) {
                onChangeStatusTextColorListener.onChange(true);
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        ll_home_tab = view.findViewById(R.id.ll_home_tab);
        tv_home_theme_title = view.findViewById(R.id.tv_home_theme_title);
        tv_home_theme_title.setOnClickListener(this);

        scroll_max_height = tv_home_theme_title.getLayoutParams().height;
        if (Build.VERSION.SDK_INT >= 19) {
            tv_home_theme_title.getLayoutParams().height = scroll_max_height + SystemAppUtils.dip2px(getContext(), 25);//25dp是状态栏高度

            tv_home_theme_title.setPadding(0, SystemAppUtils.dip2px(getContext(), 25), 0, 0);
        }
        tl_home_tab = view.findViewById(R.id.tl_home_tab);
        vp_home_content = view.findViewById(R.id.vp_home_content);

        MainActivity activity = (MainActivity) getActivity();

        vp_home_content.setAdapter(new HomeThemeAdapter(activity, this, activity.getSupportFragmentManager()));
        tl_home_tab.setupWithViewPager(vp_home_content);
    }

    @Override
    public void hide() {
        if (ll_home_tab.getTranslationY() == 0)
            createTranslate(false);
    }

    @Override
    public void open() {
        if (ll_home_tab.getTranslationY() == (-scroll_max_height))
            createTranslate(true);
    }

    private void createTranslate(final boolean is_open) {
        final int colorAccent = getContext().getResources().getColor(R.color.colorAccent);

        ValueAnimator animation = ValueAnimator.ofInt(0, scroll_max_height);
        animation.setDuration(250);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = animation.getAnimatedFraction();
                if (is_open) {//向下 打开
                    ll_home_tab.setTranslationY(scroll_max_height * (animatedValue - 1));
                    tv_home_theme_title.setTextColor(ColorUtils.blendARGB(colorAccent, Color.WHITE, animatedValue));

                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) vp_home_content.getLayoutParams();
                    layoutParams.topMargin = (int) (scroll_max_height * (animatedValue - 1));
                    vp_home_content.setLayoutParams(layoutParams);
//                    vp_home_content.setPadding(0,(int) (-scroll_max_height * animatedValue),0,0);
//                    vp_home_content.setY(scroll_max_height * (animatedValue - 1));
                } else {//向上 隐藏
                    ll_home_tab.setTranslationY(-scroll_max_height * animatedValue);
                    tv_home_theme_title.setTextColor(ColorUtils.blendARGB(Color.WHITE, colorAccent, animatedValue));

                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) vp_home_content.getLayoutParams();
                    layoutParams.topMargin = (int) (-scroll_max_height * animatedValue);
                    vp_home_content.setLayoutParams(layoutParams);
//                    vp_home_content.setPadding(0, (int) (-scroll_max_height * animatedValue),0,0);
//                    vp_home_content.setY(-scroll_max_height * animatedValue);
                }

            }
        });
        animation.start();
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), ScrollingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NotificationUtils notificationUtils = new NotificationUtils(getContext());
            notificationUtils.sendNotification("测试标题", "测试内容", intent);
        }else{
            initWindowManager("您有新的消息！");
        }
    }


    private String CHANNEL_ID = "my_chaannel_id0";

    public void initWindowManager(String string) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), CHANNEL_ID);
        Intent intent = new Intent(getActivity(), ScrollingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent,  PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentTitle("标题");// 设置通知栏标题

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//悬挂式Notification，5.0后显示

            mBuilder.setContentText(string + "点击查看。").setFullScreenIntent(pendingIntent, true);
            mBuilder.setCategory(NotificationCompat.CATEGORY_MESSAGE);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);// 设置通知小ICON（5.0必须采用白色透明图片）
        } else {
            mBuilder.setContentIntent(pendingIntent);// 设置通知栏点击意图
            mBuilder.setSmallIcon(R.drawable.ic_launcher);// 设置通知小ICON
            mBuilder.setContentText(string);
        }

        mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));// 设置通知大ICON

        mBuilder.setTicker(string + "有警报！"); // 通知首次出现在通知栏，带上升动画效果的

        mBuilder.setWhen(System.currentTimeMillis());// 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间

        mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH); // 设置该通知优先级

        mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);//在任何情况下都显示，不受锁屏影响。

        mBuilder.setAutoCancel(true);// 设置这个标志当用户单击面板就可以让通知将自动取消

        mBuilder.setOngoing(false);// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
        // 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用NotificationCompat.DEFAULT_ALL属性，可以组合
        mBuilder.setVibrate(new long[]{0, 100, 500, 100});//振动效果需要振动权限
//        mBuilder.setSound(Uri.parse("android.resource://" + getPackageName()//声音
//                + "/" + R.raw.notification_alarm));

        NotificationManager mNotificationManager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
        //Notification notification = mBuilder.getNotification();//API 11
        Notification notification = mBuilder.build();//API 16

        mNotificationManager.notify(1, notification);
    }
}
