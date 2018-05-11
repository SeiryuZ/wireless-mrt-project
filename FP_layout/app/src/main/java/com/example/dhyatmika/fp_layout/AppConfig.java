package com.example.dhyatmika.fp_layout;

import android.content.Context;
import android.content.SharedPreferences;

public class AppConfig {

    private static final String domain = "http://christopherlychealdo.me/api/";
    private static final String login = "login";
    private static final String register = "register";
    private static final String logout = "logout";

    public static String getLogin() {
        return domain + login;
    }

    public static String getRegister() {
        return domain + register;
    }

    public static String getLogout(String token) {
        return domain + logout + "?token=" + token;
    }
}
