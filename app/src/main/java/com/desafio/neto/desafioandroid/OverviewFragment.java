package com.desafio.neto.desafioandroid;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;
import com.desafio.neto.desafioandroid.models.TimeWeek;
import com.desafio.neto.desafioandroid.openWater.OpenWaterMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@EFragment(R.layout.fragment_overview)
public class OverviewFragment extends Fragment {

    @ViewById
    View divider;

    @ViewById
    TextView previsao;

    @ViewById
    RecyclerView timeList;

    @ViewById
    TextView city;

    @ViewById
    ImageView icon;

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
        openWaterMap.getWeekTime(city, new Callback<TimeWeek>() {
            @Override
            public void success(TimeWeek timeWeek, Response response) {
                updateTime(timeWeek);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getContext(), "Erro ao buscar previsão. " + error.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("Retrofit", error.getMessage());
            }
        });
    }

    void updateTime(TimeWeek timeWeek) {
        previsao.setVisibility(View.VISIBLE);
        divider.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        city.setText(timeWeek.getCity().getName());
        timeList.setLayoutManager(new LinearLayoutManager(getContext()));
        OverviewAdapter adapter = new OverviewAdapter(getContext(), timeWeek);
        timeList.setAdapter(adapter);
    }
}
