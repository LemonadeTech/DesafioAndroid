package org.thecoreme.rodrigo.desafioandroid;

import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.BaseWeather;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.model.Weather;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;
import com.survivingwithandroid.weather.lib.util.WindDirection;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailedFragment extends WeatherFragment {
    private SharedPreferences prefs;

    private TextView location;
    private TextView description;
    private TextView temp;
    private TextView pressure;
    private TextView windSpeed;
//    private TextView windDeg;
//    private TextView unitTemp;
    private TextView humidity;
    private TextView tempMin;
    private TextView tempMax;
    private TextView sunset;
    private TextView sunrise;
    private TextView cloud;
//    private TextView colorTextLine;
    private TextView rain;

    public static DetailedFragment newInstance() {
        DetailedFragment fragment = new DetailedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Log.d("WL", "WAAAAT?1");
//        String obj = null;
//        Bundle args = this.getArguments();
//
//        Log.d("WL", "WAAAAT?2");
//        if (args != null) {
//            obj = args.getString("weather");
//        }
//        Log.d("WL", "WAAAAT?3");
//        m_weather = new Gson().fromJson(obj, CurrentWeather.class);
//        units = m_weather.getUnit();
//        Log.d("WL", "WAAAAT?4");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.detailed_fragment, container, false);
        location = (TextView) v.findViewById(R.id.location);
        temp = (TextView) v.findViewById(R.id.temp);
        description = (TextView) v.findViewById(R.id.descrWeather);
        humidity = (TextView) v.findViewById(R.id.humidity);
        pressure = (TextView) v.findViewById(R.id.pressure);
        windSpeed = (TextView) v.findViewById(R.id.windSpeed);
//        windDeg = (TextView) v.findViewById(R.id.windDeg);
        tempMin = (TextView) v.findViewById(R.id.tempMin);
        tempMax = (TextView) v.findViewById(R.id.tempMax);
//        unitTemp = (TextView) v.findViewById(R.id.tempUnit);
        sunrise = (TextView) v.findViewById(R.id.sunrise);
        sunset = (TextView) v.findViewById(R.id.sunset);
        cloud = (TextView) v.findViewById(R.id.cloud);
//        colorTextLine = (TextView) v.findViewById(R.id.lineTxt);
        rain = (TextView) v.findViewById(R.id.rain);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }

    public void refreshData() {
        refresh();
    }

    private void refresh() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String cityId = sharedPref.getString("cityId", null);

        weatherClient.getCurrentCondition(new WeatherRequest(cityId), new WeatherClient.WeatherEventListener() {
            @Override
            public void onWeatherRetrieved(CurrentWeather currentWeather) {
                Weather weather = currentWeather.weather;
                BaseWeather.WeatherUnit units = currentWeather.getUnit();

                location.setText(weather.location.getCity() + ", " + weather.location.getCountry());
                description.setText(weather.currentCondition.getCondition() + " (" + weather.currentCondition.getDescr() + ")");
                temp.setText("" + ((int) weather.temperature.getTemp()) + units.tempUnit);

                tempMax.setText("Max: " + weather.temperature.getMaxTemp() + units.tempUnit);
                tempMin.setText("Min: " + weather.temperature.getMinTemp() + units.tempUnit);

                humidity.setText("Humidity: " + weather.currentCondition.getHumidity() + "%");
                windSpeed.setText("Wind: " + weather.wind.getSpeed() + units.speedUnit +
                        (int) weather.wind.getDeg() +
                        "° (" + WindDirection.getDir((int) weather.wind.getDeg()) + ")");

                pressure.setText("Pressure: " + weather.currentCondition.getPressure() + units.pressureUnit);

                sunrise.setText("Sunrise: " + convertDate(weather.location.getSunrise()));
                sunset.setText("Sunset: " + convertDate(weather.location.getSunset()));

                cloud.setText("Cloud: " + weather.clouds.getPerc() + "%");

                if (weather.rain[0].getTime() != null && weather.rain[0].getAmmount() != 0)
                    rain.setText("Rain: " + weather.rain[0].getTime() + ":" + weather.rain[0].getAmmount());
                else
                    rain.setText("Rain: " + "----");
            }

            @Override
            public void onWeatherError(WeatherLibException e) {
            }

            @Override
            public void onConnectionError(Throwable throwable) {
            }
        });
        
    }

    private String convertDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis(time * 1000);
        sdf.setTimeZone(cal.getTimeZone());

        return sdf.format(cal.getTime());
    }

}
