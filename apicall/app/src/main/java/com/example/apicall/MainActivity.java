package com.example.apicall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apicall.services.Myservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private CardView start_event;

    private CardView stop_event;

    private CardView open_map_activity;

    private CardView exit_app;

    private CardView call_service;

    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);




        start_event = findViewById(R.id.start);
        start_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wifiManager=(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if(wifiManager.isWifiEnabled()){
                    Toast.makeText(MainActivity.this, "start the service", Toast.LENGTH_SHORT).show();
                    startService(new Intent(v.getContext(), Myservice.class));
                }else{
                    Toast.makeText(MainActivity.this, "Wifi is not enabled" ,  Toast.LENGTH_SHORT).show();
                }


            }
        });

        stop_event = findViewById(R.id.stopbutton);
        stop_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "stop the service", Toast.LENGTH_SHORT).show();
                stopService(new Intent(v.getContext(), Myservice.class));

            }
        });

        open_map_activity = findViewById(R.id.map_activity);
        open_map_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(MainActivity.this,"Opening Google Map",Toast.LENGTH_SHORT).show();


                Intent mapIntent = new Intent(Intent.ACTION_VIEW);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);



            }

        });

        call_service=findViewById(R.id.open_dialer);
        call_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(Intent.ACTION_DIAL);
                startActivity(intent);
            }
        });


        exit_app =findViewById(R.id.exit_card);
        exit_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(v.getContext(), Myservice.class));
                 onBackPressed();

            }
        });

    }


    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("Exit Confirmation");
        builder.setMessage(("Want to quit the app ?"));

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                finish();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}