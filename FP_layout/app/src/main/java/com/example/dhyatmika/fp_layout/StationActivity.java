package com.example.dhyatmika.fp_layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.dhyatmika.fp_layout.helpers.AppConfig;
import com.example.dhyatmika.fp_layout.helpers.Helper;
import com.example.dhyatmika.fp_layout.helpers.VolleyResponseCallback;
import com.example.dhyatmika.fp_layout.models.Station;
import com.example.dhyatmika.fp_layout.models.Train;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class StationActivity extends AppCompatActivity {

    private TextView stationName;
    private Context mContext;

    private Handler handler = new Handler();
    private final int INTERVAL = 30000;

    // station object
    private volatile Station station;

    // the real information of the list are stored here
    // remember to change string into train model
    ArrayList<Train> trains = new ArrayList<>();

    // RecyclerView Variables
    RecyclerView trainListView;
    RecyclerView.LayoutManager layoutManager;
    TrainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        this.mContext = getApplicationContext();

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

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                String url = AppConfig.getEta(station.getId());
                Helper.MakeJsonObjectRequest(mContext, Request.Method.GET, url, null, new VolleyResponseCallback() {
                    @Override
                    public void onError(VolleyError error) {
                        trains.clear();
                        adapter.notifyDataSetChanged();
                        try {
                            String responseBody = new String(error.networkResponse.data, "UTF-8");
                            JSONObject data = new JSONObject(responseBody);
                            String message = data.getString("error");

                            Helper.toast(mContext, message, 0);
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
                            JSONArray arr = response.getJSONArray("trains");

                            trains.clear();
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject curr = arr.getJSONObject(i);
                                int id = curr.getInt("id");
                                int eta = curr.getInt("eta") / 60;
                                String prev = curr.getString("prev");
                                String next = curr.getString("next");

                                Train train = new Train(id, eta, prev, next);
                                trains.add(train);
                            }
                            Log.v("trains", trains.toString());

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Helper.toast(mContext, "Unexpected Error", 0);
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public HashMap<String, String> setHeaders() {
                        return null;
                    }
                });
            }
        }, 0, this.INTERVAL);
    }

    // on google maps button pressed
    public void goToGoogleMaps(View view) {
        String url = AppConfig.getGoogleMapsDestination(this.station);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
