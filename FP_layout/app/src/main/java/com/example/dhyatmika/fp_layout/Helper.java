package com.example.dhyatmika.fp_layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public final class Helper {

    private Helper() {
        /*
         *    private constructor to prevent instantiation of this class
         */
    }

    public static final void toastLong(Context ctx, String message) {
        Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_LONG);
        toast.show();
    }

    public static final void toastShort(Context ctx, String message) {
        Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static final SharedPreferences getPreference(Context ctx) {
        return ctx.getSharedPreferences("LOGIN_CREDENTIALS", ctx.MODE_PRIVATE);
    }

    public static final String getToken(Context ctx) {
        SharedPreferences preference = getPreference(ctx);
        return preference.getString("token", "");
    }
}
