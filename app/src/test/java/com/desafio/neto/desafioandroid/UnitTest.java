package com.desafio.neto.desafioandroid;

import com.desafio.neto.desafioandroid.models.TimeWeek;
import com.desafio.neto.desafioandroid.openWater.OpenWaterMap;

import org.junit.Test;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static org.junit.Assert.*;

public class UnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        OpenWaterMap openWaterMap = new OpenWaterMap();
        openWaterMap.getWeekTime(new Callback<TimeWeek>() {

            @Override
            public void success(TimeWeek timeWeek, Response response) {
                boolean test = false;
                if (timeWeek != null)
                    test = true;
                assertEquals(test, true);
            }

            @Override
            public void failure(RetrofitError error) {
                assertEquals(false, true);
            }
        });
    }
}