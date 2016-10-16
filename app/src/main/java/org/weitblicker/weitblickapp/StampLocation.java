package org.weitblicker.weitblickapp;

import com.google.android.gms.maps.model.LatLng;

import java.sql.Timestamp;

/**
 * Created by lzeller on 16.10.16.
 */

public class StampLocation {

    public LatLng latLng;
    public Timestamp stamp;

    public StampLocation(LatLng ll, Timestamp ts)
    {
        this.latLng = ll;
        this.stamp = ts;
    }

}
