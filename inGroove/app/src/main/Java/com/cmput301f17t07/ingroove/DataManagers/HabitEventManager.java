package com.cmput301f17t07.ingroove.DataManagers;

import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Christopher Walter on 2017-10-22.
 */

/**
 * Manages the data storage of the HabitEvents
 *
 * handles the online and offline functionality
 *
 * @author Christopher Walter
 */
public class HabitEventManager {

    private static final String FILE_NAME = "habitEvents_File.sav";

    private static HabitEventManager instance = new HabitEventManager();

    private ArrayList<HabitEvent> habitEvents = new ArrayList<>();

    private HabitEventManager() { }

    public static HabitEventManager getInstance() {
        return instance;
    }


    /**
     * Adds a HabitEvent to the server
     * if there's no connection to the server then it stores the change til
     * there is connection
     * @param event the new HabitEvent
     */
    public void addHabitEvent(HabitEvent event) {
        // TODO: add habit to server
    }

    /**
     * deletes the given HabitEvent
     * @param event the HabitEvent to be deleted
     */
    public void removeHabitEvent(HabitEvent event) {
        // TODO: remove habit from server
    }


    // TODO: change the name for the date
    /**
     * gets the Recent Events
     * @param user who's events you want
     * @param date how far back you want to get events
     * @return an ArrayList of HabitEvents from the present to the date given
     */
    public ArrayList<HabitEvent> getRecentEvents(User user, Date date) {
        // TODO: get the recent habits

        return null;
    }

    /**
     * gets the missed events for the user since the date given
     * @param user who's missed events
     * @param date how far back you want to get missed events
     * @return an ArrayList of missed HabitEvents from the present to the date given
     */
    public ArrayList<HabitEvent> getMissedEventsSince(User user, Date date) {
        // TODO: get the missed events

        return null;
    }

    /**
     * gets the completed events for the user since the date given
     * @param user who's completed events
     * @param date how far back you want to get completed events
     * @return an ArrayList of completed HabitEvents from the present to the date given
     */
    public ArrayList<HabitEvent> getCompletedEventsSince(User user, Date date) {
        // TODO: get the completed events

        return null;
    }

    /**
     * returns the percentage of habit events completed since the date given
     * @param user who's percentage
     * @param date how far back you want to get the percentage
     * @return the percentage represented as an int
     */
    public int getCompletionPercentageSince(User user, Date date) {
        // TODO: return the completion percentage

        return -1;
    }




    private void save() {
        // TODO: convert to JSON
        // TODO: write JSON to file
        // TODO: push JSON to server
    }

    private void loadHabitEvents(){
        // TODO: read from file to arraylist
        // TODO: push to server

    }

}
