package com.cmput301f17t07.ingroove;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * This activity is used to help test the back end of the program.
 */
public class BackEndTesting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_end_testing);




        Button button = findViewById(R.id.BackendButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUsersWho();
            }
        });


    }

    private void getUsersWho() {
        User user = new User("test");
        user.setUserID("AV_1mhce5oH-Uyt_aG_A");
        DataManager.getInstance().getWhoThisUserFollows(user).observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(@Nullable ArrayList<User> users) {
                Log.i("Results", "There are " + String.valueOf(users.size()) + " users" );

            }
        });
    }



}
