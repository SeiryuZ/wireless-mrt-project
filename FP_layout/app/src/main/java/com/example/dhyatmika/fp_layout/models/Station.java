package com.example.dhyatmika.fp_layout.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Station implements Parcelable {

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

    protected Station(Parcel in) {
        id = in.readInt();
        name = in.readString();
        lat = in.readDouble();
        lon = in.readDouble();
    }

    public static final Creator<Station> CREATOR = new Creator<Station>() {
        @Override
        public Station createFromParcel(Parcel in) {
            return new Station(in);
        }

        @Override
        public Station[] newArray(int size) {
            return new Station[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeDouble(lat);
        parcel.writeDouble(lon);
    }
}
