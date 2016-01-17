package com.desafio.neto.desafioandroid.models;

import java.util.ArrayList;

public class List {
    private long dt;
    private Main main;
    private java.util.List<Weather> weather = new ArrayList<Weather>();

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public java.util.List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }
}
