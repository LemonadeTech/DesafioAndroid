package br.com.rr.deslemtech.model.http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import br.com.rr.deslemtech.controller.listener.ISimpleHttpResultListener;
import br.com.rr.deslemtech.model.LemTechBO;
import br.com.rr.deslemtech.model.domain.City;
import br.com.rr.deslemtech.model.domain.Forecast;
import br.com.rr.deslemtech.model.domain.ListWeather;
import br.com.rr.deslemtech.model.domain.MapForecast;
import br.com.rr.deslemtech.model.domain.Temperature;
import br.com.rr.deslemtech.model.domain.Weather;
import br.com.rr.deslemtech.utils.WrapperLog;


public class Forecast16AsyncHttpRespHandler extends DefaultResultAsyncHttpRespHandler<City> {

	public Forecast16AsyncHttpRespHandler(final ISimpleHttpResultListener<City> listener) {
		super(listener);
	}

	@Override
	protected City parseData(JSONObject jsonObjRoot) throws JSONException {

		// get data
		City city = new City();

        city.mapForecast = new MapForecast();

        JSONObject objCity = jsonObjRoot.getJSONObject("city");

        city.id = objCity.getString("id");
        city.name = objCity.getString("name");
        city.country = objCity.getString("country");

        String strSize = jsonObjRoot.optString("cnt");

        if(strSize != null && !strSize.isEmpty()) {

            JSONArray objList = jsonObjRoot.getJSONArray("list");

            Calendar calTemp = Calendar.getInstance();
            String forecastKey;

            JSONObject objForecast;
            JSONObject objTemperature;
            Forecast forecast;
            int size = objList.length();
            WrapperLog.error("objList.length(): "+size);
            for(int count = 0; count < size; count++) {

                objForecast = objList.getJSONObject(count);

                forecast = new Forecast();

                // get forecast data
                forecast.date = objForecast.getString("dt");
                forecast.humidity = objForecast.getString("humidity");
                forecast.windSpeed = objForecast.getString("speed");
                forecast.windDegrees = objForecast.getString("deg");
                forecast.clouds = objForecast.getString("clouds");

                // get temperature data
                objTemperature = objForecast.getJSONObject("temp");
                forecast.temperature = new Temperature();

                forecast.temperature.day = objTemperature.getString("day");
                forecast.temperature.min = objTemperature.getString("min");
                forecast.temperature.max = objTemperature.getString("max");

                // get weather data
                forecast.listWeather = parseListWeather(objForecast.getJSONArray("weather"));


                // format date
                calTemp.setTimeInMillis(Long.parseLong(forecast.date) * 1000);
                forecastKey = LemTechBO.getInstance().forecastKeyDateFormat.format(calTemp.getTime());

                // put in city
                city.mapForecast.put(forecastKey, forecast);

            }

        }

		return city;
	}

    protected ListWeather parseListWeather(JSONArray arrayWeather) throws JSONException {

        ListWeather listWeather = new ListWeather();

        JSONObject objWeather;
        Weather weather;

        int size = arrayWeather.length();
        for(int count = 0; count < size; count++) {

            objWeather = arrayWeather.getJSONObject(count);

            weather = new Weather();

            weather.main = objWeather.getString("main");
            weather.desc = objWeather.getString("description");
            weather.icon = objWeather.getString("icon");

            listWeather.add(weather);
        }

        return listWeather;
    }

}
