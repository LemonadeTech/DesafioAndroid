package com.desafio.neto.desafioandroid.openWater;

import com.desafio.neto.desafioandroid.models.TimeDay;
import com.desafio.neto.desafioandroid.models.TimeWeek;

import retrofit.Callback;
import retrofit.RestAdapter;

public class OpenWaterMap {

    private final String KEY = "53f66f81dbb1859f915a1284b24207da";
    private final String ENDPOINT = "http://api.openweathermap.org/data/2.5";

    public void getWeekTime(String city, Callback<TimeWeek> callback){
        RestAdapter retrofit = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();
        OpenWaterMapService openWater = retrofit.create(OpenWaterMapService.class);
        openWater.getWeekTime(KEY, city, 7, callback);
    }

    public void getDayTime(String city, Callback<TimeDay> callback){
        RestAdapter retrofit = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ENDPOINT)
                .build();
        OpenWaterMapService openWater = retrofit.create(OpenWaterMapService.class);
        openWater.getDayTime(KEY, city, callback);
    }
}
