package com.cmput301f17t07.ingroove.DataManagers;

/**
 * Created by Christopher Walter on 2017-10-31.
 */

/**
 * Singleton class
 */
public class DataManager {

    private static DataManager instance = new DataManager();


    private HabitManager habitManager;
    private HabitEventManager habitEventManager;
    private RelationshipManager relationshipManager;


    private DataManager() {
        // TODO: get the sub managers

    }

    /**
     * Used to get access
     * @return the single instance of DataManager
     */
    public static DataManager getInstance(){
        return instance;
    }


}
