package com.cmput301f17t07.ingroove.Controller;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.User;

import java.util.ArrayList;

/**
 * Created by corey on 2017-11-29.
 */

public class StreakCalculator{

    private User user;
    private ArrayList<HabitEvent> habitEvents;
    private static final DataManagerAPI data = DataManager.getInstance();

    public StreakCalculator(){

    }

    public void updateStreakForUser(){
        user = data.getUser();
        data.find
        data.findHabitEvents("YOUR HABIT EVENT QUERY", new AsyncResultHandler<HabitEvent>() {
            @Override
            public void handleResult(ArrayList<HabitEvent> result) {

            }
        });
    }


}
