package com.cmput301f17t07.ingroove.DataManagers;

/**
 * Created by fraserbulbuc on 2017-10-22.
 */

import android.content.Context;
import android.util.Log;

import com.cmput301f17t07.ingroove.DataManagers.Command.AddHabitCommand;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommand;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.Identifiable;
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

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

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

    private ArrayList<Habit> habits = new ArrayList<>();

    private HabitManager() {
        loadHabits();
    }

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
        habit.setHabitID(id);
        Log.d("--- NEW ID ---"," generated unique ID of: " + id );

        habits.add(habit);
        saveLocal();
        ServerCommand addHabitCommand = new AddHabitCommand(user, habit);
        ServerCommandManager.getInstance().addCommand(addHabitCommand);
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

    public int editHabit(Habit oldHabit, Habit newHabit) {
        int index = habits.indexOf(oldHabit);
        if (index == -1) {
            return -1;
        }
        habits.remove(oldHabit);
        newHabit.setHabitID(oldHabit.getHabitID());
        habits.add(index, newHabit);
        saveLocal();
        return 0;
    }

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
     * returns true if the use has a habit
     *
     * @param habit
     *
     * @return true if the habit exists
     */
    public  boolean hasHabit(User user, Habit habit) {
        return habits.contains(habit);
    }

    /**
     * saves the habit array to a local file
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
     * load the habits from the disk
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

    public void addHabitToServer(Habit habit) throws Exception {

        Boolean isNew = true;

        Index.Builder builder = new Index.Builder(habit).index("cmput301f17t07_ingroove").type("habit");

        if (habit.getHabitID() != null) {
            builder.id(habit.getHabitID());
            isNew = false;
        }

        Index index = builder.build();

        DocumentResult result = ServerCommandManager.getClient().execute(index);
        if (result.isSucceeded() && isNew) {
            //habit.setHabitID(result.getId());
            //saveLocal();
        }


    }




}



















