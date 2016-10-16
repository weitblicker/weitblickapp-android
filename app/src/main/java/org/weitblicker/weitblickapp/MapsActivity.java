package org.weitblicker.weitblickapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Timestamp;
import java.util.concurrent.ArrayBlockingQueue;

public class MapsActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        OnMapReadyCallback {

    public static final String TAG = MapsActivity.class.getSimpleName();

    /*
     * Wird an Google Play services geschickt, return von Activity.onActivityResult
     */
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    /*
     * Dieser request code wird beim Erfragen der Erlaubnis fuer GPS-Funktionen requestPermission()
     * mitgegeben und anschliessend in onRequestPermissionResult() verwendet, um die Eingabe des
     * Nutzers (erlaubt/nicht erlaubt) zu verarbeiten.
     *
     * Spaeter koennten noch aehnliche Werte fuer folgende permission groups ergaenzt werden:
     * CALENDAR, CAMERA, CONTACTS, MICROPHONE, PHONE, SENSORS, SMS, STORAGE
     */
    private static final int REQUEST_LOCATION = 0;

    private static final String PERM_FINE_LOC = Manifest.permission.ACCESS_FINE_LOCATION;

    private static ArrayBlockingQueue<StampLocation> stampLocations = new ArrayBlockingQueue<>(50);

    public static float totalMeters;
    public static LatLng lastLatLng = null;


    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private void requestFineLocation()
    {
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{PERM_FINE_LOC}, REQUEST_LOCATION);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        totalMeters = 0;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            if (Build.VERSION.SDK_INT >= 23) {

                if(checkSelfPermission(PERM_FINE_LOC) != PackageManager.PERMISSION_GRANTED) {
                    //showDialogue();
                    requestFineLocation();
                    return;
                }
            }
            doGPSstuff();
        }
        else {
            handleNewLocation(location);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION :
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Erlaubnis erteilt
                    doGPSstuff();
                } else {
                    // Erlaubnis nicht erteilt
                    Log.i(TAG, "ACCESS_FINE_LOCATION verweigert!");
                }

                break;
            default: break;
        }
    }



    //----------------------------------------------------------------------------------------------

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult cr) {
        //Versucht Google Play services dazu zu bringen, den Error zu beheben.
        if (cr.hasResolution()) {
            try {
                cr.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + cr.getErrorCode());
        }
    }

    private void updateTotalMeters() {
        // go over all saved stamplocations and check if it makes sense to increase totalMeters
        // - min/max velocity
        // - smooth out spikes
    }

    private void updatePosition(LatLng latLng) {
        if(lastLatLng != null) {
            float[] dist = new float[1];
            Location.distanceBetween(lastLatLng.latitude,
                    lastLatLng.longitude,
                    latLng.latitude,
                    latLng.longitude,
                    dist);

            StampLocation sl = new StampLocation(latLng, new Timestamp(System.currentTimeMillis()));
            if(stampLocations.remainingCapacity() > 0) {
                stampLocations.offer(sl);
            } else {
                stampLocations.poll();
                stampLocations.offer(sl);
            }

            updateTotalMeters();
            totalMeters += dist[0];
        }
        lastLatLng = latLng;
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    private void handleNewLocation(Location location) {
        // TODO
        Log.d(TAG, location.toString());

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        updatePosition(latLng);

        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Current Location"));
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("I am here!");
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    /*
     * Genauere Dokumentation unter
     * https://github.com/treehouse/android-location-example/blob/master/app/src/main/
     *   java/teamtreehouse/com/iamhere/MapsActivity.java
     */
    private void setUpMapIfNeeded() {
        if (mMap == null) {
            //mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
             //       .getMapAsync();
            SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFrag.getMapAsync(this);

            if (mMap != null) {
                setUpMap();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setUpMap();
    }


    /*
     * Setzt einen Marker in Afrika
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    private void doGPSstuff() {
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {

        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void showDialogue() {
        if (Build.VERSION.SDK_INT >= 23 &&
                !shouldShowRequestPermissionRationale(PERM_FINE_LOC)) {
            showMessageOKCancel("You need to allow access to Location",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestFineLocation();
                        }
                    });
            return;
        }
    }
}
