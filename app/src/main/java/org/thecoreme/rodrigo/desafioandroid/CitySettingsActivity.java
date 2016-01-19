package org.thecoreme.rodrigo.desafioandroid;

import java.util.List;
import java.util.ArrayList;

import com.survivingwithandroid.weather.lib.model.City;
import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.AdapterView;
import android.support.v4.app.NavUtils;
import android.content.SharedPreferences;
import android.view.inputmethod.EditorInfo;
import android.preference.PreferenceManager;

public class CitySettingsActivity extends Activity {
	private ListView m_cityListView;
	private ProgressBar m_bar;
	private CityAdapter m_adapter;
    private WeatherClient m_client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_settings);

        m_client = WeatherContext.getInstance().getClient(this);

        m_cityListView = (ListView) findViewById(R.id.cityList);
        m_bar = (ProgressBar) findViewById(R.id.progressBar);
        m_adapter = new CityAdapter(CitySettingsActivity.this, new ArrayList<City>());
        m_cityListView.setAdapter(m_adapter);

        ImageView searchView = (ImageView) findViewById(R.id.imgSearch);
        final EditText edt = (EditText) findViewById(R.id.cityEdtText);

        edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(v.getText().toString());

                    return true;
                }

                return false;
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(edt.getEditableText().toString());
            }
        });

        m_cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(CitySettingsActivity.this);
                SharedPreferences.Editor editor = sharedPref.edit();
                City city = (City) parent.getItemAtPosition(pos);

                editor.putString("cityId", city.getId());
                editor.putString("cityName", city.getName());
                editor.commit();

                NavUtils.navigateUpFromSameTask(CitySettingsActivity.this);
            }
        });
    }

    private void search(String pattern) {
        m_bar.setVisibility(View.VISIBLE);

        m_client.searchCity(pattern, new WeatherClient.CityEventListener() {
            @Override
            public void onCityListRetrieved(List<City> cityList) {
                m_bar.setVisibility(View.GONE);
                m_adapter.setCityList(cityList);
                m_adapter.notifyDataSetChanged();
            }

            @Override
            public void onWeatherError(WeatherLibException t) {
                m_bar.setVisibility(View.GONE);
            }

            @Override
            public void onConnectionError(Throwable t) {
                m_bar.setVisibility(View.GONE);
            }
        });
    }
}
