package com.cmput301f17t07.ingroove;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    DataManagerAPI data = DataManager.getInstance();

    private GoogleMap mMap = null;

    // Location Variables
    private static final String TAG = MapsActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    /**
     * Provides the entry point to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    boolean map_rdy = false;
    boolean loc_rdy = false;
    boolean tot_rdy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /* Imported */
        mRequestingLocationUpdates = false;
        mLastUpdateTime = "";

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        // Kick off the process of building the LocationCallback, LocationRequest, and
        // LocationSettingsRequest objects.
        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();
        /* END IMPORTED */

        // Location setup
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        map_rdy = true;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if (loc_rdy){
            setup_map();
        }
    }

    public void setup_map(){
        if(tot_rdy){
            return;
        }
        tot_rdy = true;
        // We dont know which call will finish first, the map, or the location
        // So each caller checks to see if the other has finished already and if
        // so then sets up the map
        // @TODO One of the use cases is to see habit events of those the user follows within 5km
        // since this version doesn't support social aspects this code finds the events within
        // 5km of the user from the user him/her self.
        ArrayList<Habit> habits = data.getHabit(data.getUser());
        LatLng userLoc = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
        ArrayList<HabitEvent> close_events = data.getHabitEventsWithinRange(5, userLoc);

        // For testing purposes, this will add the users events too so you can see it working.
        // @TODO delete
        ArrayList<HabitEvent> events;
        for ( Habit h : habits){
            events = data.getHabitEvents(h);
            for (HabitEvent e : events){
                close_events.add(e);
            }
        }
        ArrayList<LatLng> points = new ArrayList<>();
        int i = 0;
        Random rand = new Random(9);
        for (HabitEvent e : close_events){
            // @TODO Use the actual location instead of a random jitter around the U of A
            LatLng loc = e.getLocation();
            mMap.addMarker(new MarkerOptions().position(loc).title(e.getName()));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLoc, 10));
    }

    /**
     * Provides a simple way of getting a device's location and is well suited for
     * applications that do not require a fine-grained location and that do not need location
     * updates. Gets the best and most recent location currently available, which may be null
     * in rare cases when a location is not available.
     * <p>
     * Note: this method should be called after location permission has been granted.
     */
    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            if (map_rdy){
                                setup_map();
                            }
                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                            showSnackbar("getLastLocationException");
                        }
                    }
                });
    }

    /**
     * Shows a {@link Snackbar} using {@code text}.
     *
     * @param text The Snackbar text.
     */
    private void showSnackbar(final String text) {
        View container = findViewById(R.id.habitEventsActivityContainer);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(MapsActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation();
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }


    /**
     * Constant used in the location settings dialog.
     */
    private static final int REQUEST_CHECK_SETTINGS = 0x1;

        /**
         * The desired interval for location updates. Inexact. Updates may be more or less frequent.
         */
        private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

        /**
         * The fastest rate for active location updates. Exact. Updates will never be more frequent
         * than this value.
         */
        private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
                UPDATE_INTERVAL_IN_MILLISECONDS / 2;

        // Keys for storing activity state in the Bundle.
        private final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
        private final static String KEY_LOCATION = "location";
        private final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";

        /**
         * Provides access to the Location Settings API.
         */
        private SettingsClient mSettingsClient;

        /**
         * Stores parameters for requests to the FusedLocationProviderApi.
         */
        private LocationRequest mLocationRequest;

        /**
         * Stores the types of location services the client is interested in using. Used for checking
         * settings to determine if the device has optimal location settings.
         */
        private LocationSettingsRequest mLocationSettingsRequest;

        /**
         * Callback for Location events.
         */
        private LocationCallback mLocationCallback;

        /**
         * Represents a geographical location.
         */
        private Location mCurrentLocation;

        /**
         * Tracks the status of the location updates request. Value changes when the user presses the
         * Start Updates and Stop Updates buttons.
         */
        private Boolean mRequestingLocationUpdates;

        /**
         * Time when the location was updated represented as a String.
         */
        private String mLastUpdateTime;

        /**
         * Sets up the location request. Android has two location request settings:
         * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
         * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
         * the AndroidManifest.xml.
         * <p/>
         * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
         * interval (5 seconds), the Fused Location Provider API returns location updates that are
         * accurate to within a few feet.
         * <p/>
         * These settings are appropriate for mapping applications that show real-time location
         * updates.
         */
        private void createLocationRequest() {
            mLocationRequest = new LocationRequest();

            // Sets the desired interval for active location updates. This interval is
            // inexact. You may not receive updates at all if no location sources are available, or
            // you may receive them slower than requested. You may also receive updates faster than
            // requested if other applications are requesting location at a faster interval.
            mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

            // Sets the fastest rate for active location updates. This interval is exact, and your
            // application will never receive updates faster than this value.
            mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }

        /**
         * Creates a callback for receiving location events.
         */
        private void createLocationCallback() {
            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);

                    mCurrentLocation = locationResult.getLastLocation();
                    mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                }
            };
        }

        /**
         * Uses a {@link com.google.android.gms.location.LocationSettingsRequest.Builder} to build
         * a {@link com.google.android.gms.location.LocationSettingsRequest} that is used for checking
         * if a device has the needed location settings.
         */
        private void buildLocationSettingsRequest() {
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
            builder.addLocationRequest(mLocationRequest);
            mLocationSettingsRequest = builder.build();
        }

        /**
         * Handles the Start Updates button and requests start of location updates. Does nothing if
         * updates have already been requested.
         */
        public void startUpdatesButtonHandler(View view) {
            if (!mRequestingLocationUpdates) {
                mRequestingLocationUpdates = true;
                startLocationUpdates();
            }
        }

        /**
         * Handles the Stop Updates button, and requests removal of location updates.
         */
        public void stopUpdatesButtonHandler(View view) {
            // It is a good practice to remove location requests when the activity is in a paused or
            // stopped state. Doing so helps battery performance and is especially
            // recommended in applications that request frequent location updates.
            stopLocationUpdates();
        }

        /**
         * Requests location updates from the FusedLocationApi. Note: we don't call this unless location
         * runtime permission has been granted.
         */
        @SuppressWarnings("MissingPermission")
        private void startLocationUpdates() {
            // Begin by checking if the device has the necessary location settings.
            mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                    .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                        @Override
                        public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                            Log.i(TAG, "All location settings are satisfied.");

                            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                    mLocationCallback, Looper.myLooper());

                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            int statusCode = ((ApiException) e).getStatusCode();
                            switch (statusCode) {
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                            "location settings ");
                                    try {
                                        // Show the dialog by calling startResolutionForResult(), and check the
                                        // result in onActivityResult().
                                        ResolvableApiException rae = (ResolvableApiException) e;
                                        rae.startResolutionForResult(MapsActivity.this, REQUEST_CHECK_SETTINGS);
                                    } catch (IntentSender.SendIntentException sie) {
                                        Log.i(TAG, "PendingIntent unable to execute request.");
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    String errorMessage = "Location settings are inadequate, and cannot be " +
                                            "fixed here. Fix in Settings.";
                                    Log.e(TAG, errorMessage);
                                    Toast.makeText(MapsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                                    mRequestingLocationUpdates = false;
                            }
                        }
                    });
        }



        /**
         * Removes location updates from the FusedLocationApi.
         */
        private void stopLocationUpdates() {
            if (!mRequestingLocationUpdates) {
                Log.d(TAG, "stopLocationUpdates: updates never requested, no-op.");
                return;
            }

            // It is a good practice to remove location requests when the activity is in a paused or
            // stopped state. Doing so helps battery performance and is especially
            // recommended in applications that request frequent location updates.
            mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mRequestingLocationUpdates = false;
                        }
                    });
        }

        @Override
        public void onResume() {
            super.onResume();
            // Within {@code onPause()}, we remove location updates. Here, we resume receiving
            // location updates if the user has requested them.
            if (mRequestingLocationUpdates && checkPermissions()) {
                startLocationUpdates();
            } else if (!checkPermissions()) {
                requestPermissions();
            }
        }

        @Override
        protected void onPause() {
            super.onPause();

            // Remove location updates to save battery.
            stopLocationUpdates();
        }

        /**
         * Stores activity data in the Bundle.
         */
        public void onSaveInstanceState(Bundle savedInstanceState) {
            savedInstanceState.putBoolean(KEY_REQUESTING_LOCATION_UPDATES, mRequestingLocationUpdates);
            savedInstanceState.putParcelable(KEY_LOCATION, mCurrentLocation);
            savedInstanceState.putString(KEY_LAST_UPDATED_TIME_STRING, mLastUpdateTime);
            super.onSaveInstanceState(savedInstanceState);
        }


}

