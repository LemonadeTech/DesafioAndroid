package com.desafio.neto.desafioandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.desafio.neto.desafioandroid.models.TimeWeek;

import java.util.ArrayList;

public class OverviewAdapter extends RecyclerView.Adapter<OverviewViewHolder> {

    private Context context;
    private ArrayList<TimeWeek> times;

    @Override
    public OverviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        OverviewViewHolder viewHolder = new OverviewViewHolder(context, view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OverviewViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return times.size();
    }
}
