package com.cmput301f17t07.ingroove.FollowOtherUsers;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.navDrawer.NavigationDrawerActivity;

import java.util.ArrayList;

public class FollowActivity extends NavigationDrawerActivity {
    private FollowAdapter followAdapter;
    private ArrayList<User> searchedForUserList = new ArrayList<User>();
    private ListView searchedForUserListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_follow);
    super.onCreateDrawer();

    // get and set adapters for the list view
    searchedForUserListView.findViewById(R.id.followUsersListView);
    followAdapter = new FollowAdapter(this, R.layout.list_item_follow_activity, searchedForUserList);
    searchedForUserListView.setAdapter(followAdapter);
    }


}
