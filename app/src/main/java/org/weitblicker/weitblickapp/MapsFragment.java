package org.weitblicker.weitblickapp;

import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Timestamp;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * A fragment that launches other parts of the demo application.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private MapView mMapView;
    private GoogleMap googleMap;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private static ArrayBlockingQueue<StampLocation> stampLocations = new ArrayBlockingQueue<>(50);
    public static LatLng lastLatLng = null;
    /*
     * Dieser request code wird beim Erfragen der Erlaubnis fuer GPS-Funktionen requestPermission()
     * mitgegeben und anschliessend in onRequestPermissionResult() verwendet, um die Eingabe des
     * Nutzers (erlaubt/nicht erlaubt) zu verarbeiten.
     *
     * Spaeter koennten noch aehnliche Werte fuer folgende permission groups ergaenzt werden:
     * CALENDAR, CAMERA, CONTACTS, MICROPHONE, PHONE, SENSORS, SMS, STORAGE
     */
    private static final int REQUEST_LOCATION = 0;
    public  static final String TAG = MapsFragment.class.getSimpleName();
    private static final String PERM_FINE_LOC = android.Manifest.permission.ACCESS_FINE_LOCATION;

    private static int totalMeters = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate and return the layout
        View v = inflater.inflate(R.layout.fragment_maps, container, false);
        mMapView = (MapView) v.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(googleMap == null);
        mMapView.getMapAsync(this);


        // Perform any camera updates here
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("should init the gmap");
        this.googleMap = googleMap;
        setUpMapIfNeeded();
    }

    /*
     * Genauere Dokumentation unter
     * https://github.com/treehouse/android-location-example/blob/master/app/src/main/
     *   java/teamtreehouse/com/iamhere/MapsActivity.java
     */
    private void setUpMapIfNeeded() {
        if (this.googleMap == null) {
            //mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
            //       .getMapAsync();
            SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFrag.getMapAsync(this);

            if (this.googleMap != null) {
                setUpMap();
            }
        }
    }



    /*
     * Setzt einen Marker in Afrika
     */
    private void setUpMap() {
        // latitude and longitude
        double lat = 17.385044;
        double lon = 78.486671;

        // create marker
        MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lon)).title("Hello Maps");

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        // adding marker
        googleMap.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lat, lon)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        if (Build.VERSION.SDK_INT >= 23) {
            // ab API 23 werden Permissions on the fly erfragt
            if (context.checkSelfPermission(PERM_FINE_LOC) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{PERM_FINE_LOC}, REQUEST_LOCATION);
            } else {
                // TODO
            }
            // vor API 23 werden die Permissions bei der Installation erteilt
        } else {
           // TODO
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
                    // TODO
                } else {
                    // Erlaubnis nicht erteilt
                    Log.i(TAG, "ACCESS_FINE_LOCATION verweigert!");
                }

                break;
            default: break;
        }
    }

    private void doGPSstuff() {
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        // TODO
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        doGPSstuff();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult cr) {
        //Versucht Google Play services dazu zu bringen, den Error zu beheben.
        if (cr.hasResolution()) {
            try {
                cr.startResolutionForResult(getActivity(), 42);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + cr.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
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
        googleMap.addMarker(options);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
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

    private void updateTotalMeters() {
        // TODO
        // go over all saved stamplocations and check if it makes sense to increase totalMeters
        // - min/max velocity
        // - smooth out spikes
    }
}