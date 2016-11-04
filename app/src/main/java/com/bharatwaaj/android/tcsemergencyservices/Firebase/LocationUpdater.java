package com.bharatwaaj.android.tcsemergencyservices.Firebase;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Bharatwaaj on 04-11-2016.
 */

public class LocationUpdater {

    private static int array_count = 0;

    public static void updateLocationToFirebase(LatLng latLng){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = firebaseDatabase.getReference();
        mDatabase.child("Users").child("LatLng").setValue(latLng);
    }
}
