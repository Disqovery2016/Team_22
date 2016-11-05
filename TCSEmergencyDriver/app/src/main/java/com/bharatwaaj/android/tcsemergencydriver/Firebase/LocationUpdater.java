package com.bharatwaaj.android.tcsemergencydriver.Firebase;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

/**
 * Created by Bharatwaaj on 04-11-2016.
 */

public class LocationUpdater {

    public static void updateLocationToFirebase(LatLng latLng){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = firebaseDatabase.getReference();
        mDatabase.child(UUID.randomUUID().toString()).setValue(latLng);
    }
}
