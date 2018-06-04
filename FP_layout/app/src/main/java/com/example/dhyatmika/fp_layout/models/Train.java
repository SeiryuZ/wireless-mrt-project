package com.example.dhyatmika.fp_layout.models;

public class Train {

    private int id;
    private String name;
    private int direction;

    public Train(int id, String name, int direction) {
        this.id = id;
        this.name = name;
        this.direction = direction;
    }

    public int getId() {
        return this.id;
    }

    public int getDirection() {
        return this.direction;
    }
}
