package org.weitblicker.weitblickapp;

import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.util.Calendar.YEAR;

public class NewsArticle {
    final private static SimpleDateFormat formatterRead = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private int id;

    private String name;
    private String text;
    private String abst;
    private String imageUrl;
    private String host;
    private Date date;

    public NewsArticle(){
        this.date = new Date();
    }

    public void setImageUrl(String url){ this.imageUrl = url; }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAbstract(String abst) {
        this.abst = abst;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHost(String host) { this.host = host; }

    // get properties
    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public String getText() {
        return text;
    }

    public String getAbstract() {
        return abst;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getHost() { return host; }

    public void setDateTime(String dateTime){
        try {
            date = formatterRead.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            // TODO
        }
    }

    public String getDate() {
        return DateTools.getDate(date);
    }

}
