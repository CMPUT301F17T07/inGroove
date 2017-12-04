package com.cmput301f17t07.ingroove.ViewFollowersActivityPackage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.SuperCombinedManagerObjectToManageTheMostRecentHabitForUser;
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.UserActivityPackage.ViewOtherUserActivity;
import com.cmput301f17t07.ingroove.ViewHabitEvent.ViewOtherUsersHabitEventActivity;
import com.cmput301f17t07.ingroove.navDrawer.NavigationDrawerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * [Boundary Class]
 *
 * Activity for viewing who your following and their HabitEvents
 *
 * @see DataManagerAPI
 * @see User
 * @see Habit
 * @see com.cmput301f17t07.ingroove.Model.HabitEvent
 */
public class ViewFollowersActivity extends NavigationDrawerActivity {

    DataManagerAPI ServerCommunicator = DataManager.getInstance();

    ListView FollowerViewer;
    Button FollowersButton;
    Button HabitsButton;
    User passed_user;
    ArrayList<User> FollowerList;
    ArrayList<HabitEvent> habitEventList;
    Boolean onFollowers;

    java.util.List<Map<String, String>> data;
    SimpleAdapter habitAdapter;

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

        data = new ArrayList<Map<String, String>>();

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
        } else {
            ServerCommunicator.setPassedHabitEvent(habitEventList.get(position));
            Intent upcomingIntent = new Intent(v.getContext(), ViewOtherUsersHabitEventActivity.class);
            startActivity(upcomingIntent);
        }
    }

    private void HabitsButtonOnClick(ArrayList<User> ListToProcess)
    {
        habitEventList = new ArrayList<HabitEvent>();
        data = new ArrayList<Map<String, String>>();

        onFollowers = false;

        if(ListToProcess == null || ListToProcess.size() == 0) {
            FollowerViewer.setAdapter(null);
            return;
        }

        habitAdapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"title", "date"},
                new int[] {android.R.id.text1,
                        android.R.id.text2});

        fillListView(habitAdapter);

        // For every user that is followed
        for (User u : ListToProcess)
        {
            ServerCommunicator.findMostRecentEvent(u, new AsyncResultHandler<SuperCombinedManagerObjectToManageTheMostRecentHabitForUser>() {
                @Override
                public void handleResult(ArrayList<SuperCombinedManagerObjectToManageTheMostRecentHabitForUser> result) {

                    for (SuperCombinedManagerObjectToManageTheMostRecentHabitForUser s : result){
                        Map<String, String> datum = new HashMap<String, String>(2);
                        datum.put("title", s.habitEvent.getName());
                        datum.put("date", "Habit: " + s.habit.getName() + " by " + s.user.getName());
                        data.add(datum);
                        habitEventList.add(s.habitEvent);
                    }
                    habitAdapter.notifyDataSetChanged();
                }
            });

        }


    }

    private void FollowerButtonOnClick()
    {
        onFollowers = true;
        fillFollowersListView(new ArrayList<User>());
        ServerCommunicator.getWhoThisUserFollows(passed_user, new AsyncResultHandler<User>() {
            @Override
            public void handleResult(ArrayList<User> result) {
                FollowerList = result;
                fillFollowersListView(FollowerList);

            }
        });
    }
}
