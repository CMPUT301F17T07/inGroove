package com.cmput301f17t07.ingroove.DataManagers;

/**
 * Created by Christopher Walter on 2017-10-31.
 */

import android.content.Context;
import android.os.AsyncTask;
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
 * Singleton class
 */
public class DataManager implements DataManagerAPI {

    private static DataManager instance = new DataManager();

    private final String USER_FILE = "User_File.sav";

    private HabitManager habitManager;
    private HabitEventManager habitEventManager;
    private RelationshipManager relationshipManager;

    private User user;

    private DataManager() {
        habitManager = HabitManager.getInstance();
        habitEventManager = HabitEventManager.getInstance();
        relationshipManager = RelationshipManager.getInstance();

        loadUser();

    }

    public User getUser() {
        if (user == null ) {
            loadUser(); return user;
        }
        else {
            return user;
        }
    }

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

    public int setUser(User user) {

        this.user = user;
        
        saveLocal();

        // TODO: push to server;

        return 0;
    }

    public int removeUser(User user) {
        return 0;
    }

    /**
     * Used to get access
     * @return the single instance of DataManager
     */
    public static DataManager getInstance(){
        return instance;
    }

    public ArrayList<Habit> getHabit(User user) {
        return habitManager.getHabits();
    }

    public ArrayList<HabitEvent> getHabitEvents(Habit forHabit) {
        return habitEventManager.getHabitEvents(forHabit);
    }

    @Override
    public ArrayList<HabitEvent> getHabitEvents(User forUser) {
        return habitEventManager.getHabitEvents(forUser);
    }

    public int addHabit(Habit habit) {
        habitManager.addHabit(user, habit);
        return 0;
    }

    public int removeHabit(Habit habit) {
        habitManager.removeHabit(user, habit);
        return 0;
    }

    public int editHabit(Habit oldHabit, Habit newHabit) {
        return HabitManager.getInstance().editHabit(oldHabit, newHabit);
    }

    public int addHabitEvent(Habit habit, HabitEvent event) {
        return habitEventManager.addHabitEvent(habit, event);
    }

    public int removeHabitEvent(HabitEvent event) {
        habitEventManager.removeHabitEvent(event);
        return 0;
    }

    public int editHabitEvent(HabitEvent oldHabitEvent, HabitEvent newHabitEvent) {
        return habitEventManager.editHabitEvent(oldHabitEvent, newHabitEvent);
    }

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























