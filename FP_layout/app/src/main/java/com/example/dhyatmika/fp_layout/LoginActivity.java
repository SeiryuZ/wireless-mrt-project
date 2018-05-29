package com.example.dhyatmika.fp_layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    // reference to EditText in the login layout
    private EditText email;
    private EditText password;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = getApplicationContext();

        // Bind layout elements to variables
        email = findViewById(R.id.emailTextView);
        password = findViewById(R.id.passwordTextView);

        // get shared preference object
        // and check whether we have token saved in or not
        SharedPreferences preference = getSharedPreferences("LOGIN_CREDENTIALS", Context.MODE_PRIVATE);
        if (preference.getString("token", "") != "") {
            Intent intent = new Intent(this, DriverActivity.class);
            startActivity(intent);
        }
    }

    public void login(View view) {

        // Specify the host we want to send our HTTP request to
        String url = AppConfig.getLogin();

        // Here we created the JSON object of the HTTP request body we want to send
        JSONObject body = new JSONObject();
        try {
            body.put("email", email.getText().toString());
            body.put("password", password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // make HTTP Request to login
        Helper.MakeJsonObjectRequest(mContext, Request.Method.POST, url, body, new VolleyResponseCallback() {
            @Override
            public void onError(VolleyError error) {

                // For now just parse the response body
                NetworkResponse response = error.networkResponse;
                String errorBody = new String(response.data);

                // and show the error to the user directly using toast
                Toast toast = Toast.makeText(LoginActivity.this, errorBody, Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Save this sessionKey in UserPreference
                    String token = response.getString("token");

                    // Get shared preference object, and get the editor object
                    // And store relevant information
                    SharedPreferences preferences = getSharedPreferences("LOGIN_CREDENTIALS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    // Also save the active user name
                    editor.putString("token", token);
                    editor.commit();

                    Toast toast = Toast.makeText(
                            LoginActivity.this,
                            "Login Successful!",
                            Toast.LENGTH_LONG

                    );
                    toast.show();

                    // Login successful, so we can move to different page
                    Intent intent = new Intent(LoginActivity.this, DriverActivity.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public HashMap<String, String> setHeaders() {
                return null;
            }
        });
    }
}
