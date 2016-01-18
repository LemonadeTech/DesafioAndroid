package org.thecoreme.rodrigo.desafioandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.model.DayForecast;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;


public class OverviewFragment extends WeatherFragment {
    private ListView m_currentDay;
    private ListView m_forecastList;

    public static OverviewFragment newInstance() {
        OverviewFragment fragment = new OverviewFragment();
        return fragment;
    }

    public OverviewFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.overview_fragment, container, false);

        m_currentDay = (ListView) v.findViewById(R.id.currentDay);
        m_forecastList = (ListView) v.findViewById(R.id.forecastDays);

        return v;
    }

    public void refreshData() {
        refresh();
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }

    private void refresh() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String cityId = sharedPref.getString("cityId", null);
        String cityName = sharedPref.getString("cityName", null);

        TextView name = (TextView) getView().findViewById(R.id.txt1);
        name.setText(name.getText() + " " + cityName);

        // get current weather data
        weatherClient.getCurrentCondition(new WeatherRequest(cityId), new WeatherClient.WeatherEventListener() {
            @Override
            public void onWeatherRetrieved(CurrentWeather currentWeather) {
                Log.d("WL", "FUCK YEAHHHHHH");

                WeatherAdapter adp = new WeatherAdapter(currentWeather, getActivity());
                m_currentDay.setAdapter(adp);
                Log.d("WL", ":: " + currentWeather.weather.temperature.getTemp());
            }

            @Override
            public void onWeatherError(WeatherLibException e) {
            }

            @Override
            public void onConnectionError(Throwable throwable) {
            }
        });

        // get forecast data
        weatherClient.getForecastWeather(new WeatherRequest(cityId),
                new WeatherClient.ForecastWeatherEventListener() {
            @Override
            public void onWeatherRetrieved(WeatherForecast forecast) {
                WeatherAdapter adp = new WeatherAdapter(forecast, getActivity());
                m_forecastList.setAdapter(adp);
            }

            @Override
            public void onWeatherError(WeatherLibException t) {
            }

            @Override
            public void onConnectionError(Throwable t) {
            }
        });
    }
}