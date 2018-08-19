package lk.hire1.driver.services;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import lk.hire1.driver.app.App;
import lk.hire1.driver.rest.models.AppSession;

public class LocationTracking implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final Context mContext;
    private OnSendLocation callback;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private boolean mRequestingLocationUpdates;
    private List<Location> mSavedLocation = new ArrayList<>();
    private LocationInterval mUpdateInterval;
    private OnUpdateLocation updateCallback;
    private boolean mNeedSaveData;
    private int locationRequestAccuracyMode = LocationRequest.PRIORITY_HIGH_ACCURACY;
    private static LocationTracking locationTracking;

    private LocationTracking(Context context, int accuracy) {
        locationRequestAccuracyMode = accuracy;
        this.mContext = context;
        buildGoogleApiClient();
    }

    public static LocationTracking getInstance() {

        if (locationTracking != null) {
            return locationTracking;
        } else {
            return locationTracking = new LocationTracking(App.getApp().getApplicationContext(), LocationRequest.PRIORITY_HIGH_ACCURACY);

        }
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    private LocationRequest createLocationRequest(LocationInterval interval) {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(interval.getInterval());
        locationRequest.setFastestInterval(interval.getFastInterval());
        locationRequest.setPriority(locationRequestAccuracyMode);
        return locationRequest;
    }

    public void startLocationUpdates(LocationInterval interval) {
        mUpdateInterval = interval;
        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, createLocationRequest(interval), this);
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                sendLocation(mLastLocation);
            }
            mRequestingLocationUpdates = true;
        } else {
            mGoogleApiClient.connect();
        }
    }

    public void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//            mGoogleApiClient.disconnect();

            mRequestingLocationUpdates = false;
        }
    }

    public void restartLocationUpdates(LocationInterval interval) {

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mRequestingLocationUpdates = false;
        }

        mUpdateInterval = interval;
        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, createLocationRequest(interval), this);
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                sendLocation(mLastLocation);
            }
            mRequestingLocationUpdates = true;
        } else {
            mGoogleApiClient.connect();
        }

    }

    public void clearSavedData() {
        mSavedLocation.clear();
    }

    public void setNeedSaveLocationData(boolean needSaveData) {
        this.mNeedSaveData = needSaveData;
    }

    private void sendLocation(Location location) {
        if (mNeedSaveData) {
            mSavedLocation.add(location);
        }
        if (callback != null) {
            callback.onSendLocation(location);
        }
        if (updateCallback != null) {
            updateCallback.onUpdateLocation(location);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates(mUpdateInterval);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            sendLocation(location);
        }
    }

    public void setOnSendLocationListener(OnSendLocation callback) {
        this.callback = callback;
    }

    public void setOnUpdateLocationListener(OnUpdateLocation updateCallback) {
        this.updateCallback = updateCallback;
    }

    public void clearLocationUpdate() {
        this.updateCallback = null;
    }

    public interface OnSendLocation {
        void onSendLocation(Location location);
    }

    public interface OnUpdateLocation {
        void onUpdateLocation(Location location);

    }

    public Location getLastLocation() {
        return mLastLocation;
    }
}
