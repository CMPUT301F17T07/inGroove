package com.cmput301f17t07.ingroove;

import android.os.Bundle;

import com.cmput301f17t07.ingroove.navDrawer.NavigationDrawer;

public class MainActivity extends NavigationDrawer{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_habits);
        super.onCreateDrawer();
    }

}
