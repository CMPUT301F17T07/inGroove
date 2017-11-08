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

        ServerCommand addHabitCommand = new AddHabitCommand(user, habit, this);
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
     * saves the habit array to a local file
     */
    private void saveLocal() {

        try {
            FileOutputStream fos = new FileOutputStream(HABITS_FILE, false);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(habits, out);
            out.flush();


        } catch (FileNotFoundException e) {
            //TODO: implement exception
        } catch (IOException e) {
            //TODO: implement exception
        }

    }

    /**
     * load the habits from the disk
     */
    private void loadHabits() {

        try {
            FileInputStream fis = new FileInputStream(HABITS_FILE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            //Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-09-19
            Type listType = new TypeToken<ArrayList<Habit>>(){}.getType();
            habits = gson.fromJson(in, listType);


        } catch (FileNotFoundException e) {
            //TODO: implement exception
        }

    }

    public void addHabitToServer(Habit habit, User user) {

        Index index = new Index.Builder(habit).index("cmput301f17t07_ingroove").type("habit").build();

        try
        {
            DocumentResult result = ServerCommandManager.getClient().execute(index);
            if (result.isSucceeded()) {
                //habit.setHabitID(result.getId());
                System.out.println("SUCCESS --- addHabitToServer success");
            }
            else {
                System.out.println("FAILURE --- addHabitToServer failed");
            }
        }
        catch (Exception e)
        {
            System.out.println("FAILURE --- addHabitToServer caught exception: " + "\"" + e + "\"");
        }

    }




}



















