package com.example.dhyatmika.fp_layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.dhyatmika.fp_layout.helpers.AppConfig;
import com.example.dhyatmika.fp_layout.helpers.Helper;
import com.example.dhyatmika.fp_layout.helpers.VolleyResponseCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DriverActivity extends AppCompatActivity implements SensorEventListener{

    private Context getContext() {
        return DriverActivity.this.getApplicationContext();
    }

    private SharedPreferences getPreference() {
        return Helper.getPreference(this.getContext());
    }

    private String getToken() {
        return Helper.getToken(this.getContext());
    }

    private SensorManager mSensorManager;

    private Sensor accelerometer;

    private boolean switcher = true;
    private boolean startStopSwitcher = true;
    private boolean firstPress = true;
    private boolean change = true;

    private Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        mContext = getApplicationContext();
        String token = this.getToken();
        if (token.equals("")) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        checkTokenValidity();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(accelerometer == null){
            //do something
        }
    }

    private void checkTokenValidity() {

        // get token
        String token = this.getToken();
        String url = AppConfig.getValidate(token);

        Helper.MakeJsonObjectRequest(mContext, Request.Method.GET, url, null, new VolleyResponseCallback() {

            @Override
            public void onError(VolleyError error) {

                // For now just parse the response body
                NetworkResponse response = error.networkResponse;
                //int responseCode = response.statusCode;

                Helper.toast(DriverActivity.this, "Token Expired", 0);
                deleteToken();

                Intent intent = new Intent(DriverActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String message = response.getString("message");
                    Log.v("message", message);

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

    public void deleteToken() {

        SharedPreferences pref = getPreference();
        SharedPreferences.Editor editor = pref.edit();

        editor.remove("token");
        editor.commit();

        pref = getSharedPreferences("TRAIN", Context.MODE_PRIVATE);
        editor = pref.edit();

        editor.remove("train_id");
        editor.commit();

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

                    Helper.toast(DriverActivity.this, "Token Expired", 0);
                    deleteToken();

                    Intent intent = new Intent(DriverActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String errorMessage = "Unexpected Error";
                    // and show the error to the user directly using toast
                    Helper.toast(DriverActivity.this, errorMessage, 0);
                }
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String message = response.getString("message");

                    deleteToken();
                    Helper.toast(DriverActivity.this, "You have logged out.", 0);

                    Intent intent = new Intent(DriverActivity.this, LoginActivity.class);
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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int accelerationX = (int)sensorEvent.values[0];
        int accelerationZ = (int)sensorEvent.values[1];

        int accelTotal = (int)Math.pow((Math.pow(accelerationX, 2) + Math.pow(accelerationZ, 2)), 0.5);

        Log.i("Test", Integer.toString(accelerationX));
        Log.i("Test", Integer.toString(accelerationZ));

        TextView indicator = (TextView)findViewById(R.id.TestIndicator);
        TextView AccelTest = (TextView)findViewById(R.id.AccelerationTest);

        AccelTest.setText(Integer.toString(accelTotal));

        if(accelTotal >= 1 && change){
            if(switcher){
                indicator.setText("Departing");
            }else{
                indicator.setText("Arriving");
            }
            switcher = !switcher;
            change = false;
        }

        if(accelTotal == 0){
            if(switcher){
                indicator.setText("Idle");
            }else{
                indicator.setText("Moving");
            }
            change = true;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void toggleSensor(View view) {
        Button startStopButton = (Button)findViewById(R.id.stopStartSensor);
        if(startStopSwitcher){
            if(firstPress){
                accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                firstPress = false;
            }else{
                mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            }
            startStopButton.setText("Stop");
        }else {
            mSensorManager.unregisterListener(this);
            startStopButton.setText("Start");
        }

        startStopSwitcher = !startStopSwitcher;
    }
}
