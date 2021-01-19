package com.example.apicall.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class Notifications {

    Context context;
    public final static String CHHANEL_1 ="channel1";
    public final static String CHHANEL_2 ="channel2";

    public Notifications(Context context) {

        this.context = context;


    }

    public void createChannel(){

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.ECLAIR_0_1){

            NotificationChannel channel1 = new NotificationChannel(CHHANEL_1,"Channel 1", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("this is channel1");

            NotificationChannel channel2 = new NotificationChannel(CHHANEL_2,"Channel 2", NotificationManager.IMPORTANCE_LOW);
            channel1.setDescription("this is channel2");


            NotificationManager manager = context.getSystemService(NotificationManager.class);

            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);


        }

    }
}
