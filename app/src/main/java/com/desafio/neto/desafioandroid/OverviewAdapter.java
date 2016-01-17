package com.desafio.neto.desafioandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.desafio.neto.desafioandroid.models.List;
import com.desafio.neto.desafioandroid.models.TimeWeek;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class OverviewAdapter extends RecyclerView.Adapter<OverviewViewHolder> {

    private Context context;
    private TimeWeek time;

    public OverviewAdapter(Context context, TimeWeek time) {
        this.context = context;
        this.time = time;
    }

    @Override
    public OverviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        OverviewViewHolder viewHolder = new OverviewViewHolder(context, view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OverviewViewHolder holder, int position) {
        List itemList = time.getList().get(position);
        String icon = itemList.getWeather().get(0).getIcon() + ".png";
        Picasso.with(context).load("http://openweathermap.org/img/w/"+ icon)
                .resize(100, 100)
                .centerCrop()
                .into(holder.icon);

        Date date = new Date();
        date.setDate(date.getDay() + position);

        holder.day.setText(makeDate(date));
        holder.condicion.setText(itemList.getWeather().get(0).getMain());
        holder.max.setText(String.valueOf((itemList.getMain() == null) ? "-" : itemList.getMain().getTemp_min()));
        holder.min.setText(String.valueOf((itemList.getMain() == null) ? "-" : itemList.getMain().getTemp_max()));
    }

    @Override
    public int getItemCount() {
        return time.getList().size();
    }

    private String makeDate(Date date) {
        String dateString = String.format("%d-%d-%d", date.getYear(), date.getMonth(), date.getDay());
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
        switch (dayOfWeek){
            case "Sunday":
                return "Domingo";
            case "Monday":
                return "Segunda";
            case "Tuesday":
                return "Treça";
            case "Wednesday":
                return "Quarta";
            case "Thursday":
                return "Quinta";
            case "Friday":
                return "Sexta";
            default:
                return "Sábadoo";
        }
    }
}
