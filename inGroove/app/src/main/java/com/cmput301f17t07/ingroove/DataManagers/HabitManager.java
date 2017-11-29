package com.cmput301f17t07.ingroove.DataManagers;

import android.content.Context;
import android.util.Log;
import com.cmput301f17t07.ingroove.DataManagers.Command.AddHabitCommand;
import com.cmput301f17t07.ingroove.DataManagers.Command.DeleteHabitCommand;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommand;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.GenericGetRequest;
import com.cmput301f17t07.ingroove.Model.Habit;
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

import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * All responsibilities of data facade related to retrieving, creating, or modifying habit data are
 * relayed to the habit manager.
 *
 * Represents the receiver for habit commands, saves and load data locally and in elastic search
 * data base as required.
 *
 * @see Habit
 *
 * Created by fraserbulbuc on 2017-10-22.
 */
public class HabitManager {

    // file name on disk for storing habits added during offline usage
    private static final String HABITS_FILE = "habits.sav";

    private static HabitManager instance = new HabitManager();
    private ArrayList<Habit> habits = new ArrayList<>();

    public void findHabits(AsyncResultHandler handler, String query) {

        GenericGetRequest<Habit> getReq = new GenericGetRequest(handler,Habit.class,"habit","name");
        //GetRequest<Habit> get = new GetRequest<Habit>(Habit.class,"habit","name");
        // TODO: check for connection before executing
        getReq.execute(query);
    }

    /**
     * Private constructor to ensure only one instance application wide
     */
    private HabitManager() {
        loadHabits();
    }

    /**
     * Public access to singleton instance
     *
     * @return the singleton instance of this class
     */
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

        UniqueIDGenerator generator = new UniqueIDGenerator(habits);
        String id = generator.generateNewID();
        habit.setObjectID(user.getUserID() + id);
        Log.d("--- NEW ID ---"," generated unique ID of: " + id );

        habit.setUserID(user.getUserID());

        habits.add(habit);
        saveLocal();
        ServerCommand addHabitCommand = new AddHabitCommand(user, habit);
        ServerCommandManager.getInstance().addCommand(addHabitCommand);

        //TODO: update this to the job scheduler
        ServerCommandManager.getInstance().execute();
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

        ServerCommand deleteHabit = new DeleteHabitCommand(habit);
        ServerCommandManager.getInstance().addCommand(deleteHabit);

        //TODO: update this to the job scheduler
        ServerCommandManager.getInstance().execute();

        // TODO: do somthing about the events that belong to this habit
    }

    /**
     * Update the contents of a habit in storage with new information
     *
     * @param oldHabit the habit to be updated
     * @param newHabit the new habit data to replace the old data
     * @return 0 if success, -1 if any issues
     * @see Habit
     */
    public int editHabit(Habit oldHabit, Habit newHabit) {
        int index = habits.indexOf(oldHabit);
        if (index == -1) {
            return -1;
        }
        habits.remove(oldHabit);
        newHabit.setObjectID(oldHabit.getObjectID());
        newHabit.setUserID(oldHabit.getUserID());
        habits.add(index, newHabit);
        saveLocal();

        //TODO: update server
        ServerCommand updateHabitCommand = new AddHabitCommand(DataManager.getInstance().getUser(), newHabit);
        ServerCommandManager.getInstance().addCommand(updateHabitCommand);

        //TODO: update this to the job scheduler
        ServerCommandManager.getInstance().execute();

        return 0;
    }

    /**
     * Relays habit to be added to the habit manager
     *
     * @return a list of habit objects
     * @see Habit
     */
    //TODO: update so it gets habits for a user
    public ArrayList<Habit> getHabits() {

        if (habits.size() == 0) {
            Log.d("-- RETURNING HABITS --",habits.size() + " habit(s) to return");

            for (Habit habit: habits) {
                Log.d("----- RETURNED -----", " habit named: " + habit.getName());
            }
            loadHabits();
            return habits;
        }

        Log.d("-- RETURNING HABITS --",habits.size() + " habit(s) to return");

        for (Habit habit: habits) {
            Log.d("----- RETURNED -----", " habit named: " + habit.getName());
        }

        return habits;
    }

    /**
     *
     * Checks if the user has a particular habit
     *
     * @param habit the habit to look for
     * @param user the user whose habits should be checked
     *
     * @return true if the habit exists
     */
    public  boolean hasHabit(User user, Habit habit) {
        return habits.contains(habit);
    }

    /**
     * Save a local copy of the user habits on the disk for offline use of the application
     *
     * @see Gson
     * @see FileOutputStream
     * @see BufferedWriter
     * @see InGroove
     */
    private void saveLocal() {

        try {
            Context context = InGroove.getInstance();

            FileOutputStream fos = context.openFileOutput(HABITS_FILE, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(habits, out);
            out.flush();

            for (Habit habit: habits) {
                Log.d("--- HABITS SAVED ---", " named: " + habit.getName());
            }


        } catch (FileNotFoundException e) {
            //TODO: implement exception
            Log.d("---- ERROR ----", "Caught Exception:" + e);

        } catch (IOException e) {
            //TODO: implement exception
            Log.d("---- ERROR ----", "Caught Exception:" + e);
        }

    }

    /**
     * Load the local copy of the user habits from the disk for offline use of application
     *
     * @see Gson
     * @see FileInputStream
     * @see BufferedReader
     * @see InGroove
     */
    private void loadHabits() {

        try {
            Context context = InGroove.getInstance();

            FileInputStream fis = context.openFileInput(HABITS_FILE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            //Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-09-19
            Type listType = new TypeToken<ArrayList<Habit>>(){}.getType();
            habits = gson.fromJson(in, listType);

            Log.d("--- DEBUG POINT ---", "here");

            Log.d("--- LOADED HABITS --- ", habits.size()+ " habit(s) in memory.");

            for (Habit habit: habits) {
                Log.d("--- HABIT ---", " named: " + habit.getName());
            }

        } catch (FileNotFoundException e) {
            Log.d("---- ERROR ----", "Caught Exception:" + e);
        }

    }

    /**
     * method to add a habit to the server
     * !!!!!Must be called Async!!!!!
     *
     * @param habit the habit to add to the server storage
     * @see ServerCommandManager
     * @see Habit
     */
    public void addHabitToServer(Habit habit) throws Exception {

        Boolean isNew = true;

        Index.Builder builder = new Index.Builder(habit).index(ServerCommandManager.INDEX).type(ServerCommandManager.HABIT_TYPE);

        if (habit.getObjectID() != null) {
            builder.id(habit.getObjectID());
            isNew = false;
        }

        Index index = builder.build();

        DocumentResult result = ServerCommandManager.getClient().execute(index);
        if (result.isSucceeded() && isNew) {
            //habit.setHabitID(result.getId());
            //saveLocal();
        }

    }

    /**
     * Method to delete a Habit from the server
     * *
     * @param habit the Habit to be deleted
     * @throws Exception throws an exception if it cant delete from the server
     * @see Habit
     */
    public void deleteHabitFromServer(Habit habit) throws Exception {

        Delete.Builder builder = new Delete.Builder(habit.getObjectID()).index(ServerCommandManager.INDEX).type(ServerCommandManager.HABIT_TYPE);

        Delete index = builder.build();
        DocumentResult result = ServerCommandManager.getClient().execute(index);
        if (result.isSucceeded()) {
            Log.d("---- ES -----"," Successfully Deleted Habit named " + habit.getName() + " from ES.");
        }

    }

}



















