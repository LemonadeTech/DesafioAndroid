package com.desafio.neto.desafioandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OverviewViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    public TextView day;
    public TextView condicion;
    public TextView min;
    public TextView max;
    public ImageView icon;

    public OverviewViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        day = (TextView) itemView.findViewById(R.id.day);
        condicion = (TextView) itemView.findViewById(R.id.condicion);
        min = (TextView) itemView.findViewById(R.id.min);
        max = (TextView) itemView.findViewById(R.id.max);
        icon = (ImageView) itemView.findViewById(R.id.icon);
    }
}
