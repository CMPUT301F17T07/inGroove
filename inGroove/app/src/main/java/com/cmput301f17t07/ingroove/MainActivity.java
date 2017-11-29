package com.cmput301f17t07.ingroove;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.MockDataManager;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.Model.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncResultHandler<User>{

    DataManagerAPI data = DataManager.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Set up the user if none exists
        User user = data.getUser();
        if (user == null){
            data.addUser("New Groover", this);
        } else {
            Intent intent = new Intent(getApplicationContext(), CurrentHabitsActivity.class);
            // Head to the Current Habits Activity as that is the chosen first screen

            getApplicationContext().startActivity(intent);

            // This main activity is meant to set up the user, and then send them to our chosen first
            // Activity, so it should kill itself off once it sends the user along so that they cant
            // come back to it.
            finish();
        }


    }

    @Override
    public void handleResult(ArrayList<User> result) {
        if (result == null) {
            //TODO: Tell the User that they need a connection to start the app the first time to make an account
            // maybe have a retry button on the main Activity screen
            Log.d("---- USER ----"," Failed to add user to server.");

        } else {
            // the user got added so the app can proceed

            // Head to the Current Habits Activity as that is the chosen first screen
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            getApplicationContext().startActivity(intent);

            // This main activity is meant to set up the user, and then send them to our chosen first
            // Activity, so it should kill itself off once it sends the user along so that they cant
            // come back to it.
            finish();
        }
    }

}
