package com.desafio.neto.desafioandroid;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.desafio.neto.desafioandroid.models.TimeWeek;
import com.desafio.neto.desafioandroid.openWater.OpenWaterMap;

import org.junit.Before;
import org.junit.Test;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static org.junit.Assert.assertThat;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    OpenWaterMap openWater;

    public ApplicationTest() {
        super(Application.class);
    }

    @Before
    private void config() {
        openWater = new OpenWaterMap();
    }

    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        openWater.getWeekTime("Joinville", new Callback<TimeWeek>() {

            @Override
            public void success(TimeWeek timeWeek, Response response) {
                boolean test = false;
                if (response != null){
                    test = true;
                }
                assertEquals(test, true);
            }

            @Override
            public void failure(RetrofitError error) {
                assertEquals(false, true);
            }
        });

//        assertThat(EmailValidator.isValidEmail("name@email.com"), is(true));
    }
}