package com.example.jawwad.mobiledatabase;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.*;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import mapareas.MapAreaManager;
import mapareas.MapAreaMeasure;
import mapareas.MapAreaWrapper;

import static com.example.jawwad.mobiledatabase.R.id.map;

public class Createnewlocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    MapAreaManager circleManager;
    EditText radius = null;
    String latitude = null;
    String longitude = null;
    String radiusval = null;
    LocationManager locationManager;
    Location location;
    LatLng myLoc;
    GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnewlocation);


        getLocaion();
//        gpsTracker = new GPSTracker(Createnewlocation.this);

        radius = (EditText) findViewById(R.id.radius);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

      /* // setUpMapIfNeeded();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      //      mMap.setMyLocationEnabled(true);
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(true);*/

    }

    public void GoBackLocattion(View v)
    {
        finish();
    }
    public void GoBackok(View v)
    {
        //data collect


        EditText lname = (EditText) findViewById(R.id.name);
        if( radiusval == null || lname.getText().toString().matches(""))
        {

            Toast.makeText(getApplicationContext(),"PLease Write name of location and select radius",Toast.LENGTH_SHORT).show();

        }
        else {


            Intent i = new Intent();
            i.putExtra("Radius", radiusval);
            i.putExtra("Longitude", longitude);
            i.putExtra("Latitude", latitude);
            i.putExtra("Name",lname.getText().toString());
            setResult(RESULT_OK, i);


            finish();
        }
    }


  /*  private void setUpMapIfNeeded() {
*//*        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(map)).getMapAsync();

            if (map != null) {
                setupMap();
            }*//*

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        //    mMap.setMyLocationEnabled(true);
         //   mMap.getUiSettings().setMyLocationButtonEnabled(true);

        }*/


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
        mMap = googleMap;

        circleManager = new MapAreaManager(mMap,

                4, Color.RED, Color.HSVToColor(70, new float[] {173, 216, 230}), //styling

                R.drawable.move, R.drawable.resize, //custom drawables for move and resize icons

                0.5f, 0.5f, 0.5f, 0.5f, //sets anchor point of move / resize drawable in the middle

                new MapAreaMeasure(100, MapAreaMeasure.Unit.pixels), //circles will start with 100 pixels (independent of zoom level)

                new MapAreaManager.CircleManagerListener() { //listener for all circle events

                    @Override
                    public void onResizeCircleEnd(MapAreaWrapper draggableCircle) {
                        radius.setText(String.valueOf(draggableCircle.getRadius()));


                        latitude = String.valueOf(draggableCircle.getCenter().latitude);
                        longitude = String.valueOf(draggableCircle.getCenter().longitude);
                        radiusval = String.valueOf(draggableCircle.getRadius());
                        // Toast.makeText(Createnewlocation.this, "do something on drag end circle: " + draggableCircle, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCreateCircle(MapAreaWrapper draggableCircle) {
                        radius.setText(String.valueOf(draggableCircle.getRadius()));
                        latitude = String.valueOf(draggableCircle.getCenter().latitude);
                        longitude = String.valueOf(draggableCircle.getCenter().longitude);
                        radiusval = String.valueOf(draggableCircle.getRadius());
                        // Toast.makeText(Createnewlocation.this, "do something on crate circle: " + draggableCircle, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onMoveCircleEnd(MapAreaWrapper draggableCircle) {
                        radius.setText(String.valueOf(draggableCircle.getRadius()));
                        latitude = String.valueOf(draggableCircle.getCenter().latitude);
                        longitude = String.valueOf(draggableCircle.getCenter().longitude);
                        radiusval = String.valueOf(draggableCircle.getRadius());
                        // Toast.makeText(Createnewlocation.this, "do something on moved circle: " + draggableCircle, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onMoveCircleStart(MapAreaWrapper draggableCircle) {
                        radius.setText(String.valueOf(draggableCircle.getRadius()));
                        latitude = String.valueOf(draggableCircle.getCenter().latitude);
                        longitude = String.valueOf(draggableCircle.getCenter().longitude);
                        radiusval = String.valueOf(draggableCircle.getRadius());
                        //Toast.makeText(Createnewlocation.this, "do something on move circle start: " + draggableCircle, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResizeCircleStart(MapAreaWrapper draggableCircle) {
                        radius.setText(String.valueOf(draggableCircle.getRadius()));
                        latitude = String.valueOf(draggableCircle.getCenter().latitude);
                        longitude = String.valueOf(draggableCircle.getCenter().longitude);
                        radiusval = String.valueOf(draggableCircle.getRadius());
                        // Toast.makeText(Createnewlocation.this, "do something on resize circle start: " + draggableCircle, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onMinRadius(MapAreaWrapper draggableCircle) {
                        radius.setText(String.valueOf(draggableCircle.getRadius()));
                        latitude = String.valueOf(draggableCircle.getCenter().latitude);
                        longitude = String.valueOf(draggableCircle.getCenter().longitude);
                        radiusval = String.valueOf(draggableCircle.getRadius());
                        // Toast.makeText(Createnewlocation.this, "do something on min radius: " + draggableCircle, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onMaxRadius(MapAreaWrapper draggableCircle) {
                        radius.setText(String.valueOf(draggableCircle.getRadius()));
                        latitude = String.valueOf(draggableCircle.getCenter().latitude);
                        longitude = String.valueOf(draggableCircle.getCenter().longitude);
                        radiusval = String.valueOf(draggableCircle.getRadius());
                        // Toast.makeText(Createnewlocation.this, "do something on max radius: " + draggableCircle, Toast.LENGTH_LONG).show();
                    }
                });

        // Add a marker in Sydney and move the camera
//        LatLng myLoc = new LatLng(24.9150883, 67.0260892);
//        LatLng myLoc = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
        if (location!=null)
         myLoc = new LatLng(location.getLatitude(), location.getLongitude());
        else{
            myLoc = new LatLng(0.0,0.0);
        }

        mMap.addMarker(new MarkerOptions().position(myLoc).title("MY CURRENT LOCATION"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 13));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

    }
    void getLocaion() {

        locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);


        Criteria locationCritera = new Criteria();
        String providerName = locationManager.getBestProvider(locationCritera,
                true);
        if (providerName != null)
            location = locationManager.getLastKnownLocation(providerName);


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,
                5, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        mMap.clear();

                        mMap .addMarker(new MarkerOptions().position(new LatLng(location.getLatitude()
                                ,location.getLongitude())));



                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                });
    }
}
