package org.weitblicker.weitblickapp;

/**
 * Created by MOHISM on 16.10.2016.
 */

public class Location {

    private int id;
    private String town;
    private int postalCode;
    private String street;
    private int streetNo;
    private String longitude;
    private String latitude;
    private String country;
    private String adition;
    private String comments;


    // convert from json, numbers with system language = text

    public Location(int location_id, String s, int postal_id, String s2, int street_id,String s3,String s4,String s5,String s6, String s7) {
        // sample
        this.id = location_id;
        this.town = "name of town";
        this.postalCode =postal_id ;
        this.street = "name of street";
        this.streetNo = street_id; // converted from location id
        this.longitude= "longitude";
        this.latitude= "latitude" ;
        this.country="name of country";
        this.adition="aditions";
        this.comments="other comments";
    }

    // get properties
    public int getId() {
        return id;
    }

    public String getTown(){
        return town;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getStreet() {
        return street;
    }

    public int getstreetNo() {
        return streetNo;
    }


    public String getLongitude() {
        return longitude;
    }


    public String getLatitude() {
        return latitude;
    }

    public String getCountry() {
        return country;
    }

    public String getAdition() {
        return adition;
    }

    public String getComments() {
        return comments;
    }


    // set properties
    public void setTown(String string) {
        town = string; // call name from database
    }

    public void setPostalCode(Integer number){
        postalCode = number;
    }

    public void setStreet(String string){
        street = string;
    }

    public void setStreetNo(Integer number){
        streetNo = number;
    }


    public void setLongitude(String string){
        longitude = string;
    }

    public void setLatitude(String string){
        latitude = string;
    }

    public void setCountry(String string){
        country = string;
    }
    public void setAdition(String string){
        adition = string;
    }
    public void setComments(String string){
        comments = string;
    }
}
