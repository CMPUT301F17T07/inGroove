package com.cmput301f17t07.ingroove;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.MockDataManager;
import com.cmput301f17t07.ingroove.Model.User;

public class MainActivity extends AppCompatActivity {

    DataManagerAPI data = DataManager.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent;
        // Set up the user if none exists
        User user = data.getUser();
        if (user == null){
            data.addUser("New Groover");
            intent = new Intent(getApplicationContext(), SignupActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), CurrentHabitsActivity.class);
        }


        // Head to the Current Habits Activity as that is the chosen first screen

        getApplicationContext().startActivity(intent);

        // This main activity is meant to set up the user, and then send them to our chosen first
        // Activity, so it should kill itself off once it sends the user along so that they cant
        // come back to it.
        finish();
    }

}
