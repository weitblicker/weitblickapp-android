package org.weitblicker.weitblickapp;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class Project {
    private int id;
    private String name;
    private String description;
    private String abst;
    private String location;

    // convert from json, numbers with system language = text

    public Project(int project_id) {
        // sample
        id = project_id;
        name = "Dies ist ein Beispiel Projekt";
        description = "project description";
        abst = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et.";
        location = "location of project"; // converted from location id
        URL imageUri = null;
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

    // set properties
    public void setName(String string) {
        name = string; // call name from database
    }

    public void setDescription(String string){
        description = string;
    }

    public void setAbstract(String string){
        abst = string;
    }

    public void setLocation(String string){
        location = string;
    }

    public URL getImageUrl() throws MalformedURLException {
        return new URL("https://weitblicker.org/sites/default/files/styles/width100_custom_user_normal_1x/public/tappictures/12122961_1092695100749688_9146679379404367906_n.jpg");
    }
}
