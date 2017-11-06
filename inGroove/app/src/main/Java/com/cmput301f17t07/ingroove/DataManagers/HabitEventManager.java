package com.cmput301f17t07.ingroove.DataManagers;

import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
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

    private static final String HABIT_EVENTS_FILE = "habitEvents_File.sav";

    private static HabitEventManager instance = new HabitEventManager();

    private ArrayList<HabitEvent> habitEvents = new ArrayList<>();

    private HabitEventManager() {
        loadHabitEvents();
    }

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
        habitEvents.add(event);
        saveLocal();
        // TODO: add habit to server

    }

    /**
     * deletes the given HabitEvent
     * @param event the HabitEvent to be deleted
     */
    public void removeHabitEvent(HabitEvent event) {
        habitEvents.remove(event);
        saveLocal();
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




    private void saveLocal() {

        try {
            FileOutputStream fos = new FileOutputStream(HABIT_EVENTS_FILE, false);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(habitEvents, out);
            out.flush();


        } catch (FileNotFoundException e) {
            //TODO: implement exception
        } catch (IOException e) {
            //TODO: implement exception
        }

    }

    private void loadHabitEvents(){
        // TODO: read from file to arraylist

        try {
            FileInputStream fis = new FileInputStream(HABIT_EVENTS_FILE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            //Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-09-19
            Type listType = new TypeToken<ArrayList<Habit>>(){}.getType();
            habitEvents = gson.fromJson(in, listType);


        } catch (FileNotFoundException e) {
            //TODO: implement exception
        }

    }

}
