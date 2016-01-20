package org.thecoreme.rodrigo.desafioandroid;

import android.os.Bundle;
import android.app.Fragment;

import com.survivingwithandroid.weather.lib.WeatherClient;

public abstract class WeatherFragment extends Fragment {
    protected WeatherClient weatherClient;

    public WeatherFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    protected WeatherEventListener getListener() {
        return ((WeatherEventListener) getActivity());
    }

    public static interface WeatherEventListener {
        public void requestCompleted();
    }

    public abstract void refreshData();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        weatherClient = WeatherContext.getInstance().getClient(getActivity());
    }
}
