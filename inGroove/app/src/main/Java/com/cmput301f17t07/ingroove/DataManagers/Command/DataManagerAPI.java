package com.cmput301f17t07.ingroove.DataManagers.Command;

import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.User;

import java.util.ArrayList;

/**
 * Created by fraserbulbuc on 2017-11-07.
 */

public interface DataManagerAPI {

    ArrayList<Habit> getHabit(User user);

    ArrayList<HabitEvent> getHabitEvents(Habit habit);

    int addHabit(Habit habit);

    int removeHabit(Habit habit);

    int editHabit(Habit oldHabit, Habit newHabit);

    int addHabitEvent(HabitEvent habitEvent);

    int removeHabitEvent(HabitEvent habitEvent);

    int editHabitEvent(HabitEvent oldHabitEvent, HabitEvent newHabitEvent);

    int addUser(String userName);

    int removeUser(User user);

    User getUser();

}
