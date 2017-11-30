package com.cmput301f17t07.ingroove.ViewFollowersActivityPackage;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.MockDataManager;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.Model.Follow;
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.UserActivityPackage.UserActivity;
import com.cmput301f17t07.ingroove.avehabit.ViewHabitActivity;
import com.cmput301f17t07.ingroove.navDrawer.NavigationDrawerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewFollowersActivity extends NavigationDrawerActivity {

    DataManager ServerCommunicator = DataManager.getInstance();
    MockDataManager mServerCommunicator = MockDataManager.getInstance();

    ListView FollowerViewer;
    ArrayList<User> FollowerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_followers);
        super.onCreateDrawer();

        User passed_user = ServerCommunicator.getUser();

        //Initilize variables.
        FollowerViewer = (ListView) findViewById(R.id.FollowerViewer);

        //Populate the listview

        // TODO fix me
        ServerCommunicator.getWhoFollows(passed_user, new AsyncResultHandler<User>() {
            @Override
            public void handleResult(ArrayList<User> result) {
                FollowerList = result;
                fillFollowersListView(FollowerList);
            }
        });
        
        //FollowerList = mServerCommunicator.getWhoFollows(passed_user);



        FollowerViewer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                FollowerListOnClick(position);
            }
        });
    }

    /**
     * This method creates the elements that will populate a listview.
     * @param List: The list that will populate the listview
     */
    private void fillFollowersListView(ArrayList<User> List)
    {
        java.util.List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        for (User l : List)
        {
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("title", l.getName());
            datum.put("date", "Longest Streak: " + l.getStreak());
            data.add(datum);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"title", "date"},
                new int[] {android.R.id.text1,
                        android.R.id.text2});

        fillListView(adapter);
    }

    /**
     * This method populates a specific listview with a passed adapter.
     * @param adapter: An adapter with the elements that will populate the list.
     */
    private void fillListView(SimpleAdapter adapter)
    {
        FollowerViewer.setAdapter(adapter);
    }

    private void FollowerListOnClick(int position)
    {
        /* TODO fix me
        //TODO: Find out which activity a on-click event should go to.
        ServerCommunicator.setPassedUser(FollowerList.get(position));
        Intent upcomingIntent = new Intent(v.getContext(), ViewOtherUserActivity.class);
        startActivityForResult(upcomingIntent, 0);
        */
    }
}
