package org.weitblicker.weitblickapp;

/**
 * Created by MOHISM on 16.10.2016.
 */

public class Location {

    private int id;
    private String town;
    private int postal_code;
    private String street;
    private int street_no;
    private String longitude;
    private String latitude;
    private String country;
    private String adition;
    private String comments;


    // convert from json, numbers with system language = text

    public Location(int location_id, String s, int postal_id, String s2, int street_id,String s3,String s4,String s5,String s6, String s7) {
        // sample
        id = location_id;
        town = "name of town";
        postal_code =postal_id ;
        street = "name of street";
        street_no = street_id; // converted from location id
        longitude= "longitude";
        latitude= "latitude" ;
        country="name of country";
        adition="aditions";
        comments="other comments";
    }

    // get properties
    public int getId() {
        return id;
    }

    public String getTown(){
        return town;
    }

    public int getPostal_code() {
        return postal_code;
    }

    public String getStreet() {
        return street;
    }

    public int getStreet_no() {
        return street_no;
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

    public void setPostal_code(Integer number){
        postal_code = number;
    }

    public void setStreet(String string){
        street = string;
    }

    public void setStreet_no(Integer number){
        street_no = number;
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
