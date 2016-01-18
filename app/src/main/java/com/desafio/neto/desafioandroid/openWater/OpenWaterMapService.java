package com.desafio.neto.desafioandroid.openWater;

import com.desafio.neto.desafioandroid.models.TimeDay;
import com.desafio.neto.desafioandroid.models.TimeWeek;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface OpenWaterMapService {

    @GET("/forecast/daily&lang=zh_cn&units=metric")
    void getWeekTime(@Query("APPID") String id, @Query("q") String city, @Query("cnt") int days,
                     Callback<TimeWeek> callback);

    @GET("/weather")
    void getDayTime(@Query("APPID") String id, @Query("q") String city, Callback<TimeDay> callback);
}
