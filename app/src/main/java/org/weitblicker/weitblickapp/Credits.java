package org.weitblicker.weitblickapp;

import java.util.Comparator;

/**
 * Created by spuetz on 20.12.16.
 */

public class Credits implements Comparable<Credits> {
    String name;
    String login;
    int commitsWeitblickAppAndroid;
    int commitsWeitblickAppServer;
    String imageUrl;
    String location;
    String bio;
    int linesOfCodeWeitblickAppAndroid;
    int linesOfCodeWeitblickAppServer;
    boolean loaded = false;


    @Override
    public int compareTo(Credits o) {
        int sumLinesThis = linesOfCodeWeitblickAppAndroid + linesOfCodeWeitblickAppServer;
        int sumLinesOther = o.linesOfCodeWeitblickAppAndroid + o.linesOfCodeWeitblickAppServer;
        return sumLinesOther - sumLinesThis;
    }
}
