package org.weitblicker.weitblickapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by spuetz on 09.01.17.
 */

public class DateTools {
    public static String getDate(Date date) {

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
