package org.weitblicker.weitblickapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by mohism on 19.10.2016.
 */

public class ReadLocation extends AppCompatActivity {


    // Location Layout
    private  TextView l_town;
    private TextView l_street;
    private TextView l_country;
    private TextView l_postal_code;
    private TextView l_longitude;
    private TextView l_latitude;
    private TextView l_adition;
    private  TextView l_comments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);




        //to call the Location's data from Location Project

        l_town = (TextView) findViewById(R.id.town);
        l_street = (TextView) findViewById(R.id.street);
        ;
        l_country = (TextView) findViewById(R.id.country);
        l_postal_code = (TextView) findViewById(R.id.postal_code);
        l_longitude = (TextView) findViewById(R.id.longitude);
        l_latitude = (TextView) findViewById(R.id.latitude);
        l_adition = (TextView) findViewById(R.id.adition);
        l_comments = (TextView) findViewById(R.id.comments);
    }

}
