package com.cmput301f17t07.ingroove;

import android.os.Bundle;

import com.cmput301f17t07.ingroove.navDrawer.NavigationDrawerActivity;

public class CurrentHabitsActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_habits);
        super.onCreateDrawer();
    }
}
