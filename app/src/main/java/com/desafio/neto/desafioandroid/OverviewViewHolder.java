package com.desafio.neto.desafioandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class OverviewViewHolder extends RecyclerView.ViewHolder {

    private Context context;

    public OverviewViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
    }
}
