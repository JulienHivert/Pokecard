package com.example.iem.test.Login_Register_Auth.Fragments;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.iem.test.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_activity);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng Bourg_En_Bresse = new LatLng(46.202427, 5.221133);
        mMap.addMarker(new MarkerOptions().position(Bourg_En_Bresse).title("Bourg-en-Bresse"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Bourg_En_Bresse));
    }
}
