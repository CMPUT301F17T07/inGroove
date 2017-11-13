package com.cmput301f17t07.ingroove.ViewHabitEvent;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.UserActivityPackage.EditUserActivity;
import com.cmput301f17t07.ingroove.UserActivityPackage.UserActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by corey on 2017-11-08.
 */

public class ViewHabitEventActivity extends FragmentActivity implements OnMapReadyCallback {
    // Key for sending the habit event to this activity for display
    // This class REQUIRES a habit event be sent to 
    public static final String he_key = "HABIT_EVENT_TO_DISPLAY";
    HabitEvent habitEvent;

    // Map variables
    GoogleMap mMap;

    // Interface Variables
    TextView he_title;
    TextView he_comment;
    ImageView he_image;
    ImageButton he_edit;
    ImageButton he_del;

    // Data Manager
    DataManagerAPI data = DataManager.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_event_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Hook up interface variables
        he_title = findViewById(R.id.view_he_event_title);
        he_comment = findViewById(R.id.view_he_event_comment);
        he_image = findViewById(R.id.view_he_event_image);
        he_edit = findViewById(R.id.view_he_edit_button);
        he_del = findViewById(R.id.view_he_delete_button);

        // Get the habit event to display
        Bundle bundle = this.getIntent().getExtras();
        habitEvent = (HabitEvent) bundle.getSerializable(this.he_key);

        // Set event image
        Drawable drawable = getResources().getDrawable(R.drawable.default_event_image);
        he_image.setImageDrawable(drawable);

        // Set the text fields
        he_title.setText(habitEvent.getName());
        he_comment.setText(habitEvent.getComment());

        // Set the on click listeners for the buttons
        he_edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditHabitEventActivity.class);
                intent.putExtra(EditUserActivity.habit_event_key, habitEvent);
                getApplicationContext().startActivity(intent);

            }
        });
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
        //LatLng sydney = new LatLng(33.8121, -117.919);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("DISNEYLAND!"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // Add a marker for the location of this event
        LatLng loc = habitEvent.locationToLatLng();
        mMap.addMarker(new MarkerOptions().position(loc).title("Event Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));

    }
}
