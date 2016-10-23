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
    private String imageUrl;

    public Project(){
        name = "Dies ist ein Beispiel Titel";
        abst = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam.";
        description = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
        imageUrl = "https://weitblicker.org/sites/default/files/styles/width100_custom_user_normal_1x/public/tappictures/12122961_1092695100749688_9146679379404367906_n.jpg";
    }

    public Project(int id, String name, String description, String abst, String location, String imageUrl){
        this.id = id;
        this.name = name;
        this.description = description;
        this.abst = abst;
        this.location = location;
        this.imageUrl = imageUrl;
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

    public String getImageUrl(){
        return imageUrl;
    }
}
