package com.example.dhyatmika.fp_layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;

import com.example.dhyatmika.fp_layout.Helper;

public class DriverActivity extends AppCompatActivity {

    private Context getContext() {
        return DriverActivity.this.getApplicationContext();
    }

    private SharedPreferences getPreference() {
        return Helper.getPreference(this.getContext());
    }

    private String getToken() {
        return Helper.getToken(this.getContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        String token = this.getToken();
        if (token.equals("")) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        checkTokenValidity();
    }

    private void checkTokenValidity() {

        // get token
        String token = this.getToken();
        String url = AppConfig.getValidate(token);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    // if response is HTTP 200 OK
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Log.v("message", message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    // stuff to do when server returned an error
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // For now just parse the response body
                        NetworkResponse response = error.networkResponse;
                        int responseCode = response.statusCode;

                        Helper.toastLong(DriverActivity.this, "Token Expired");

                        SharedPreferences pref = getPreference();
                        SharedPreferences.Editor editor = pref.edit();

                        editor.remove("token");
                        editor.commit();

                        Intent intent = new Intent(DriverActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    String token = getToken();

                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", "token " + token);
                    return headers;
                }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsObjRequest);
    }

    public void logout(View view) {

        // get token and URL for logging out
        String token = getToken();
        String url = AppConfig.getLogout(token);

        // make request to logout
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    // if response is HTTP 200 OK
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.v("message", response.toString());

                            String message = response.getString("message");

                            SharedPreferences pref = getPreference();
                            SharedPreferences.Editor editor = pref.edit();

                            editor.remove("token");
                            editor.commit();

                            Helper.toastLong(DriverActivity.this, "You have logged out.");

                            Intent intent = new Intent(DriverActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    // stuff to do when server returned an error
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // For now just parse the response body
                        NetworkResponse response = error.networkResponse;
                        int responseCode = response.statusCode;

                        // if token has expired
                        if (responseCode == AppConfig.isUnauthorized()) {

                            Helper.toastLong(DriverActivity.this, "Token Expired");

                            SharedPreferences pref = getPreference();
                            SharedPreferences.Editor editor = pref.edit();

                            editor.remove("token");
                            editor.commit();

                            Intent intent = new Intent(DriverActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            String errorBody = new String(response.data);

                            // and show the error to the user directly using toast
                            Helper.toastLong(DriverActivity.this, errorBody);
                        }
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        String token = getToken();

                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Authorization", "token " + token);
                        return headers;
                    }
                };
        // instantiate basic queue by volley
        // add our request to the queue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsObjRequest);
    }
}
