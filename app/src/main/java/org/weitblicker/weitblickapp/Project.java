package org.weitblicker.weitblickapp;

public class Project {
    public int id;
    public String name;
    public String description;
    public String abst;
    public String location;

    // convert from json, numbers with system language = text

    public Project(int project_id) {
        // sample
        id = project_id;
        name = "name of project";
        description = "project description";
        abst = "project abstract";
        location = "location of project"; // converted from location id
    }

    // methods for get and set member

}
