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

        Locale currentLanguage = Locale.GERMAN;

        Map<Locale, String> strNow = new HashMap<Locale, String>();
        strNow.put(Locale.GERMAN, "jetzt");
        strNow.put(Locale.ENGLISH, "now");
        strNow.put(Locale.FRANCE, "maintenant");

        Map<Locale, String> strMin = new HashMap<Locale, String>();
        strNow.put(Locale.GERMAN, "Minuten");
        strNow.put(Locale.ENGLISH, "minutes");
        strNow.put(Locale.FRANCE, "minutes");

        Date now = new Date();

        long diff = now.getTime() - date.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);

        SimpleDateFormat writeDateFormat = new SimpleDateFormat("d. MMMM", currentLanguage);
        SimpleDateFormat writeDateYearFormat = new SimpleDateFormat("dd.MM.yyyy", currentLanguage);
        SimpleDateFormat writeTimeFormat = new SimpleDateFormat("HH:mm", currentLanguage);

        Calendar calNow = Calendar.getInstance();
        calNow.setTime(now);

        Calendar calDate = Calendar.getInstance();
        calDate.setTime(date);

        boolean differentYear = calNow.get(Calendar.YEAR) != calDate.get(Calendar.YEAR);
        int diffDays = calNow.get(Calendar.DAY_OF_YEAR) - calDate.get(Calendar.DAY_OF_YEAR);

        if(diff < 0){  // TODO client time is before server time, what to do?
            return writeDateYearFormat.format(date);
        } else if (differentYear) {
            return writeDateYearFormat.format(date);
        } else if (diffDays > 1) {
            return writeDateFormat.format(date);
        } else if (diffDays == 1) {
            return "gestern";
        } else if (diffHours >= 1) {
            return "heute"; // writeTimeFormat.format(date);  // TODO just return today, (current language)
        } else if (diffMinutes > 5) {
            return diffMinutes + " " + strMin.get(currentLanguage);
        } else {
            return strNow.get(currentLanguage);
        }
    }
}
