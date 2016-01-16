package com.desafio.neto.desafioandroid.models;

import java.util.List;

public class Weather {
    private List<WhaterDetail> weather;

    public Weather(List<WhaterDetail> weather) {
        this.weather = weather;
    }

    public List<WhaterDetail> getWeather() {
        return weather;
    }

    public void setWeather(List<WhaterDetail> weather) {
        this.weather = weather;
    }
}
