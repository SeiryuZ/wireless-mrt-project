package com.example.dhyatmika.fp_layout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchLiveMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);

        startActivity(intent);
    }

    public void launchDirections(View view) {
        Intent intent = new Intent(this, SearchDirection.class);

        startActivity(intent);
    }

    public void launchNearby(View view) {
        Intent intent = new Intent(this, NearbyMap.class);

        startActivity(intent);
    }

    public void launchLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
    }
}
