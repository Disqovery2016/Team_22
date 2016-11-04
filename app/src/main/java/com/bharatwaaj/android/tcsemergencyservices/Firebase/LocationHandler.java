package com.bharatwaaj.android.tcsemergencyservices.Firebase;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Bharatwaaj on 04-11-2016.
 */

public class LocationHandler {

    private static int array_count = 0;

    public static void updateLocationToFirebase(LatLng latLng) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = firebaseDatabase.getReference();
        mDatabase.child("Users").child("LatLng").setValue(latLng);
    }

    public static String retrieveLocationToFirebase() {
        final String[] string = {null};
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                string[0] =  dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = firebaseDatabase.getReference();
        mDatabase.addValueEventListener(postListener);
        return string[0];
    }

}
