package org.weitblicker.weitblickapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MeetInfo implements Comparable<MeetInfo>{
    private int id;

    private String name;
    private String description;
    private String abst;
    private Location location;
    private List<ImageInfo> images;
    private Date dateTime;
    private String hostName;
    private String hostEmail;

    public MeetInfo(){
        images = new LinkedList<ImageInfo>();
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

    public boolean hasImage(){
        return !images.isEmpty() && !images.get(0).equals("");
    }

    public String getImageUrl(){
        if(!images.isEmpty()){
            return images.get(0).url;
        }
        return "";
    }

    public List<ImageInfo> getImages(){
        return images;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public int compareTo(MeetInfo other) {
        return dateTime.compareTo(other.dateTime) ;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
