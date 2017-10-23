package com.cmput301f17t07.ingroove;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Christopher Walter on 2017-10-22.
 */

public class HabitEventManager {

    public HabitEventManager() { }


    public void addHabitEvent(HabitEvent event) {
        // TODO: add habit to server
    }

    public void removeHabitEvent(HabitEvent event) {
        // TODO: remove habit from server
    }

    public ArrayList<HabitEvent> getRecentEvents(User user, Date date) {
        // TODO: get the recent habits
    }

    public ArrayList<HabitEvent> getMissedEventsSince(User user, Date date) {
        // TODO: get the missed events
    }

    public ArrayList<HabitEvent> getCompletedEventsSince(User user, Date date) {
        // TODO: get the completed events
    }

    public int getCompletionPercentage(User user) {
        // TODO: return the completion percentage
    }


}
