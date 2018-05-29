package com.example.dhyatmika.fp_layout.models;

public class Station {

    private int id;
    private String name;
    private double lat;
    private double lon;

    public Station(int id, String name, double lat, double lon) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getLat() {
        return this.lat;
    }

    public double getLon() {
        return this.lon;
    }

}
