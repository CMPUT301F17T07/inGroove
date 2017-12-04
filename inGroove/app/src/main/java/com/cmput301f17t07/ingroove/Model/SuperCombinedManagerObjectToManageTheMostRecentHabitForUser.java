package com.cmput301f17t07.ingroove.Model;

/**
 * [Data Class]
 *
 * class used for holding a user, Habit, and HabitEvent for passing results from
 *      the findMostRecentEvent method in the DataManager
 *
 * @see com.cmput301f17t07.ingroove.DataManagers.DataManager
 *
 * Created by Christopher Walter on 2017-12-02.
 */
public class SuperCombinedManagerObjectToManageTheMostRecentHabitForUser {
    public final User user;
    public final Habit habit;
    public final HabitEvent habitEvent;

    public SuperCombinedManagerObjectToManageTheMostRecentHabitForUser(User user, Habit habit, HabitEvent habitEvent) {
        this.user = user;
        this.habit = habit;
        this.habitEvent = habitEvent;
    }
}
