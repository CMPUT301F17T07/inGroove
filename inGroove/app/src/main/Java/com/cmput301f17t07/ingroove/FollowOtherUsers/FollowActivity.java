package com.cmput301f17t07.ingroove.FollowOtherUsers;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;

import java.util.ArrayList;

public class FollowActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private FollowAdapter followAdapter;
    private ArrayList<User> searchedForUserList = new ArrayList<User>();
    private ListView searchedForUserListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        drawerLayout = (DrawerLayout) findViewById(R.id.follow_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get and set adapters for the list view 
        searchedForUserListView.findViewById(R.id.followUsersListView);
        followAdapter = new FollowAdapter(this, R.layout.list_item_follow_activity, searchedForUserList);
        searchedForUserListView.setAdapter(followAdapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
