package org.weitblicker.weitblickapp;

public class Project {
    private int id;
    private String name;
    private String description;
    private String abst;
    private String location;


    // convert from json, numbers with system language = text

    public Project(int project_id, String s, String s1, String s2, String s3) {
        // sample
        id = project_id;
        name = "name of project";
        description = "project description";
        abst = "project abstract";
        location = "location of project"; // converted from location id
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





}
