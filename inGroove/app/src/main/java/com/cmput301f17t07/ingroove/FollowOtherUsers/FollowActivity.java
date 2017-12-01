package com.cmput301f17t07.ingroove.FollowOtherUsers;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.MockDataManager;
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

    // DataManagerAPI data = DataManager.getInstance();
    DataManagerAPI data = new MockDataManager();

    // elements on the view
    ListView searchedForUsersListView;
    EditText searchBox;
    ImageButton searchButton;

    // adapter
    FollowAdapter followAdapter;
    ArrayList<User> searchResults = new ArrayList<User>();

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        super.onCreateDrawer();

        // find all the elements on the page
        searchBox = (EditText) findViewById(R.id.searchForUsersEditText);
        searchButton = (ImageButton) findViewById(R.id.searchForUsersButton);
        searchedForUsersListView = (ListView) findViewById(R.id.followUsersListView);

        // for testing
        //searchResults.add(new User("Bob"));
        //searchResults.add(new User("Joe"));

        // set adapter for the list view
        followAdapter = new FollowAdapter(searchResults, this);
        searchedForUsersListView.setAdapter(followAdapter);

        search

        searchedForUsersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                User user = searchResults.get(i);
                Log.w("TESTTESTEST", "HELLO WORLD HELLO WORLD"+user.getName());
            }
        });

    }

}
