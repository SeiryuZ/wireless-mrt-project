package com.example.dhyatmika.fp_layout.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.example.dhyatmika.fp_layout.models.Station;

public final class AppConfig {

    private static final String domain = "http://christopherlychealdo.me/api/";
    private static final String login = "login";
    private static final String register = "register";
    private static final String logout = "logout";
    private static final String validate = "validate";
    private static final String stations = "stations";
    private static final String nearby = "nearby";
    private static final String choose = "choose";
    private static final int unauthorized = 401;
    private static final int empty = 204;

    private static final int FORWARD = 0;
    private static final int BACKWARD = 1;

    private static final String googleMapsAPI = "https://maps.googleapis.com/maps/api/";
    private static final String nearbySearch = "place/nearbysearch/json?";

    private static final String googleMaps = "https://www.google.com/maps/dir/?api=1";

    private static String apiKey(Context context) {
        String key = null;
        try {
            ApplicationInfo app = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (app.metaData != null) {
                key = app.metaData.getString("com.google.android.geo.API_KEY");
            }
        } catch (PackageManager.NameNotFoundException e) {
            // return null
        }

        return key;
    }

    public static String getLogin() {
        return domain + login;
    }

    public static String getRegister() {
        return domain + register;
    }

    public static String getLogout(String token) {
        return domain + logout + "?token=" + token;
    }

    public static String getValidate(String token) {
        return domain + validate + "?token=" + token;
    }

    public static String getStations() {
        return domain + stations;
    }

    public static String getChoose(String token) { return domain + choose + "?token=" + token; }

    public static String getNearby(Double lat, Double lon) {
        String strLat = Double.toString(lat);
        String strLon = Double.toString(lon);
        return domain + nearby + "?lat=" + strLat + "&long=" + strLon;
    }

    public static int isUnauthorized() {
        return unauthorized;
    }

    public static int isEmpty() {
        return empty;
    }

    public static String getNearbySearch(Context context) {
        String key = apiKey(context);
        return key;
    }

    public static String getGoogleMapsDestination(Station station) {
        String url = googleMaps + "&destination=" + station.getLat() + "," + station.getLon() +
                "&travelmode=WALKING";

        return url;
    }

    public static int DIRECTION_FORWARD() {
        return FORWARD;
    }

    public static int DIRECTION_BACKWARD() {
        return BACKWARD;
    }
}
