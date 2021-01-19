package com.example.apicall.services;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.apicall.MainActivity;
import com.example.apicall.R;
import com.example.apicall.alarm_ring;

import com.example.apicall.notifications.Notifications;
import com.example.apicall.urls;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Myservice extends Service {


    MediaPlayer ring;

    AudioManager audioManager;
    int volume;
    private Button blinkBtn;


    public NotificationManagerCompat notificationManagerCompat;


    public Context context = this;

    public Runnable runnable =null;

    public Handler handler = null;


    public Myservice() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(getApplicationContext(),"started",Toast.LENGTH_SHORT).show();
        notificationManagerCompat = NotificationManagerCompat.from(context);
        Notifications channel = new Notifications(context);
        channel.createChannel();

        ring = MediaPlayer.create(Myservice.this, R.raw.ring);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                api_call();
                handler.postDelayed(runnable,3000);

            }
        };

        handler.postDelayed(runnable,1000);

    }

    public void earnotification(){
        Notification notification = new NotificationCompat.Builder(context, Notifications.CHHANEL_1)
                .setSmallIcon(R.drawable.ic_baseline_add_alert_24)
                .setContentTitle("Be alert")
                .setContentText("drowsy alert")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(1,notification);
    }


    public void marnotification(){
        Notification notification1 = new NotificationCompat.Builder(context, Notifications.CHHANEL_2)
                .setSmallIcon(R.drawable.ic_baseline_add_alert_24)
                .setContentTitle("Be alert")
                .setContentText("The driver is yawning frequently")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(2,notification1);
    }


    public  void alarms(Context context){
        ring.stop();
        ring = MediaPlayer.create(context, R.raw.ring);

    }


    public void api_call () {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        urls url_link = retrofit.create(urls.class);
        Call<alarm_ring>call = url_link.getAlarms();


        call.enqueue(new Callback<alarm_ring>() {
            @Override
            public void onResponse(Call<alarm_ring> call, Response<alarm_ring> response) {

                alarm_ring alarms = response.body();

                if(alarms.getSet_alarm()){

                    earnotification();
                    ring.start();
                    Toast.makeText(Myservice.this, "sleeping", Toast.LENGTH_SHORT).show();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            ring.stop();

                        }
                    },5000);
                }

                else if(alarms.getMouth_status()==2){

                    Toast.makeText(Myservice.this, "Yawn count : 2", Toast.LENGTH_SHORT).show();
                }

                else if(alarms.getMouth_status()>3){
                    marnotification();

                    Toast.makeText(Myservice.this, "Frequent yawning: 3", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Driver is not sleeping", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<alarm_ring> call, Throwable t) {
                

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        handler.removeCallbacks(runnable);
        Toast.makeText(getApplicationContext(), "Service Stopped", Toast.LENGTH_SHORT).show();


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
