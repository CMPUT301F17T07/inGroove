package com.cmput301f17t07.ingroove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;

import java.util.Date;

public class BackEndTesting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_end_testing);


        Habit habit1 = new Habit("test2", "first test");

        DataManagerAPI dataManager = DataManager.getInstance();

        dataManager.addHabit(habit1);

        ServerCommandManager.getInstance().execute();

        HabitEvent event1 = new HabitEvent("testEvent2", new Date());
        event1.setHabitID(habit1.getHabitID());

        dataManager.addHabitEvent(habit1, event1);

        ServerCommandManager.getInstance().execute();





    }



}
