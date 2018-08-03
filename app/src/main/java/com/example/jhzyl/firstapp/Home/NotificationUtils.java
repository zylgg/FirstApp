package com.example.jhzyl.firstapp.Home;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
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

    public NotificationUtils(Context context) {
        super(context);
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

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String content, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        return new Notification.Builder(getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setVisibility(Notification.VISIBILITY_PUBLIC)

                .setFullScreenIntent(pendingIntent, false)

                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(Icon.createWithResource(getApplicationContext(), R.mipmap.ic_launcher))

                .setAutoCancel(false);
    }

    public NotificationCompat.Builder getNotification_25(String title, String content, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setFullScreenIntent(pendingIntent, true)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // 设置该通知优先级
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
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
}
