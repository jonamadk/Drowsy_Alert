package com.example.apicall;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface urls {



    String BASE_URL ="http://192.168.254.8:5000/";

//    String BASE_URL ="http://manojadk.pythonanywhere.com/";

    @GET("post/1")
    Call<alarm_ring>getAlarms();
}
