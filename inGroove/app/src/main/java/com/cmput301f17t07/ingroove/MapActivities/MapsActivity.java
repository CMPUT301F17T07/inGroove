package com.cmput301f17t07.ingroove.MapActivities;


import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * [Boundary Class]
 * Displays a map of habit events on screen
 *
 * Uses code from android example projects.
 * See https://github.com/googlesamples/android-play-location/tree/master/LocationUpdates
 *
 * @see MapOptionsActivity
 * @see HabitEvent
 * @see DataManagerAPI
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    DataManagerAPI data = DataManager.getInstance();

    // Bundle Keys
    public static final String USER_LOC_KEY = "user location";
    public static final String CENTER_USER_LOC_KEY = "if centering on user location";
    public static final String LOC_ARRAY_KEY = "array of locations";
    public static final String HIGHLIGHT_NEAR_KEY = "highlight nearby locations";
    public static final String FOLLOWEE_HABS_KEY = "if the map should include habits from those you follow";
    public static final String USERS_HABS_KEY = "if the map should include your habits";

    // Represents the google map fragment
    private GoogleMap mMap = null;
    private boolean center_on_user;
    private boolean highlight_near;
    private boolean show_followee_habs;
    private boolean show_usr_habs;

    //Represents a geographical location.
    protected Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mLastLocation = (Location) getIntent().getExtras().getParcelable(USER_LOC_KEY);
        if (mLastLocation != null){
            Log.d("---MAPS ACTIVITY---", "last location" + mLastLocation.toString());
        }

        center_on_user = getIntent().getExtras().getBoolean(CENTER_USER_LOC_KEY, false);
        highlight_near = getIntent().getExtras().getBoolean(HIGHLIGHT_NEAR_KEY, false);
        show_followee_habs = getIntent().getExtras().getBoolean(FOLLOWEE_HABS_KEY, true);
        show_usr_habs = getIntent().getExtras().getBoolean(USERS_HABS_KEY, true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
        setup_map();
    }

    public void setup_map(){
        // @TODO One of the use cases is to see habit events of those the user follows within 5km
        // since this version doesn't support social aspects this code finds the events within
        // 5km of the user from the user him/her self.
        ArrayList<Habit> habits = data.getHabits();

        if(show_followee_habs){
            add_followee_habit_markers();
        }

        if(show_usr_habs){
            add_user_habit_markers();
        }


        // Add the user's own location as a blue marker if available
        if(center_on_user){
            LatLng userLoc = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(userLoc)
                    .title("Your location")
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLoc, 10));
        }

    }

    private void add_user_habit_markers() {
        ArrayList<HabitEvent> my_events = data.getHabitEvents();
        // Add all User events Markers
        for (HabitEvent e : my_events){
            LatLng loc = e.getLocation();
            if (loc != null){
                if (eventNear(e)){
                    mMap.addMarker(new MarkerOptions()
                            .position(loc)
                            .title(e.getName())
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                } else {
                    mMap.addMarker(new MarkerOptions()
                            .position(loc)
                            .title(e.getName())
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                }

            }

        }
    }

    private void add_followee_habit_markers() {
        // Get all the users this user follows
        data.getWhoThisUserFollows(data.getUser(), new AsyncResultHandler<User>() {
            @Override
            public void handleResult(ArrayList<User> result) {
                // For each one
                Log.d("--MAP--", "Got "+ result.size() + " followers");
                for(User u : result){
                    Log.d("---MAP---", "For user " + u.getName()+ " with id " + u.getUserID());
                    // Get all their habit events with locations
                    // Add those locations to the map
                    data.findHabitEvents(u, new AsyncResultHandler<HabitEvent>() {
                        @Override
                        public void handleResult(ArrayList<HabitEvent> result) {
                            for (HabitEvent e : result){
                                LatLng loc = e.getLocation();
                                if (loc != null){
                                    if(eventNear(e)){
                                        mMap.addMarker(new MarkerOptions()
                                                .position(loc)
                                                .title(e.getName())
                                                .icon(BitmapDescriptorFactory
                                                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                    } else {
                                        mMap.addMarker(new MarkerOptions()
                                                .position(loc)
                                                .title(e.getName())
                                                .icon(BitmapDescriptorFactory
                                                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean eventNear(HabitEvent e){
        // Adapted from https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
        if (mLastLocation == null || !highlight_near){
            return false;
        }

        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(e.getLocation().latitude-mLastLocation.getLatitude());
        double dLng = Math.toRadians(e.getLocation().longitude-mLastLocation.getLongitude());
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(mLastLocation.getLatitude())) * Math.cos(Math.toRadians(e.getLocation().latitude)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);
        Log.d("---DISTANCE CALCULATION---", "Distance was computed as " + dist);

        if (dist <= 5000){
            return true;
        } else {
            return false;
        }
    }

}

