package com.example.tariel.fuelemergency;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.analytics.FirebaseAnalytics;


public class PetrolStation extends AppCompatActivity implements
        OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        NavigationView.OnNavigationItemSelectedListener


{


    private FirebaseAnalytics mFirebaseAnalytics;

    private Marker electro[] = new Marker[3];
    private Marker petrolium[] = new Marker[6];
    private Marker gas[] = new Marker[50];


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    private GoogleMap mMap;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.petrol_activity);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navview);
        navigationView.setNavigationItemSelectedListener(this);


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();

        all();

    }


    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
                PermissionUtils.requestPermission(PetrolStation.this, LOCATION_PERMISSION_REQUEST_CODE,
                        android.Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }



    @Override
    public boolean onMyLocationButtonClick() {

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }


    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }


    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        

        return false;
    }

    public void all() {

        petrolium[0] = mMap.addMarker(new MarkerOptions()
                .icon(bitmapDescriptorFromVector(PetrolStation.this, R.drawable.petrol_marker))
                .position(new LatLng(40.206721, 44.507840)));
        petrolium[1] = mMap.addMarker(new MarkerOptions()
                .icon(bitmapDescriptorFromVector(PetrolStation.this, R.drawable.petrol_marker))
                .position(new LatLng(40.168422, 44.522288)));
        petrolium[2] = mMap.addMarker(new MarkerOptions()
                .icon(bitmapDescriptorFromVector(PetrolStation.this, R.drawable.petrol_marker))
                .position(new LatLng(40.157897, 44.450429)));
        petrolium[3] = mMap.addMarker(new MarkerOptions()
                .icon(bitmapDescriptorFromVector(PetrolStation.this, R.drawable.petrol_marker))
                .position(new LatLng(40.206126, 44.521914)));
        petrolium[4] = mMap.addMarker(new MarkerOptions()
                .icon(bitmapDescriptorFromVector(PetrolStation.this, R.drawable.petrol_marker))
                .position(new LatLng(40.209472, 44.480649)));
        petrolium[5] = mMap.addMarker(new MarkerOptions()
                .icon(bitmapDescriptorFromVector(PetrolStation.this, R.drawable.petrol_marker))
                .position(new LatLng(40.181308, 44.510574)));

        gas[0] = mMap.addMarker(new MarkerOptions()
                .icon(bitmapDescriptorFromVector(PetrolStation.this, R.drawable.gas_marker))
                .position(new LatLng(40.132419, 44.477365)));
        gas[1] = mMap.addMarker(new MarkerOptions()
                .icon(bitmapDescriptorFromVector(PetrolStation.this, R.drawable.gas_marker))
                .position(new LatLng(40.215515, 44.494600)));
        gas[2] = mMap.addMarker(new MarkerOptions()
                .icon(bitmapDescriptorFromVector(PetrolStation.this, R.drawable.gas_marker))
                .position(new LatLng(40.224276, 44.505273)));
        gas[3] = mMap.addMarker(new MarkerOptions()
                .icon(bitmapDescriptorFromVector(PetrolStation.this, R.drawable.gas_marker))
                .position(new LatLng(40.234202, 44.511221)));



    }

    


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.petrol:

                for (int i = 0; i < 4; i++) {
                    gas[i].setAlpha(0);
                }
                for (int i = 0; i < 6; i++) {
                    petrolium[i].setAlpha(1);
                }
                break;

            case R.id.gas:
                for (int i = 0; i < 6; i++) {
                   petrolium[i].setAlpha(0);
                }
                for (int i = 0; i < 4; i++) {
                    gas[i].setAlpha(1);
                }


                break;
            case R.id.electro:
                Toast.makeText(this, "Comming soon", Toast.LENGTH_LONG).show();
                break;
            case R.id.aboutus:
                Intent intent = new Intent(PetrolStation.this, AboutUs.class);
                startActivity(intent);
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}



