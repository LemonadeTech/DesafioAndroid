package org.thecoreme.rodrigo.desafioandroid;

import android.content.Context;

import com.survivingwithandroid.weather.lib.WeatherClient;
import com.survivingwithandroid.weather.lib.WeatherConfig;
import com.survivingwithandroid.weather.lib.client.okhttp.WeatherDefaultClient;
import com.survivingwithandroid.weather.lib.exception.WeatherProviderInstantiationException;
import com.survivingwithandroid.weather.lib.provider.openweathermap.OpenweathermapProviderType;


public class WeatherContext {
    private static WeatherContext m_weatherContext;
    private WeatherClient m_client;

    private WeatherContext() {}

    public static WeatherContext getInstance() {
        if (m_weatherContext == null)
            m_weatherContext = new WeatherContext();

        return m_weatherContext;
    }

    public WeatherClient getClient(Context ctx) {
        if (m_client != null)
            return m_client;

        try {
            WeatherConfig config = new WeatherConfig();
            config.ApiKey = "2ffdd0916ecc926d15b723006c99fd42"; // TODO
            config.maxResult = 5;
            config.numDays = 6;
            config.unitSystem = WeatherConfig.UNIT_SYSTEM.M;

            m_client = new WeatherClient.ClientBuilder()
                    .attach(ctx)
                    .config(config)
                    .provider(new OpenweathermapProviderType())
                    .httpClient(WeatherDefaultClient.class)
                    .build();
        }
        catch (WeatherProviderInstantiationException e) {
            e.printStackTrace();
        }

        return m_client;
    }
}
