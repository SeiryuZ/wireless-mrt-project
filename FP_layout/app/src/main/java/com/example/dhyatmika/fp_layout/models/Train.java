package com.example.dhyatmika.fp_layout.models;

public class Train {

    private int id;
    private int eta;
    private String lastStation;
    private String nextStation;

    public Train(int id, int eta, String lastStation, String nextStation) {
        this.id = id;
        this.eta = eta;
        this.lastStation = lastStation;
        this.nextStation = nextStation;
    }

    public int getId() {
        return this.id;
    }

    public int getEta() {
        return eta;
    }

    public String getLastStation() {
        return lastStation;
    }

    public String getNextStation() {
        return nextStation;
    }
}
