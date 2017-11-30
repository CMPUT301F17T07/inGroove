package com.cmput301f17t07.ingroove;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.MockDataManager;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.User;

import java.util.ArrayList;
import java.util.Date;

/*
- User wants to see all habits of a list of Users
-- Helper method, just get all habits of Users that have been followed
- User wants to see all requests to follow them
- User wants to see latest event from each habit in a list
 */



public class MainActivity extends AppCompatActivity implements AsyncResultHandler<User>{

    DataManagerAPI data = DataManager.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // Set up the user if none exists
        User user = data.getUser();
        if (user == null){
            data.addUser("New Groover", this);
        } else {

            // Update streak calculations
            // Get all the users habit events
            ArrayList<HabitEvent> habitEvents = data.getHabitEvents(user);
            // Get the last day this streak was calculated for
            Date calc_date = user.getStreak_end();

            if (DateUtils.isToday(calc_date.getTime())){
                Log.d("STREAK CALC: ", "No calc, last calc was today");
            } else if (isYesterday(calc_date)) {
                Log.d("STREAK CALC: ", "Date was yesterday, updating");
                boolean add_to_streak = false;
                for( HabitEvent e : habitEvents){
                    if (isYesterday(e.getDay())){
                        add_to_streak = true;
                        break;
                    }
                }
                if (add_to_streak){
                    user.setStreak(user.getStreak() + 1);
                    // update max streak
                    if (user.getMax_streak() < user.getStreak()){
                        user.setMax_streak(user.getStreak());
                    }
                } else {
                    // lose streak
                    user.setStreak(0);
                }
                user.setStreak_end(new Date());
            } else {
                Log.d("STREAK CALC: ", "Date older than yesterday, losing streak");
                // update max streak
                if (user.getMax_streak() < user.getStreak()){
                    user.setMax_streak(user.getStreak());
                }
                user.setStreak(0);
                user.setStreak_end(new Date());
            }
            data.editUser(user);


            Intent intent = new Intent(getApplicationContext(), CurrentHabitsActivity.class);
            // Head to the Current Habits Activity as that is the chosen first screen

            getApplicationContext().startActivity(intent);

            // This main activity is meant to set up the user, and then send them to our chosen first
            // Activity, so it should kill itself off once it sends the user along so that they cant
            // come back to it.
            finish();
        }


    }

    public static boolean isYesterday(Date d) {
        return DateUtils.isToday(d.getTime() + DateUtils.DAY_IN_MILLIS);
    }

    @Override
    public void handleResult(ArrayList<User> result) {
        if (result == null) {
            //TODO: Tell the User that they need a connection to start the app the first time to make an account
            // maybe have a retry button on the main Activity screen
            Log.d("---- USER ----"," Failed to add user to server.");


        } else {
            // the user got added so the app can proceed

            // Head to the Signup Activity as that is the chosen first screen
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            getApplicationContext().startActivity(intent);

            // This main activity is meant to set up the user, and then send them to our chosen first
            // Activity, so it should kill itself off once it sends the user along so that they cant
            // come back to it.
            finish();
        }
    }

}
