package com.example.dhyatmika.fp_layout;

import android.content.Context;
import android.content.SharedPreferences;

public final class AppConfig {

    private static final String domain = "http://christopherlychealdo.me/api/";
    private static final String login = "login";
    private static final String register = "register";
    private static final String logout = "logout";
    private static final String validate = "validate";
    private static final int unauthorized = 401;
    private static final int empty = 204;

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

    public static int isUnauthorized() {
        return unauthorized;
    }

    public static int isEmpty() {
        return empty;
    }
}
