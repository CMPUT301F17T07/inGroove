package com.cmput301f17t07.ingroove.ViewFollowRequests;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.navDrawer.NavigationDrawerActivity;

import java.util.ArrayList;

public class FollowRequestsActivity extends NavigationDrawerActivity {
    // get all the pages elements
    ListView followListView;

    // set up adapter elements
    ArrayList<User> followRequests = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_requests);
        super.onCreateDrawer();

        followListView = (ListView) findViewById(R.id.followRequestsListView);

        followRequests.add(new User("Bob"));
        followRequests.add(new User("Joe"));

        // set adapter for list view
        FollowRequestAdapter adapter = new FollowRequestAdapter(followRequests, this);
        followListView.setAdapter(adapter);

        // handle click on list view item
        followListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                User user = followRequests.get(i);
            }
        });
    }
}
