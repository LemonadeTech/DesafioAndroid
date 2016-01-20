package org.thecoreme.rodrigo.desafioandroid;

import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.model.Weather;
import com.survivingwithandroid.weather.lib.model.BaseWeather;
import com.survivingwithandroid.weather.lib.util.WindDirection;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class DetailedFragment extends WeatherFragment {
    private SharedPreferences m_prefs;

    private TextView m_location;
    private TextView m_description;
    private TextView m_temp;
    private TextView m_pressure;
    private TextView m_windSpeed;
    private TextView m_humidity;
    private TextView m_tempMin;
    private TextView m_tempMax;
    private TextView m_sunset;
    private TextView m_sunrise;
    private TextView m_cloud;
    private TextView m_rain;

    public static DetailedFragment newInstance() {
        DetailedFragment fragment = new DetailedFragment();

        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        m_prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.detailed_fragment, container, false);

        m_location = (TextView) v.findViewById(R.id.location);
        m_temp = (TextView) v.findViewById(R.id.temp);
        m_description = (TextView) v.findViewById(R.id.descrWeather);
        m_humidity = (TextView) v.findViewById(R.id.humidity);
        m_pressure = (TextView) v.findViewById(R.id.pressure);
        m_windSpeed = (TextView) v.findViewById(R.id.windSpeed);
        m_tempMin = (TextView) v.findViewById(R.id.tempMin);
        m_tempMax = (TextView) v.findViewById(R.id.tempMax);
        m_sunrise = (TextView) v.findViewById(R.id.sunrise);
        m_sunset = (TextView) v.findViewById(R.id.sunset);
        m_cloud = (TextView) v.findViewById(R.id.cloud);
        m_rain = (TextView) v.findViewById(R.id.rain);

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

        weatherClient.getCurrentCondition(new WeatherRequest(cityId),
                new WeatherClient.WeatherEventListener() {
            @Override
            public void onWeatherRetrieved(CurrentWeather currentWeather) {
                Weather weather = currentWeather.weather;
                BaseWeather.WeatherUnit units = currentWeather.getUnit();

                m_location.setText(weather.location.getCity() + ", " +
                        weather.location.getCountry());
                m_description.setText(weather.currentCondition.getCondition() +
                        " (" + weather.currentCondition.getDescr() + ")");
                m_temp.setText("" + ((int) weather.temperature.getTemp()) + units.tempUnit);

                m_tempMax.setText("Max: " + weather.temperature.getMaxTemp() + units.tempUnit);
                m_tempMin.setText("Min: " + weather.temperature.getMinTemp() + units.tempUnit);

                m_humidity.setText("Humidity: " + weather.currentCondition.getHumidity() + "%");
                m_windSpeed.setText("Wind: " + weather.wind.getSpeed() + units.speedUnit +
                        (int) weather.wind.getDeg() +
                        "° (" + WindDirection.getDir((int) weather.wind.getDeg()) + ")");

                m_pressure.setText("Pressure: " + weather.currentCondition.getPressure() +
                        units.pressureUnit);

                m_sunrise.setText("Sunrise: " + convertDate(weather.location.getSunrise()));
                m_sunset.setText("Sunset: " + convertDate(weather.location.getSunset()));

                m_cloud.setText("Cloud: " + weather.clouds.getPerc() + "%");

                if (weather.rain[0].getTime() != null && weather.rain[0].getAmmount() != 0)
                    m_rain.setText("Rain: " + weather.rain[0].getTime() + ":" +
                            weather.rain[0].getAmmount());
                else
                    m_rain.setText("Rain: " + "----");
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
