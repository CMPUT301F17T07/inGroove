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
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.GenericGetRequest;
import com.cmput301f17t07.ingroove.Model.Follow;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * This activity is used to help test the back end of the program.
 */
public class BackEndTesting extends AppCompatActivity implements AsyncResultHandler {

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


    @Override
    public void handleResult(ArrayList result) {
        if (result != null && !result.isEmpty()){
            if (result.get(0) instanceof Follow){
                Log.i("BackEndTesting", "found " + String.valueOf(result.size()) + " Follows");
            } else {
                Log.i("BackEndTesting", "found " + String.valueOf(result.size()) + " Users");
            }
        } else {
            Log.i("BackEndTesting", "result is empty ");

        }




    }

    private void getFollows(){
        GenericGetRequest<Habit> getFollows = new GenericGetRequest<>(this, Habit.class, "habit", "objectID");
        getFollows.execute("av");
    }

    private void getUsersWho() {
        User user = new User("test");
        user.setUserID("AWAArhLoBOIa5W1F-q2g");
        DataManager.getInstance().getWhoThisUserFollows(this, user);

    }



}
