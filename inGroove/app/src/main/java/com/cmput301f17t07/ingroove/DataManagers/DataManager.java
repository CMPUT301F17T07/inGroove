package com.cmput301f17t07.ingroove.DataManagers;

import android.content.Context;
import android.util.Log;
import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.User;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * Singleton class representing DataManager facade (relays calls as appropriate to individual classes
 * that manage habit, habitEvent, relationships, and user data)
 *
 * Allows for frontend classes to interact with the backend data--implements the DataManagerAPI so
 * that interacting objects only have access to the methods defined in the interface
 *
 * @see DataManagerAPI
 *
 * Created by Christopher Walter on 2017-10-31.
 */
public class DataManager implements DataManagerAPI {

    private static DataManager instance = new DataManager();

    // File name for the user data on disk
    private final String USER_FILE = "User_File.sav";

    private HabitManager habitManager;
    private HabitEventManager habitEventManager;
    private RelationshipManager relationshipManager;

    // current user
    private User user;

    private User passedUser;
    private Habit passedHabit;
    private HabitEvent passedHabitEvent;


    /**
     * Private constructor to instantiate a new singleton object
     */
    private DataManager() {
        habitManager = HabitManager.getInstance();
        habitEventManager = HabitEventManager.getInstance();
        relationshipManager = RelationshipManager.getInstance();

        loadUser();
    }

    /**
     * Method to access singleton instance
     *
     * @return the singleton instance of DataManager
     */
    public static DataManager getInstance(){
        return instance;
    }

    /**
     * Retrieves the current user
     *
     * @return a User instance representing the current user
     * @see User
     */
    public User getUser() {
        if (user == null ) {
            loadUser(); return user;
        }
        else {
            return user;
        }
    }

    /**
     * Adds a new user to storage.
     *
     * @param s a string representing the user's username
     * @return 0 if success, -1 if any issues
     * @see User
     */
    @Override
    public String addUser(String s) {

        // TODO: Verify there is a network connection before attempting.

        user = new User(s);
        ServerCommandManager.AddUserAsync addUserTask = new ServerCommandManager.AddUserAsync();
        System.out.println("---- NEW USER ---- with name " + user.getName());
        addUserTask.execute(user);
        System.out.println("---- NEW USER ---- with name " + user.getName());

        saveLocal();

        return s;
    }

    // TODO: CAN WE REMOVE THIS?
    public int editUser(User user) {

        this.user = user;
        
        saveLocal();

        // TODO: push to server;

        return 0;
    }

    /**
     * Removes a user from the application, all data will be lost for that user
     *
     * @param user the user to be removed
     * @return 0 if success, -1 if any issues
     * @see User
     */
    public int removeUser(User user) {
        // TODO: remove user from local and elastic search storage
        return 0;
    }

    /**
     * Relays habit to be added to the habit manager
     *
     * @param user the user for which the habits should be retrieved
     * @return a list of habit objects
     * @see Habit
     */
    public ArrayList<Habit> getHabit(User user) {
        return habitManager.getHabits();
    }

    /**
     *  Retrieves the habitEvents for a particular habit from the habitEventManager
     *
     * @param forHabit the habit in which the habitEvents are wanted
     * @return a list of habitEvents for that particular habit
     * @see HabitEvent
     */
    public ArrayList<HabitEvent> getHabitEvents(Habit forHabit) {
        return habitEventManager.getHabitEvents(forHabit);
    }

    /**
     * Retrieves the habitEvents for a particular user from the habitEventManager
     *
     * @param forUser the user in which the habitEvents are wanted
     * @return a list of all the habitEvents a user has
     * @see HabitEvent
     * @see User
     */
    @Override
    public ArrayList<HabitEvent> getHabitEvents(User forUser) {
        return habitEventManager.getHabitEvents(forUser);
    }

    /**
     * Asks the habitManager to add a habit object to the data storage
     *
     * @param habit the new habit to be added
     * @return 0 if success, -1 if any issues
     * @see User
     */
    public int addHabit(Habit habit) {
        habitManager.addHabit(user, habit);
        return 0;
    }

    /**
     * Relay call to habit manager to remove habit from storage
     *
     * @param habit the habit to be removed
     * @return 0 if success, -1 if any issues
     * @see Habit
     */
    public int removeHabit(Habit habit) {
        habitManager.removeHabit(user, habit);
        return 0;
    }

    /**
     * Relay call to habit manager to update the contents of a habit in storage with new information
     *
     * @param oldHabit the habit to be updated
     * @param newHabit the new habit data to replace the old data
     * @return 0 if success, -1 if any issues
     * @see Habit
     */
    public int editHabit(Habit oldHabit, Habit newHabit) {
        return HabitManager.getInstance().editHabit(oldHabit, newHabit);
    }

    /**
     * Relay the call to the habitEventManager to add a habitEvent for a habit to storage
     *
     * @param habit the habit for which the event is being logged
     * @param event habitEvent to add
     * @return 0 if success, -1 if any issues
     * @see HabitEvent
     */
    public int addHabitEvent(Habit habit, HabitEvent event) {
        return habitEventManager.addHabitEvent(habit, event);
    }

    /**
     * Relay the call to the habitEventManager to remove a habitEvent from storage
     *
     * @param event the habitEvent to be removed
     * @return 0 if success, -1 if any issues
     * @see HabitEvent
     */
    public int removeHabitEvent(HabitEvent event) {
        habitEventManager.removeHabitEvent(event);
        return 0;
    }

    /**
     * Relay the call to the habitEventManager to update an existing habitEvent with new data
     *
     * @param oldHabitEvent the habitEvent being updated
     * @param newHabitEvent the new data to update the old event with
     * @return 0 if success, -1 if any issues
     * @see HabitEvent
     */
    public int editHabitEvent(HabitEvent oldHabitEvent, HabitEvent newHabitEvent) {
        return habitEventManager.editHabitEvent(oldHabitEvent, newHabitEvent);
    }

    /**
     * used to pass users between activities
     * only returns the user once and then returns null til a new user is set by setPassedUser(User passedUser)
     *
     * @return the last user passed using setPassedUser(User passedUser)
     */
    public User getPassedUser() {
        User temp = passedUser;
        passedUser = null;
        return temp;
    }

    /**
     * used to pass users between activities
     *
     * @param passedUser the user to be passed, is return by getPassedUser()
     */
    public void setPassedUser(User passedUser) {
        this.passedUser = passedUser;
    }

    /**
     * used to pass habit between activities
     * only returns the habit once and then returns null til a new user is set by setPassedHabit(Habit passedHabit)
     *
     * @return the last habit passed using setPassedHabit(Habit passedHabit)
     */
    public Habit getPassedHabit() {
        Habit temp = passedHabit;
        passedHabit = null;
        return temp;
    }

    /**
     * used to pass users between activities
     *
     * @param passedHabit the user to be passed, is return by getPassedHabit()
     */
    public void setPassedHabit(Habit passedHabit) {
        this.passedHabit = passedHabit;
    }

    /**
     * used to pass habitEvents between activities
     * only returns the habitEvent once and then returns null til a new user is set by setPassedHabitEvent(HabitEvent passedHabitEvent)
     *
     * @return the last habit passed using setPassedHabitEvent(HabitEvent passedHabitEvent)
     */
    public HabitEvent getPassedHabitEvent() {
        HabitEvent temp = passedHabitEvent;
        passedHabitEvent = null;
        return temp;
    }

    /**
     * used to pass users between activities
     *
     * @param passedHabitEvent the user to be passed, is return by getPassedHabitEvent()
     */
    public void setPassedHabitEvent(HabitEvent passedHabitEvent) {
        this.passedHabitEvent = passedHabitEvent;
    }

    /**
     * Save a local copy of the user information on the disk for offline use of the application
     *
     * @see Gson
     * @see FileOutputStream
     * @see BufferedWriter
     */
    private void saveLocal() {

        try {

            Context context = InGroove.getInstance();

            FileOutputStream fos = context.openFileOutput(USER_FILE, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(user, out);
            out.flush();

            Log.d("---- USER ----"," Successfully saved user with name, " + user.getName());


        } catch (FileNotFoundException e) {
            Log.d("---- ERRROR ----"," Could not save user. Caught Exception " + e);
        } catch (IOException e) {
            Log.d("---- ERRROR ----"," Could not save user. Caught Exception " + e);
        }

    }

    /**
     * Load the local copy of the user information from the disk if offline use of application
     *
     * @see Gson
     * @see FileInputStream
     * @see BufferedReader
     */
    private void loadUser() {

        try {

            Context context = InGroove.getInstance();

            FileInputStream fis = context.openFileInput(USER_FILE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            user = gson.fromJson(in, User.class);

            Log.d("---- USER ----"," Successfully loaded user with name, " + user.getName());
            Log.d("---- USER ----"," Successfully loaded user with id, " + user.getUserID());


        } catch (FileNotFoundException e) {
            Log.d("---- ERRROR ----"," Could not load user. Caught Exception " + e);
        }

    }

    /**
     * Add the user to the server for the first time when they install the application
     *
     * @param user the user to be added to storage
     * @see User
     * @see ServerCommandManager
     */
    public void addUserToServer(User user) {

        Index index = new Index.Builder(user).index("cmput301f17t07_ingroove").type("user").build();

        try {
            DocumentResult result = ServerCommandManager.getClient().execute(index);
            if (result.isSucceeded()) {
                user.setUserID(result.getId());
                saveLocal();
            }
        }
        catch (Exception e) {
            Log.d("---- USER ----"," Failed to add user with name " + user.getName() + " to server. Caught " + e);
        }
    }

}























