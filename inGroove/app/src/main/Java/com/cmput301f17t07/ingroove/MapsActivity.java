package com.cmput301f17t07.ingroove;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    DataManagerAPI data = DataManager.getInstance();

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // @TODO One of the use cases is to see habit events of those the user follows within 5km
        // since this version doesn't support social aspects this code finds the events within
        // 5km of the user from the user him/her self.
        ArrayList<Habit> habits = data.getHabit(data.getUser());
        ArrayList<HabitEvent> close_events = new ArrayList<>();
        ArrayList<HabitEvent> events;
        for ( Habit h : habits){
            events = data.getHabitEvents(h);
            for (HabitEvent e : events){
                if (isClose(e)){
                    close_events.add(e);
                }
            }
        }
        Random rand = new Random(9);
        for (HabitEvent e : close_events){
            // @TODO Use the actual location instead of a random jitter around the U of A
            LatLng loc = new LatLng(53.5232 + rand.nextDouble()/100.1, -113.5263 + rand.nextDouble()/100.1);
            mMap.addMarker(new MarkerOptions().position(loc).title(e.getName()));
        }
        LatLng university = new LatLng(53.5232, -113.5263);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(university));
    }

    private boolean isClose(HabitEvent event){
        // @TODO check if event is within 5km of location
        return true;
    }
}
