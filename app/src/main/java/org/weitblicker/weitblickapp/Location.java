package org.weitblicker.weitblickapp;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by spuetz on 10.01.17.
 */

public class Location {
    double lat, lng;
    String name;
    String street;
    String addition;
    int mapZoom;

    public LatLng getLatLng() {
        return new LatLng(lat, lng);
    }

}
