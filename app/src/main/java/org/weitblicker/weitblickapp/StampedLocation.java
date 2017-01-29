package org.weitblicker.weitblickapp;

import com.google.android.gms.maps.model.LatLng;

import java.sql.Timestamp;


public class StampedLocation {

    public LatLng latLng;
    public Timestamp stamp;

    public StampedLocation(LatLng ll, Timestamp ts)
    {
        this.latLng = ll;
        this.stamp = ts;
    }

}
