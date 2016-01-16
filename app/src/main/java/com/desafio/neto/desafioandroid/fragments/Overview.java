package com.desafio.neto.desafioandroid.fragments;

import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.desafio.neto.desafioandroid.R;
import com.desafio.neto.desafioandroid.models.TimeWeek;
import com.desafio.neto.desafioandroid.openWater.OpenWaterMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@EFragment(R.layout.fragment_overview)
public class Overview extends Fragment {

    @ViewById
    RecyclerView cardList;

    @AfterViews
    void init() {
        cardList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cardList.setLayoutManager(layoutManager);
    }

    @Background
    void searchTime() {
        OpenWaterMap openWaterMap = new OpenWaterMap();
        openWaterMap.getWeekTime(new Callback<TimeWeek>() {
            @Override
            public void success(TimeWeek timeWeek, Response response) {
                updateTime(timeWeek);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getContext(), "Erro ao utualizar tempo.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @UiThread
    void updateTime(TimeWeek timeWeek) {
        Toast.makeText(getContext(), timeWeek.getCity().getName(), Toast.LENGTH_LONG).show();
//        cardList
//        adapter.updateBookmarks(bookmarks);
//        bookmarkList.startAnimation(fadeIn);
    }
}
