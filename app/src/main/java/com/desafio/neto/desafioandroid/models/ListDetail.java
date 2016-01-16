package com.desafio.neto.desafioandroid.models;

import java.util.*;
import java.util.List;

public class ListDetail {
    private Date dt;
    private Temp temp;
    private float pressure;
    private int humidity;
    private java.util.List<WhaterDetail> whather;

    public ListDetail(Date dt, Temp temp, float pressure, int humidity, List<WhaterDetail> whather) {
        this.dt = dt;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.whather = whather;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public List<WhaterDetail> getWhather() {
        return whather;
    }

    public void setWhather(List<WhaterDetail> whather) {
        this.whather = whather;
    }
}