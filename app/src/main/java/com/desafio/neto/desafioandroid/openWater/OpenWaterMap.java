package com.desafio.neto.desafioandroid.openWater;

import com.desafio.neto.desafioandroid.models.TimeWeek;

import retrofit.Callback;
import retrofit.RestAdapter;

public class OpenWaterMap {

    private final String KEY = "53f66f81dbb1859f915a1284b24207da";
    private final String ENDPOINT = "http://api.openweathermap.org/data/2.5/forecast/daily/";

    public void getWeekTime(Callback<TimeWeek> callback){
        int floripa = 6323121;
        int numberDays = 7;
        RestAdapter retrofit = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ENDPOINT)
                .build();
        OpenWaterMapService openWater = retrofit.create(OpenWaterMapService.class);
        openWater.getWeekTime(KEY, floripa, numberDays, callback);
    }
}
