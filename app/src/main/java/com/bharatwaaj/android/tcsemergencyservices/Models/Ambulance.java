package com.bharatwaaj.android.tcsemergencyservices.Models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bharatwaaj on 04-11-2016.
 */

@IgnoreExtraProperties
public class Ambulance {

    public String lat;
    public String lon;
    public Map<String, Boolean> stars = new HashMap<>();

    public Ambulance() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Ambulance(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("lat", lat);
        result.put("lon", lon);
        return result;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}