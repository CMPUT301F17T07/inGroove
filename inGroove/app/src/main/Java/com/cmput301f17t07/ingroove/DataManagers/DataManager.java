package com.cmput301f17t07.ingroove.DataManagers;

/**
 * Created by Christopher Walter on 2017-10-31.
 */

import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.User;

/**
 * Singleton class
 */
public class DataManager {



    private static DataManager instance = new DataManager();


    private HabitManager habitManager;
    private HabitEventManager habitEventManager;
    private RelationshipManager relationshipManager;

    private User user;


    private DataManager() {
        habitManager = HabitManager.getInstance();
        habitEventManager = HabitEventManager.getInstance();
        relationshipManager = RelationshipManager.getInstance();

    }

    /**
     * Used to get access
     * @return the single instance of DataManager
     */
    public static DataManager getInstance(){
        return instance;
    }


    public void addHabit(Habit habit) {
        habitManager.addHabit(user, habit);
    }

    public void removeHabit(Habit habit) {
        habitManager.removeHabit(user, habit);
    }






}
