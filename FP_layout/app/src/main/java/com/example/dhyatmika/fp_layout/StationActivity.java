package com.example.dhyatmika.fp_layout;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.dhyatmika.fp_layout.helpers.AppConfig;
import com.example.dhyatmika.fp_layout.models.Station;

import java.util.ArrayList;

public class StationActivity extends AppCompatActivity {

    private TextView stationName;

    // station object
    private Station station;

    // the real information of the list are stored here
    // remember to change string into train model
    ArrayList<String> trains = new ArrayList<>();

    // RecyclerView Variables
    RecyclerView trainListView;
    RecyclerView.LayoutManager layoutManager;
    TrainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        // get station data
        this.station = getIntent().getParcelableExtra("station");

        // bind text and set station name
        stationName = findViewById(R.id.stationName);
        stationName.setText("Station Name: " + station.getName());

        // bind recycler view
        trainListView = findViewById(R.id.trainList);

        // set the layout manager. use GridLayoutManager for alternative
        layoutManager = new LinearLayoutManager(this);
        trainListView.setLayoutManager(layoutManager);

        // set adapter (data source)
        adapter = new TrainAdapter(trains);
        trainListView.setAdapter(adapter);

        fetchTrains();
    }

    // fetch list of active trains
    private void fetchTrains() {

        trains.add("jakarta");
        trains.add("jambi");
        trains.add("palembang");

        Log.v("message", "this is called");
        adapter.notifyDataSetChanged();
    }

    // on google maps button pressed
    public void goToGoogleMaps(View view) {
        String url = AppConfig.getGoogleMapsDestination(this.station);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
