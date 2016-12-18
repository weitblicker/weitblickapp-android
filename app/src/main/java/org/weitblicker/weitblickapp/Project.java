package org.weitblicker.weitblickapp;

import com.google.android.gms.maps.model.LatLng;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class Project {
    private int id;

    private String name;
    private String description;
    private String abst;
    private String location;
    private String imageUrl;
    private float lat, lng;

    public Project(){
        // test data
        lat = 0;
        lng = 0;
        imageUrl = "https://weitblicker.org/sites/default/files/styles/width100_custom_user_normal_1x/public/tappictures/gelande.jpg?itok=aEHsegE0";
    }

    public Project(int id, String name, String description, String abst, String location, float lat, float lng, String imageUrl){
        this.id = id;
        this.name = name;
        this.description = description;
        this.abst = abst;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    public void setImageUrl(String url){ this.imageUrl = url; }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAbstract(String abst) {
        this.abst = abst;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLocation(float lng, float lat){
        this.lng = lng;
        this.lat = lat;
    }

    // get properties
    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAbstract() {
        return abst;
    }

    public String getLocation() {
        return location;
    }

    public LatLng getLatLng() {
        return new LatLng(lat, lng);
    }

    public String getImageUrl(){
        return imageUrl;
    }
}
