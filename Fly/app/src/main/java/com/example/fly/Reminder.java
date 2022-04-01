package com.example.fly;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Date;


public class Reminder extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.fly2);
        Notification noti = new NotificationCompat.Builder(context,MyNotification.CHANNEL_ID)
                .setSmallIcon(R.drawable.fly1)
                .setSubText("FLY WITH US!")
                .setContentTitle("LET'S FLY TOGETHER")
                .setContentText("We are waiting for you")
//                .setLargeIcon(bitmap)
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(getNotificationId(),noti);
    }


    private int getNotificationId()
    {
        return (int) new Date().getTime();
    }

}
