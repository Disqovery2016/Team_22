package com.bharatwaaj.android.tcsemergencyservices.Others;

import android.app.Application;
import android.content.Context;

import com.bharatwaaj.android.tcsemergencyservices.Network.ConnectivityReceiver;


/**
 * Created by Bharatwaaj on 04-11-2016.
 */
public class TApplication extends Application {
    private static TApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static synchronized TApplication getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
