package com.desafio.neto.desafioandroid.models;

public class Temp {
    private float day;
    private float min;
    private float max;
    private float night;
    private float even;
    private float morn;

    public Temp(float day, float min, float max, float night, float even, float morn) {
        this.day = day;
        this.min = min;
        this.max = max;
        this.night = night;
        this.even = even;
        this.morn = morn;
    }

    public float getDay() {
        return day;
    }

    public void setDay(float day) {
        this.day = day;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getNight() {
        return night;
    }

    public void setNight(float night) {
        this.night = night;
    }

    public float getEven() {
        return even;
    }

    public void setEven(float even) {
        this.even = even;
    }

    public float getMorn() {
        return morn;
    }

    public void setMorn(float morn) {
        this.morn = morn;
    }
}
