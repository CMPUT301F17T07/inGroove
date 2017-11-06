package com.cmput301f17t07.ingroove.DataManagers;

/**
 * Created by Christopher Walter on 2017-10-31.
 */

import com.cmput301f17t07.ingroove.Model.Habit;
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

/**
 * Singleton class
 */
public class DataManager {



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






    private void saveLocal() {

        try {
            FileOutputStream fos = new FileOutputStream(USER_FILE, false);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(user, out);
            out.flush();

        } catch (FileNotFoundException e) {
            //TODO: implement exception
        } catch (IOException e) {
            //TODO: implement exception
        }

    }


    private void loadUser() {

        try {
            FileInputStream fis = new FileInputStream(USER_FILE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            user = gson.fromJson(in, User.class);

        } catch (FileNotFoundException e) {
            //TODO: implement exception
        }

    }

}























