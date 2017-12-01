package com.cmput301f17t07.ingroove.FollowOtherUsers;

import android.content.Intent;
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
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.MockDataManager;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.Model.Follow;
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.navDrawer.NavigationDrawerActivity;

import java.util.ArrayList;

/**
 * View allows the user to search for users to follow and request to follow them.
 *
 * @see FollowAdapter
 * @see DataManagerAPI
 * @see DataManager
 */
public class FollowActivity extends NavigationDrawerActivity implements AsyncResultHandler<User> {

    //DataManagerAPI data = DataManager.getInstance();
    DataManagerAPI data = new MockDataManager();

    // elements on the view
    ListView searchedForUsersListView;
    EditText nameSearchBox;
    EditText streakSearchBox;
    ImageButton searchButton;

    String searchText;
    Integer streakValue;

    // adapter
    FollowAdapter followAdapter;
    ArrayList<User> searchResults = new ArrayList<User>();

    /**
     * Sets up and initializes the follow activity.
     *
     * @param savedInstanceState the saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        super.onCreateDrawer();

        // find all the elements on the page
        nameSearchBox = (EditText) findViewById(R.id.searchByNameEditText);
        streakSearchBox = (EditText) findViewById(R.id.searchByStreakEditText);
        searchButton = (ImageButton) findViewById(R.id.searchForUsersButton);
        searchedForUsersListView = (ListView) findViewById(R.id.followUsersListView);

        // set up on click listener for the search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // get the results from the search box
                searchText = nameSearchBox.getText().toString();
                streakValue = Integer.valueOf(streakSearchBox.getText().toString());

                if (streakValue == null && searchText != null) {
                    data.findUsers(0, searchText, Boolean.FALSE, this);
                } else if (streakValue != null && searchText == null) {
                    data.findUsers(streakValue, "", Boolean.FALSE, this);
                } else if (streakValue != null && searchText != null) {
                    data.findUsers(streakValue, searchText, Boolean.FALSE, this);
                }


            }
        });

        searchResults.add(new User("Bob"));

        // set adapter for the list view
        followAdapter = new FollowAdapter(searchResults, this);
        searchedForUsersListView.setAdapter(followAdapter);

    }

    @Override
    public void handleResult(ArrayList<User> result) {

    }
}
