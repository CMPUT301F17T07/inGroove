package com.cmput301f17t07.ingroove.ViewFollowersActivityPackage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.MockDataManager;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.UserActivityPackage.ViewOtherUserActivity;
import com.cmput301f17t07.ingroove.navDrawer.NavigationDrawerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewFollowersActivity extends NavigationDrawerActivity {

    DataManager ServerCommunicator = DataManager.getInstance();

    ListView FollowerViewer;
    Button FollowersButton;
    Button HabitsButton;
    User passed_user;
    ArrayList<User> FollowerList;
    ArrayList<Habit> habitList;
    Boolean onFollowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_followers);
        super.onCreateDrawer();

        //Initilize variables.
        passed_user = ServerCommunicator.getUser();
        onFollowers = true;
        FollowerViewer = (ListView) findViewById(R.id.FollowerViewer);
        HabitsButton = (Button) findViewById(R.id.view_followers_habits_button);
        FollowersButton = (Button) findViewById(R.id.view_followers_button);

        //Populate the listview
        //FollowerList = mServerCommunicator.getWhoFollows(passed_user);
        ServerCommunicator.getWhoThisUserFollows(passed_user, new AsyncResultHandler<User>() {
            @Override
            public void handleResult(ArrayList<User> result) {
                FollowerList = result;
                fillFollowersListView(FollowerList);
                Log.d("---ViewFollowers---"," Got " + String.valueOf(result.size()) + " followers");

            }
        });


        HabitsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HabitsButtonOnClick(FollowerList);
            }
        });

        FollowersButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FollowerButtonOnClick();
            }
        });

        FollowerViewer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                FollowerListOnClick(position,v);
            }
        });
    }

    /**
     * This method creates the elements that will populate a listview.
     * @param List: The list that will populate the listview
     */
    private void fillFollowersListView(ArrayList<User> List)
    {
        if(List == null || List.size() == 0)
            return;

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

    private void FollowerListOnClick(int position, View v)
    {
        if(onFollowers) {
            ServerCommunicator.setPassedUser(FollowerList.get(position));
            Intent upcomingIntent = new Intent(v.getContext(), ViewOtherUserActivity.class);
            startActivityForResult(upcomingIntent, 0);
        }
    }

    private void HabitsButtonOnClick(ArrayList<User> ListToProcess)
    {
        habitList = new ArrayList<Habit>();
        java.util.List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        onFollowers = false;

        if(ListToProcess == null || ListToProcess.size() == 0) {
            FollowerViewer.setAdapter(null);
            return;
        }

        // For every user that is followed
        for (User u : ListToProcess)
        {
            ServerCommunicator.findHabits(u, new AsyncResultHandler<Habit>() {
                @Override
                public void handleResult(ArrayList<Habit> result) {
                    // Get their habits
                    for(Habit h : result){
                        ServerCommunicator.findHabitEvents(h, new AsyncResultHandler<HabitEvent>() {
                            @Override
                            public void handleResult(ArrayList<HabitEvent> result) {
                                // And their latest habit events
                                HabitEvent latest = null;
                                for (HabitEvent e : result){
                                    if (latest == null){
                                        latest = e;
                                    } else if (latest.getDay().before(e.getDay())){
                                        latest = e;
                                    }
                                }
                            }
                        });
                    }
                }
            });
            habitList = ServerCommunicator.getHabits();
            if(habitList == null || habitList.size() == 0)
                continue;
            for(Habit h : habitList){
                Map<String, String> datum = new HashMap<String, String>(2);
                datum.put("title", u.getName());
                datum.put("date", "Habit: " + h.getName());
                data.add(datum);
            }
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"title", "date"},
                new int[] {android.R.id.text1,
                        android.R.id.text2});

        fillListView(adapter);
    }

    private void FollowerButtonOnClick()
    {
        onFollowers = true;
        ServerCommunicator.getWhoThisUserFollows(passed_user, new AsyncResultHandler<User>() {
            @Override
            public void handleResult(ArrayList<User> result) {
                FollowerList = result;
                fillFollowersListView(FollowerList);

            }
        });
    }
}
