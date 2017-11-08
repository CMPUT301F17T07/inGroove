package com.cmput301f17t07.ingroove.ViewFollowRequests;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.navDrawer.NavigationDrawerActivity;

import java.util.ArrayList;

public class FollowRequestsActivity extends NavigationDrawerActivity {
    private FollowRequestAdapter adapter;
    private ArrayList<User> followRequestList = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_requests);
        super.onCreateDrawer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new FollowRequestAdapter(this, R.layout.list_item_activity_follow_requests, followRequestList);
        // then we need to .setAdapter(adapter) for whatever list we have populating the ListView
    }
}