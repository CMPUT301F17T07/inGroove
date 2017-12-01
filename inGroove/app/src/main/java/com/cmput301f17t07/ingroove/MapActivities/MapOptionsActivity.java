package com.cmput301f17t07.ingroove.MapActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.navDrawer.NavigationDrawerActivity;

/**
 * The MapOptionsActivity allows the user to choose different types of maps
 * they might be interested in viewing
 */
public class MapOptionsActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_options);
    }
}
