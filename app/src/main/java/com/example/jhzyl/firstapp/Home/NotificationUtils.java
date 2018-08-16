package com.example.jhzyl.firstapp.Home;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.example.jhzyl.firstapp.R;

/**
 * Created by LaoZhao on 2017/11/19.
 */

public class NotificationUtils extends ContextWrapper {

    private NotificationManager manager;
    public static final String id = "channel_id1";
    public static final String name = "channel_name_1";
    private Context context;

    public NotificationUtils(Context context) {
        super(context);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel() {
        NotificationChannel channel1 = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        channel1.setDescription("通知渠道的描述1");
        channel1.enableLights(false);//通知灯
        channel1.enableVibration(false);//振动
//        channel1.setLightColor(Color.WHITE);//设置光色
        getManager().createNotificationChannel(channel1);
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public void sendNotification(String title, String content, Intent intent) {
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
            Notification notification = getChannelNotification
                    (title, content, intent).build();
            getManager().notify(1, notification);
        } else {
            Notification notification = getNotification_25(title, content, intent).build();
            getManager().notify(1, notification);
        }
    }

    public NotificationCompat.Builder getChannelNotification(String title, String content, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        return new NotificationCompat.Builder(getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setUsesChronometer(true)
                .setTimeoutAfter(3000L)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)// 设置通知栏点击意图
//                .setFullScreenIntent(pendingIntent, true)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // 设置该通知优先级
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))// 设置通知大ICON
                .setOngoing(false)
                .setAutoCancel(true);
    }

    public NotificationCompat.Builder getNotification_25(String title, String content, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        return new NotificationCompat.Builder(context, id)
                .setContentTitle(title)
                .setContentText(content)
                .setTicker(title + "有警报！") // 通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())
                .setVibrate(new long[]{0, 100, 500, 100})//振动效果需要振动权限
//                .setTimeoutAfter(3000L)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)// 设置通知栏点击意图
//                .setFullScreenIntent(pendingIntent, true)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // 设置该通知优先级
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))// 设置通知大ICON
                .setAutoCancel(true)
                .setOngoing(false);

    }

}
