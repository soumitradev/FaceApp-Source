package com.parrot.freeflight.utils;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics$Param;

public final class GPSHelper {
    private static volatile GPSHelper instance;
    private LocationManager locationManager = null;

    private GPSHelper(Context context) {
        this.locationManager = (LocationManager) context.getSystemService(FirebaseAnalytics$Param.LOCATION);
    }

    public static GPSHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (GPSHelper.class) {
                if (instance == null) {
                    GPSHelper gPSHelper = new GPSHelper(context);
                    return gPSHelper;
                }
            }
        }
        return instance;
    }

    public static boolean deviceSupportGPS(Context context) {
        if (((LocationManager) context.getSystemService(FirebaseAnalytics$Param.LOCATION)).getAllProviders().contains("gps")) {
            return true;
        }
        return false;
    }

    public static boolean isGpsOn(Context context) {
        try {
            if (((LocationManager) context.getSystemService(FirebaseAnalytics$Param.LOCATION)).isProviderEnabled("gps")) {
                return true;
            }
            return false;
        } catch (IllegalArgumentException e) {
            Log.d("GPS Helper", "Looks like we do not have gps on the device");
            return false;
        }
    }

    public void startListening(LocationListener theListener) {
        if (this.locationManager.isProviderEnabled("gps")) {
            this.locationManager.requestLocationUpdates("gps", 0, 0.0f, theListener);
        }
    }

    public void stopListening(LocationListener theListener) {
        this.locationManager.removeUpdates(theListener);
    }
}
