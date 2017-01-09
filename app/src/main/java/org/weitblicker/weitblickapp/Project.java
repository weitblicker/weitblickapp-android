package org.weitblicker.weitblickapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;
import java.util.List;

public class Project {
    private int id;

    private String name;
    private String description;
    private String abst;
    private float lat, lng;
    private List<ImageInfo> images;
    private List<String> hosts;

    public Project(){
        images = new LinkedList<ImageInfo>();
        hosts = new LinkedList<String>();
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

    public void addImage(ImageInfo image){
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

    public void addHost(String host){
        if(!hosts.contains(host))
            hosts.add(host);
    }

    public List<ImageInfo> getImages(){
        return images;
    }

    public String getHostNames()
    {
        StringBuilder strBuilder = new StringBuilder();
        for(int i=0; i< hosts.size(); i++) {
            String hostName = hosts.get(i);
            hostName = hostName.replace("Weitblick", "");
            strBuilder.append(hostName);
            if(i != hosts.size()-1){
                strBuilder.append(", ");
            }
        }
        return strBuilder.toString();
    }

    public boolean hasImage() {
        return !images.isEmpty();
    }
}
