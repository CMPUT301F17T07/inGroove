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

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.MockDataManager;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.navDrawer.NavigationDrawerActivity;

import java.util.ArrayList;

/**
 * View allows the user to accept or reject follow requests that other users have sent them.
 *
 * @see FollowRequestsActivity
 * @see DataManagerAPI
 * @see DataManager
 */
public class FollowRequestsActivity extends NavigationDrawerActivity implements AsyncResultHandler<User> {

    DataManagerAPI data = DataManager.getInstance();

    // set up needed page element
    ListView followListView;

    // set up adapter elements
    ArrayList<User> followRequests = new ArrayList<User>();

    /**
     * Sets up and initializes the follow request activity.
     *
     * @param savedInstanceState the saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_requests);
        super.onCreateDrawer();

        followListView = (ListView) findViewById(R.id.followRequestsListView);

        // get follow requests
        data.getFollowRequests(this);

        if (followRequests == null) {
            followRequests = new ArrayList<User>();
        }

        // testing!!!
        followRequests.add(new User("Joe"));

        // set adapter for list view
        FollowRequestAdapter adapter = new FollowRequestAdapter(followRequests, this);
        followListView.setAdapter(adapter);

    }

    /**
     * Get the new results for the follow requests that have been sent to the user.
     *
     * @param result the updated results
     */
    @Override
    public void handleResult(ArrayList<User> result) {
        followRequests = result;
    }
}
