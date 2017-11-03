package com.cmput301f17t07.ingroove.DataManagers;

/**
 * Created by fraserbulbuc on 2017-10-22.
 */

import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.User;

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

    private static final String HABITS_FILE = "habits.sav";

    private static HabitManager instance = new HabitManager();

    private static ArrayList<Habit> habits = new ArrayList<>();

    private HabitManager() { }

    public static HabitManager getInstance() {
        return instance;
    }

    /**
     * adds habit in elastic search for user
     *
     * @param user user who has the habit
     * @param habit habit to be added
     */
    public void addHabit(User user, Habit habit) {
        habits.add(habit);
        saveLocal();

    }

    /**
     * removes habit from elastic search for user
     *
     * @param user user who has the habit
     * @param habit habit to be removed
     */
    public void removeHabit(User user, Habit habit) {
        habits.remove(habit);
        saveLocal();
    }

    /**
     *
     * returns true if the use has a habit
     *
     * @param habit
     *
     * @return true if the habit exists
     */
    public static boolean hasHabit(User user, Habit habit) {
        return habits.contains(habit);
    }

    /**
     * saves the habits locally and update the server if there is a connecction
     */
    private void saveLocal() {
        // TODO: convert to JSON
        // TODO: write JSON to disk

    }

    /**
     * load the habits from the disk
     */
    private void loadHabits() {
        // TODO: read from file

    }




}
