package com.desafio.neto.desafioandroid.models;

import java.util.List;

public class City {
    private int id;
    private String name;
    private java.util.List<Coord> coord;
    private String country;

    public City(int id, String name, String country, List<Coord> coord) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.coord = coord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Coord> getCoord() {
        return coord;
    }

    public void setCoord(List<Coord> coord) {
        this.coord = coord;
    }
}
