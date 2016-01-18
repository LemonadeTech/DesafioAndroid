package org.thecoreme.rodrigo.desafioandroid;

import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import com.survivingwithandroid.weather.lib.model.BaseWeather;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
import com.survivingwithandroid.weather.lib.model.DayForecast;
import com.survivingwithandroid.weather.lib.model.WeatherForecast;

import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

public class WeatherAdapter extends ArrayAdapter<DayForecast> {
    private final static SimpleDateFormat m_dayFormat = new SimpleDateFormat("E");
    private final static SimpleDateFormat m_monthFormat = new SimpleDateFormat("MMM dd");

	private List<DayForecast> m_forecastList;
	private Context m_context;
	private BaseWeather.WeatherUnit m_units;
    private boolean m_currentDay = false;

	public WeatherAdapter(WeatherForecast forecast, Context context) {
		super(context, R.layout.row_overview_layout);

		m_forecastList = forecast.getForecast();
        m_context = context;
        m_units = forecast.getUnit();
	}

    public WeatherAdapter(CurrentWeather current, Context context) {
        super(context, R.layout.row_overview_layout);


        DayForecast dirtyWay = new DayForecast();
        dirtyWay.weather = current.weather;

        List<DayForecast> dirtyList = Arrays.asList(dirtyWay);

        m_forecastList = dirtyList;
        m_context = context;
        m_units = current.getUnit();

        m_currentDay = true;
    }

	@Override
	public int getCount() {
		return m_forecastList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) m_context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.row_overview_layout, parent, false);
		}

		TextView day = (TextView) convertView.findViewById(R.id.dayName);
        TextView date = (TextView) convertView.findViewById(R.id.dayDate);

		TextView minTemp = (TextView) convertView.findViewById(R.id.dayTempMin);
        TextView maxTemp = (TextView) convertView.findViewById(R.id.dayTempMax);

        TextView description = (TextView) convertView.findViewById(R.id.dayDescr);

		DayForecast forecast = m_forecastList.get(position);
		Date d = new Date();
		Calendar gc =  new GregorianCalendar();
		gc.setTime(d);
		gc.add(GregorianCalendar.DAY_OF_MONTH, position + 1);

        if (m_currentDay) {
            day.setText("Today");

            maxTemp.setText(Math.round(forecast.weather.temperature.getTemp()) + m_units.tempUnit);
            maxTemp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
            maxTemp.setTextColor(Color.BLACK);

            description.setText(forecast.weather.currentCondition.getCondition());

        } else {
            day.setText(m_dayFormat.format(gc.getTime()));
            date.setText("(" + m_monthFormat.format(gc.getTime()) + ")");

            minTemp.setText("Min:" + Math.round(forecast.forecastTemp.min) + m_units.tempUnit);
            maxTemp.setText("Max:" + Math.round(forecast.forecastTemp.max) + m_units.tempUnit);

            description.setText(forecast.weather.currentCondition.getDescr());
        }

        return convertView;
	}
}
