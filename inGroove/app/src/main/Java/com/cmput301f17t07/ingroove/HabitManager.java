package com.cmput301f17t07.ingroove;

/**
 * Created by fraserbulbuc on 2017-10-22.
 */

import java.util.ArrayList;

/**
 *
 * Class to handle modifying habit data in elastic search
 *
 * @author fraserbulbuc
 * @see Habit
 *
 */
public class HabitManager {

    public HabitManager() { }

    /**
     * adds habit in elastic search for user
     *
     * @param user user who has the habit
     * @param habit habit to be added
     */
    public void addHabit(User user, Habit habit) {
        // TODO: remove habit from elasticsearch
    }

    /**
     * removes habit from elastic search for user
     *
     * @param user user who has the habit
     * @param habit habit to be removed
     */
    public void removeHabit(User user, Habit habit) {
        // TODO: add habit to elasticsearch
    }

    /**
     * retrieves habits for a user
     *
     * @param user user to query habits
     *
     * @return arraylist of habit objects for user
     */
    public ArrayList<Habit> retrieveHabits(User user) {
        // TODO: retrieve habits from elastic search
        return new ArrayList<>();
    }

}
