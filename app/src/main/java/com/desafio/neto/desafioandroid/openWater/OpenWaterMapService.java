package com.desafio.neto.desafioandroid.openWater;

import com.desafio.neto.desafioandroid.models.TimeWeek;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;


public interface OpenWaterMapService {

    @GET("")
    void getWeekTime(@Query("APPID") String id, @Query("q") int city, @Query("cnt") int days,
                     Callback<TimeWeek> callback);

//    @GET("/")
//    void getCommentsList(@Query("api_key") String key, @Query("photo_id") String photoId,
//                         Callback<GetListCommentsObjectResponse> callback);
}
