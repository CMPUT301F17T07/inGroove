package com.cmput301f17t07.ingroove.FollowOtherUsers;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.cmput301f17t07.ingroove.Model.Follow;
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.navDrawer.NavigationDrawerActivity;

import java.util.ArrayList;

/**
 * Boundary class that displays a user's followers
 *
 * @see User
 */
public class FollowActivity extends NavigationDrawerActivity {

    // elements on the view
    ListView otherUsersList;
    EditText searchBox;
    ImageButton searchButton;

    // adapter
    FollowAdapter followAdapter;
    ArrayList<User> searchedForUserList = new ArrayList<User>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        super.onCreateDrawer();

        User user1 = new User("Bob");
        User user2 = new User("Joe");
        //Follow follow1 = new Follow()
        searchedForUserList.add(user1);
        searchedForUserList.add(user2);

        // find all the elements on the page
        searchBox.findViewById(R.id.searchForUsersButton);
        searchButton.findViewById(R.id.searchForUsersButton);
        // get and set adapters for the list view
        otherUsersList.findViewById(R.id.followUsersListView);
        followAdapter = new FollowAdapter(this, R.layout.list_item_follow_activity, searchedForUserList);
        otherUsersList.setAdapter(followAdapter);
    }


}
