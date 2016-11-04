package com.bharatwaaj.android.tcsemergencyservices.Fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bharatwaaj.android.tcsemergencyservices.Firebase.LocationHandler;
import com.bharatwaaj.android.tcsemergencyservices.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private SupportMapFragment supportMapFragment;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private LatLng latLng;
    private GoogleMap mGoogleMap;
    private Circle currLocationMarker;
    private int radiusSize = 2500;

    private DatabaseReference latitudeReference;
    private DatabaseReference longitudeReference;
    private ValueEventListener valueEventListener;

    private String ambuLati;
    private String ambuLongi;

    public MapsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this supportMapFragment
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        latitudeReference = FirebaseDatabase.getInstance().getReference()
                .child("Ambulance").child("LatLng").child("latitude");

        longitudeReference = FirebaseDatabase.getInstance().getReference()
                .child("Ambulance").child("LatLng").child("longitude");

        getFirebaseData();

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        supportMapFragment = new SupportMapFragment();
        fragmentTransaction.add(R.id.map_view, supportMapFragment);
        fragmentTransaction.commit();
        supportMapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        buildGoogleApiClient();
        mGoogleApiClient.connect();
    }

    protected synchronized void buildGoogleApiClient() {
        Toast.makeText(getActivity(), "buildGoogleApiClient", Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "onConnected", Toast.LENGTH_SHORT).show();
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                CircleOptions circleOptions = new CircleOptions()
                        .center(latLng)
                        .radius(radiusSize)
                        .strokeWidth(2)
                        .strokeColor(Color.BLUE)
                        .fillColor(Color.parseColor("#400084d3"));
                currLocationMarker = mGoogleMap.addCircle(circleOptions);
                LocationHandler.updateLocationToFirebase(latLng);
            }
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(5000); //5 seconds
            mLocationRequest.setFastestInterval(3000); //3 seconds
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override

    public void onConnectionSuspended(int i) {
        Toast.makeText(getActivity(), "onConnectionSuspended", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), "onConnectionFailed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (currLocationMarker != null) {
            currLocationMarker.remove();
        }
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CircleOptions circleOptions = new CircleOptions()
                .center(latLng)
                .radius(radiusSize)
                .strokeWidth(2)
                .strokeColor(Color.BLUE)
                .fillColor(Color.parseColor("#400084d3"));
        currLocationMarker = mGoogleMap.addCircle(circleOptions);
        LocationHandler.updateLocationToFirebase(latLng);
        Toast.makeText(getActivity(), "Location Changed", Toast.LENGTH_SHORT).show();
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(13).build();
        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }

    public void getFirebaseData() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               ambuLati = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ValueEventListener postListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ambuLongi = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        latitudeReference.addValueEventListener(postListener);
        longitudeReference.addValueEventListener(postListener2);
    }
}
