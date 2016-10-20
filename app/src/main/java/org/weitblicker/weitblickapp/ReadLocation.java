package org.weitblicker.weitblickapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by mohism on 19.10.2016.
 *  this Class display the project information on Layout called  (activity_project_contents)  */

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
    private TextView p_name ;
    private TextView p_description ;
    private  TextView p_title ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_contents);



        // call the Project data

        p_name = (TextView) findViewById(R.id.name);
        p_description = (TextView) findViewById(R.id.diescription);
        p_title = (TextView) findViewById(R.id.title);




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



        // display the project's details

        Project p1=new Project(0,"ahmed","address","AMO","DSD");

        p_name.setText("Nmae:"+ " "+p1.getName());
        p_description.setText("Description:"+ " "+p1.getDescription());
        p_title.setText("Abstract:"+ " "+p1.getAbstract());


       //display the location

        Location l=new Location(0,"ahmed",0,"address",0,"AMO","","","","DSD");



        l_town.setText("Town:"+ " "+l.getTown());
        l_street.setText("Street:" + " "+l.getStreet()+"No: "+ " "+ l.getstreetNo());
        l_country.setText("Country:" + l.getCountry());
        l_postal_code.setText("" + l.getPostalCode());
        l_longitude.setText("" + l.getLongitude());
        l_latitude.setText("" + l.getLatitude());
        l_adition.setText("" +l.getAdition());
        l_comments.setText("" + l.getComments());


    }



}
