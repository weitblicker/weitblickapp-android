package org.weitblicker.weitblickapp;

import com.google.android.gms.maps.model.LatLng;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class Project {
    private int id;

    private String name;
    private String description;
    private String abst;
    private float lat, lng;
    private List<ProjectImage> images;

    public Project(){
        images = new LinkedList<ProjectImage>();
    }

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

    public void addImage(ProjectImage image){
        images.add(image);
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

    public LatLng getLatLng() {
        return new LatLng(lat, lng);
    }

    public String getImageUrl(){
        if(!images.isEmpty()){
            return images.get(0).url;
        }
        return "";
    }

    public List<ProjectImage> getImages(){
        return images;
    }
}
