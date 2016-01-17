package com.desafio.neto.desafioandroid;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.desafio.neto.desafioandroid.models.TimeDay;
import com.desafio.neto.desafioandroid.openWater.OpenWaterMap;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.w3c.dom.Text;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@EFragment(R.layout.fragment_detail)
public class DetailFragment extends Fragment {

    @ViewById
    TextView city;

    @ViewById
    ImageView icon;

    @ViewById
    TextView min;

    @ViewById
    TextView max;

    @ViewById
    TextView latitude;

    @ViewById
    TextView longitude;

    @ViewById
    TextView humidity;

    @ViewById
    TextView pressure;

    @ViewById(R.id.speed_wind)
    TextView speedWind;

    @AfterViews
    void init() {
        searchTime("Florianopolis");
    }

    private void searchTime(String city) {
        OpenWaterMap openWaterMap = new OpenWaterMap();
        openWaterMap.getDayTime(city, new Callback<TimeDay>() {
            @Override
            public void success(TimeDay timeDay, Response response) {
                updateTime(timeDay);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getContext(), "Erro ao utualizar tempo. " + error.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("Retrofit", error.getMessage());
            }
        });
    }

    void updateTime(TimeDay timeDay) {
        city.setText(timeDay.getName());
        min.setText("Temperatura mínima:" + String.valueOf(timeDay.getMain().getTemp_min()));
        max.setText("Temperatura máxima:" + String.valueOf(timeDay.getMain().getTemp_max()));

        String image = timeDay.getWeather().get(0).getIcon() + ".png";
        Picasso.with(getContext()).load("http://openweathermap.org/img/w/"+ image)
                .resize(100, 100)
                .centerCrop()
                .into(icon);

        latitude.setText("Latitude: " + String.valueOf(timeDay.getCoord().getLat()));
        longitude.setText("Longitude: " + String.valueOf(timeDay.getCoord().getLon()));
        humidity.setText("Humidade: " + String.valueOf(timeDay.getMain().getHumidity()));
        pressure.setText("Pressão: " + String.valueOf(timeDay.getMain().getPressure()));
        speedWind.setText("Velocidade do vento: " + String.valueOf(timeDay.getWind().getSpeed()));
    }
}
