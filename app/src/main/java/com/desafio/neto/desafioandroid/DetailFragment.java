package com.desafio.neto.desafioandroid;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    View divider;

    @ViewById
    TextView previsao;

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

    @ViewById
    ProgressBar progress;

    @AfterViews
    void init() {
        previsao.setVisibility(View.GONE);
        divider.setVisibility(View.GONE);
        searchTime((DataCity.getCity() == null) ? "Florianopolis" : DataCity.getCity());
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
        progress.setVisibility(View.GONE);
        previsao.setVisibility(View.VISIBLE);
        divider.setVisibility(View.VISIBLE);
        city.setText(timeDay.getName());
        String temperaturaMinima = R.string.temeperatura_minima + ": "
                + String.valueOf(timeDay.getMain().getTemp_min());
        min.setText(temperaturaMinima);
        String temperaturaMaxima = R.string.temeperatura_maxima + ": "
                + String.valueOf(timeDay.getMain().getTemp_max());
        max.setText(temperaturaMaxima);

        String image = timeDay.getWeather().get(0).getIcon() + ".png";
        Picasso.with(getContext()).load("http://openweathermap.org/img/w/"+ image)
                .resize(100, 100)
                .centerCrop()
                .into(icon);

        String lat = R.string.latitude + ": " + String.valueOf(timeDay.getCoord().getLat());
        latitude.setText(lat);
        String lon = R.string.longitude +": " + String.valueOf(timeDay.getCoord().getLon());
        longitude.setText(lon);
        String humi = R.string.humidade + ": " + String.valueOf(timeDay.getMain().getHumidity());
        humidity.setText(humi);
        String pr = R.string.pressao + ": " + String.valueOf(timeDay.getMain().getPressure());
        pressure.setText(pr);
        String speed = R.string.velocidade_vento + ": " + String.valueOf(timeDay.getWind().getSpeed());
        speedWind.setText(speed);
    }
}
