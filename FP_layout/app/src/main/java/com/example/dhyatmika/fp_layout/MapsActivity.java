package com.example.dhyatmika.fp_layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.dhyatmika.fp_layout.models.Station;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        OnMarkerClickListener {

    private Context mContext;
    private GoogleMap mMap;
    private ArrayList<Station> stations = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mContext = getApplicationContext();
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.fetchStations();

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng benhil = new LatLng(-6.217096, 106.815228);
//        LatLng senayan = new LatLng(-6.227878, 106.800994);

//        mMap.addMarker(new MarkerOptions().position(benhil).title("Bendungan Hilir"));
//        mMap.addMarker(new MarkerOptions().position(senayan).title("Senayan"));

//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(senayan)
//                .zoom(10).build();
//
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        // set listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        // retrieve the data from the marker
        Integer clickCount = (Integer) marker.getTag();

        Toast.makeText(this, marker.getPosition().toString(), Toast.LENGTH_SHORT).show();

        // return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    private void fetchStations() {

        // get stations from API
        this.getStations();
    }

    // get from DB first
    private void getStations() {

        String url = AppConfig.getStations();

        Helper.MakeJsonObjectRequest(this.mContext, Request.Method.GET, url,
                null, new VolleyResponseCallback() {

            @Override
            public void onError(VolleyError error) {
                String msg = null;

                if (error instanceof NetworkError)
                    msg = "Failed to connect to server";
                else if (error instanceof TimeoutError)
                    msg = "Timeout for connection exceeded";
                else
                    msg = error.getMessage();

                Helper.toastShort(MapsActivity.this, msg);
            }

            @Override
            public void onResponse(JSONObject response) {
                try {
                    // get response to json array
                    JSONArray stations = response.getJSONArray("stations");

                    // array list of stations
                    ArrayList<Station> result = new ArrayList<Station>();

                    for(int i=0; i<stations.length(); i++) {
                        JSONObject curr = stations.getJSONObject(i);
                        Station station = new Station(
                                curr.getInt("id"),
                                curr.getString("name"),
                                curr.getDouble("lat"),
                                curr.getDouble("long")
                        );
                        result.add(station);
                    }

                    onStationsLoaded(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Helper.toastShort(MapsActivity.this, "An error occurred");
                }
            }

            @Override
            public HashMap<String, String> setHeaders() {
                return null;
            }
        });
    }

    private void onStationsLoaded(ArrayList<Station> result) {
        this.stations = result;

        // get name and additional data from Places API

        // place markers
        Bitmap marker = Helper.customMarker(getResources());

        int stationCount = this.stations.size();
        for(int i=0; i<stationCount; i++) {
            Station curr = this.stations.get(i);

            LatLng latlng = new LatLng(curr.getLat(), curr.getLon());
            mMap.addMarker(new MarkerOptions()
                    .position(latlng)
                    .title(curr.getName())
                    .icon(BitmapDescriptorFactory.fromBitmap(marker)));
        }

        Station mid = this.stations.get(stationCount/2);
        LatLng latLng = new LatLng(mid.getLat(), mid.getLon());

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom((float) 12.5).build();

        this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

}
