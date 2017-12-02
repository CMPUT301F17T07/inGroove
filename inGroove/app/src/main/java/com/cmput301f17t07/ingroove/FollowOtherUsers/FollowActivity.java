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
public class FollowActivity extends NavigationDrawerActivity {

    DataManagerAPI data = DataManager.getInstance();

    // elements on the view
    ListView searchedForUsersListView;
    EditText nameSearchBox;
    EditText streakSearchBox;
    ImageButton searchButton;

    String searchText;
    String streakText;

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
            streakText = streakSearchBox.getText().toString();

            // check to make sure there are values in the edit texts and if there are not put
            // the corresponding needed output in
            if (streakText.matches("") && !searchText.matches("")) {

                data.findUsers(0, searchText, Boolean.FALSE, new AsyncResultHandler() {
                    @Override
                    public void handleResult(ArrayList result) {

                        if (result != null) {
                            result = (ArrayList<User>) result;
                            updateSearchResults(result);
                            Log.i("Searching For Users", "Successfully found "
                                    + String.valueOf(result.size()) + " users.");
                        }

                    }
                });

            } else if (!streakText.matches("") && searchText.matches("")) {

                data.findUsers(Integer.valueOf(streakText), "", Boolean.FALSE, new AsyncResultHandler() {
                    @Override
                    public void handleResult(ArrayList result) {

                        if (result != null) {
                            result = (ArrayList<User>) result;
                            updateSearchResults(result);
                            Log.i("Searching For Users", "Successfully found "
                                    + String.valueOf(result.size()) + " users.");
                        }

                    }
                });

            } else if (!streakText.matches("") && !searchText.matches("")) {

                data.findUsers(Integer.valueOf(streakText), searchText, Boolean.FALSE, new AsyncResultHandler() {
                    @Override
                    public void handleResult(ArrayList result) {

                        if (result != null) {
                            result = (ArrayList<User>) result;
                            updateSearchResults(result);
                            Log.i("Searching For Users", "Successfully found "
                                    + String.valueOf(result.size()) + " users.");
                        }

                    }
                });

            }

            }
        });

        // set adapter for the list view
        followAdapter = new FollowAdapter(searchResults, this);
        searchedForUsersListView.setAdapter(followAdapter);
    }

    /**
     *
     * @param updatedList
     */
    public void updateSearchResults(ArrayList<User> updatedList) {

        searchResults.clear();
        searchResults = updatedList;
        followAdapter = new FollowAdapter(searchResults, this);
        searchedForUsersListView.setAdapter(followAdapter);

    }

}
