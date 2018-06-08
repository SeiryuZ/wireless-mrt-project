package com.example.dhyatmika.fp_layout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    private int PERMISSION_ACCESS_LOC;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        boolean fineLocPermision = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean coarseLocPermision = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if(!fineLocPermision){
            Log.v("TEST", "NO PERMISSION FOR LOCATION, ATTEMPT REQUEST");
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1
            );
            return;
        }

        if(!coarseLocPermision){
            Log.v("TEST", "NO PERMISSION FOR LOCATION, ATTEMPT REQUEST");
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1
            );
            return;
        }

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
