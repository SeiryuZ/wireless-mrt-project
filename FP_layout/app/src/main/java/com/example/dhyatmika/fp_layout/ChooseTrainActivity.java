package com.example.dhyatmika.fp_layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.dhyatmika.fp_layout.helpers.AppConfig;
import com.example.dhyatmika.fp_layout.helpers.Helper;
import com.example.dhyatmika.fp_layout.helpers.VolleyResponseCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.Driver;
import java.util.HashMap;

public class ChooseTrainActivity extends AppCompatActivity {

    private Context getContext() {
        return ChooseTrainActivity.this.getApplicationContext();
    }

    private SharedPreferences getPreference() {
        return Helper.getPreference(this.getContext());
    }

    private String getToken() {
        return Helper.getToken(this.getContext());
    }

    private EditText trainID;
    private int chosenTrainID;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_train);

        mContext = this.getContext();
        this.trainID = findViewById(R.id.trainIdTextView);
    }

    public void submit(View view) {

        chosenTrainID = Integer.parseInt(trainID.getText().toString());

        // specify the host we want to send our HTTP request to
        String token = Helper.getToken(this.getApplicationContext());
        String url = AppConfig.getChoose(token);

        JSONObject body = new JSONObject();
        try {
            body.put("id", chosenTrainID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Helper.MakeJsonObjectRequest(mContext, Request.Method.POST, url, body, new VolleyResponseCallback() {
            @Override
            public void onError(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, "UTF-8");
                    JSONObject data = new JSONObject(responseBody);
                    String message = data.getString("error");

                    Helper.toast(mContext, message, 1);
                } catch(JSONException e) {
                    e.printStackTrace();
                    Helper.toast(mContext, "Unexpected Error", 0);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Helper.toast(mContext, "Unexpected Error", 0);
                }
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String message = response.getString("message");
                    Helper.toast(mContext, message, 0);

                    SharedPreferences preferences = getSharedPreferences("TRAIN", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putInt("train_id", chosenTrainID);
                    editor.commit();

                    Intent intent = new Intent(ChooseTrainActivity.this, DriverActivity.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public HashMap<String, String> setHeaders() {
                String token = getToken();

                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "token " + token);
                return headers;
            }
        });
    }

    public void logout(View view) {

        // get token and URL for logging out
        String token = getToken();
        String url = AppConfig.getLogout(token);

        // make request to logout
        Helper.MakeJsonObjectRequest(mContext, Request.Method.GET, url, null, new VolleyResponseCallback() {
            @Override
            public void onError(VolleyError error) {

                // For now just parse the response body
                NetworkResponse response = error.networkResponse;
                int responseCode = response.statusCode;

                // if token has expired
                if (responseCode == AppConfig.isUnauthorized()) {

                    Helper.toast(ChooseTrainActivity.this, "Token Expired", 0);

                    SharedPreferences pref = getPreference();
                    SharedPreferences.Editor editor = pref.edit();

                    editor.remove("token");
                    editor.commit();

                    Intent intent = new Intent(ChooseTrainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String errorMessage = "Unexpected Error";
                    // and show the error to the user directly using toast
                    Helper.toast(ChooseTrainActivity.this, errorMessage, 0);
                }
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.v("message", response.toString());

                    String message = response.getString("message");

                    SharedPreferences pref = getPreference();
                    SharedPreferences.Editor editor = pref.edit();

                    editor.remove("token");
                    editor.commit();

                    Helper.toast(ChooseTrainActivity.this, "You have logged out.", 0);

                    Intent intent = new Intent(ChooseTrainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public HashMap<String, String> setHeaders() {
                String token = getToken();

                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "token " + token);
                return headers;
            }
        });
    }
}
