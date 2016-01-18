package org.thecoreme.rodrigo.desafioandroid;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.exception.WeatherLibException;
import com.survivingwithandroid.weather.lib.model.CurrentWeather;
//import com.survivingwithandroid.weather.lib.provider.wunderground.WeatherUndergroundProviderType;
import com.survivingwithandroid.weather.lib.request.WeatherRequest;
import org.thecoreme.rodrigo.desafioandroid.OverviewFragment;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///
        try {
            WeatherClient client = WeatherContext.getInstance().getClient(this);

//            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//            String cityId = sharedPref.getString("cityId", null);
//            String cityName = sharedPref.getString("cityName", null);
//            Log.d("WLBB", "__");
//            Log.d("WLBB", cityId);
//            Log.d("WLBB", cityName);
//            Log.d("WLBB", "__");

            getFragmentManager().beginTransaction()
                    .replace(R.id.content, OverviewFragment.newInstance())
                    .commit();


//            client.getCurrentCondition(new WeatherRequest(cityId), new WeatherClient.WeatherEventListener() {
//                @Override
//                public void onWeatherRetrieved(CurrentWeather currentWeather) {
//                    float currentTemp = currentWeather.weather.temperature.getTemp();
//                    Log.d("WL", "City [" + currentWeather.weather.location.getCity() + "] Current temp [" + currentTemp + "]");
//                }
//
//                @Override
//                public void onWeatherError(WeatherLibException e) {
//                    Log.d("WL", "Weather Error - parsing data");
//                    e.printStackTrace();
//                }
//
//                @Override
//                public void onConnectionError(Throwable throwable) {
//                    Log.d("WL", ">> Connection error");
//                    throwable.printStackTrace();
//                }
//            });
        } catch (Throwable t) {
            Log.d("FUCK", "SOMETHING WRONG");
            t.printStackTrace();
        }
        ///

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, CitySettings.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://org.thecoreme.rodrigo.desafioandroid/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://org.thecoreme.rodrigo.desafioandroid/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
