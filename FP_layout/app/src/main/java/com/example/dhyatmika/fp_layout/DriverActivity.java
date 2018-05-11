package com.example.dhyatmika.fp_layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class DriverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        SharedPreferences preference = getSharedPreferences("LOGIN_CREDENTIALS", Context.MODE_PRIVATE);
        if (preference.getString("token", "").equals("")) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void logout(View view) {

        // get token for logging out
        SharedPreferences preference = getSharedPreferences("LOGIN_CREDENTIALS", Context.MODE_PRIVATE);
        String token = preference.getString("token", "");

        String url = AppConfig.getLogout(token);

        // make request to logout
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    // if response is HTTP 200 OK
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");

                            SharedPreferences pref = getSharedPreferences("LOGIN_CREDENTIALS", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();

                            editor.remove("token");
                            editor.commit();

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
                        String errorBody = new String(response.data);

                        // and show the error to the user directly using toast
                        Toast toast = Toast.makeText(DriverActivity.this, errorBody, Toast.LENGTH_LONG);
                        toast.show();
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        SharedPreferences preference = getSharedPreferences("LOGIN_CREDENTIALS", Context.MODE_PRIVATE);
                        String token = preference.getString("token", "");

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
