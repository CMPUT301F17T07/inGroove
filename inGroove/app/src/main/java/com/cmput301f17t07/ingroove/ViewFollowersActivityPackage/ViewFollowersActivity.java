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

        User passed_user = ServerCommunicator.getPassedUser();
        FollowerViewer = (ListView) findViewById(R.id.FollowerViewer);

        //FollowerList = ServerCommunicator.getWhoFollows(passed_user);
        FollowerList = mServerCommunicator.getWhoFollows(passed_user);
        fillFollowersListView(FollowerList);


        FollowerViewer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                gridViewOnClickEvent(v, position);
            }
        });
    }

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

    private void fillListView(SimpleAdapter adapter)
    {
        FollowerViewer.setAdapter(adapter);
    }

    private void gridViewOnClickEvent(View v, int position)
    {
        //TODO: Find out which activity a on-click event should go to.
        /*
        Intent upcomingIntent = new Intent(v.getContext(), ViewAnother.class);
        ServerCommunicator.setPassedUser(FollowerList.get(position));
        startActivityForResult(upcomingIntent, 0);
        */
    }
}
