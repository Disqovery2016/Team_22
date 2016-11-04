package com.bharatwaaj.android.tcsemergencyservices.Models;

/**
 * Created by Bharatwaaj on 04-11-2016.
 */

public class Ambulance {
    String latitude;
    String longitude;

    public Ambulance(){

    }

    public Ambulance(String latitude,String longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
