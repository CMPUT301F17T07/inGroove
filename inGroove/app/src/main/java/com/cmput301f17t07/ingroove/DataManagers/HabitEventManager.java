package com.cmput301f17t07.ingroove.DataManagers;

import android.content.Context;
import android.util.Log;

import com.cmput301f17t07.ingroove.DataManagers.Command.AddHabitEventCommand;
import com.cmput301f17t07.ingroove.DataManagers.Command.DeleteHabitEventCommand;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommand;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.GenericGetRequest;
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

import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

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
    public int addHabitEvent(Habit habit, HabitEvent event) {

        event.setHabitID(habit.getObjectID());
        event.setUserID(habit.getUserID());
        UniqueIDGenerator generator = new UniqueIDGenerator(habitEvents);
        String id = generator.generateNewID();
        event.setObjectID(habit.getUserID() + id);
        Log.d("--- NEW ID ---"," generated unique ID of: " + id );
        habitEvents.add(event);
        saveLocal();

        ServerCommand addHabitEventCommand = new AddHabitEventCommand(event);
        ServerCommandManager.getInstance().addCommand(addHabitEventCommand);

        //TODO: update this to the job scheduler
        ServerCommandManager.getInstance().execute();

        return 0;

    }

    /**
     * deletes the given HabitEvent
     * @param event the HabitEvent to be deleted
     */
    public void removeHabitEvent(HabitEvent event) {
        habitEvents.remove(event);
        saveLocal();

        ServerCommand deleteHabitEventCommand = new DeleteHabitEventCommand(event);
        ServerCommandManager.getInstance().addCommand(deleteHabitEventCommand);

        //TODO: update this to the job scheduler
        ServerCommandManager.getInstance().execute();

    }

    /**
     * used to edit a habitEvent from the old oldHE to the newHE
     * @param oldHE the original HabitEvent
     * @param newHE the modified HabitEvent
     * @return 0 for success, -1 for habitEvent not found
     */
    public int editHabitEvent(HabitEvent oldHE, HabitEvent newHE) {
        int index = habitEvents.indexOf(oldHE);
        if (index == -1) {
            return -1;
        }
        newHE.setObjectID(oldHE.getObjectID());
        newHE.setHabitID(oldHE.getHabitID());
        newHE.setUserID(oldHE.getUserID());
        habitEvents.remove(oldHE);
        habitEvents.add(index, newHE);
        saveLocal();

        ServerCommand addHabitEventCommand = new AddHabitEventCommand(newHE);
        ServerCommandManager.getInstance().addCommand(addHabitEventCommand);

        //TODO: update this to the job scheduler
        ServerCommandManager.getInstance().execute();

        return 0;
    }

    /**
     * Returns an list of HabitEvents for a particular habit a User has
     *
     * @param forHabit the habit for which the event history will be returned
     * @return a list of events for the specific habit
     */
    // TODO: will this work for other users habits???
    public ArrayList<HabitEvent> getHabitEvents(Habit forHabit) {
        if (habitEvents.size() == 0) {
            loadHabitEvents();
        }

        Log.d("-- getHabitEvents --"," NUMBER OF EVENTS " + habitEvents.size());

        ArrayList<HabitEvent> forHabitList = new ArrayList<>();
        for (HabitEvent event: habitEvents) {
            if (event.getHabitID() == null || forHabit.getObjectID() == null) {
                continue;
            }


            if (event.getHabitID().equals(forHabit.getObjectID())) {
                forHabitList.add(event);
            }
        }

        Log.d("-- getHabitEvents --"," NUMBER OF EVENTS RETURNED " + forHabitList.size());

        return forHabitList;
    }

    /**
     * Returns a list of all the HabitEvents a user has, not specific to a particular habit
     *
     * @return a list of all events the user has logged
     */
    // TODO: implement getting habits for other users
    public ArrayList<HabitEvent> getHabitEvents() {
        if (habitEvents.size() == 0) {
            loadHabitEvents();
            return habitEvents;
        }
        return habitEvents;
    }

    public void findHabitEvents(User forUser, AsyncResultHandler<HabitEvent> handler) {
        GenericGetRequest<HabitEvent> get = new GenericGetRequest(handler, HabitEvent.class, ServerCommandManager.HABIT_EVENT_TYPE, "userID");
        get.execute(forUser.getUserID());
    }

    public void findHabitEvents(Habit forHabit, AsyncResultHandler<HabitEvent> handler) {
        GenericGetRequest<HabitEvent> get = new GenericGetRequest<>(handler, HabitEvent.class, ServerCommandManager.HABIT_EVENT_TYPE, "habitID");
        get.execute(forHabit.getObjectID());
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

        ArrayList<HabitEvent> recentEvents = new ArrayList<>();

        // if looking for the recent events of the devices user
        if (user == DataManager.getInstance().getUser()) {
            for (int i = 0; i < habitEvents.size() ; i++) {
                HabitEvent event = habitEvents.get(i);

                // if event.day is on or after date
                if (event.getDay().compareTo(date) >= 0) {
                    recentEvents.add(event);
                }
            }

        } else {
            // TODO: needs server access to get the recent habits of the given user
            return null;
        }

        return recentEvents;
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



    /**
     * Save a local copy of the user HabitEvents on the disk for offline use of the application
     *
     * @see Gson
     * @see FileOutputStream
     * @see BufferedWriter
     * @see InGroove
     */
    private void saveLocal() {

        try {
            Context context = InGroove.getInstance();

            FileOutputStream fos = context.openFileOutput(HABIT_EVENTS_FILE, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(habitEvents, out);
            out.flush();


        } catch (FileNotFoundException e) {
            Log.d("---- ERROR -----"," Could not save habit events locally, caught exception: " + e);
        } catch (IOException e) {
            Log.d("---- ERROR -----"," Could not save habit events locally, caught exception: " + e);
        }

    }

    /**
     * Load the local copy of the user HabitEvents from the disk for offline use of application
     *
     * @see Gson
     * @see FileInputStream
     * @see BufferedReader
     * @see InGroove
     */
    private void loadHabitEvents(){

        try {
            Context context = InGroove.getInstance();

            FileInputStream fis = context.openFileInput(HABIT_EVENTS_FILE);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            //Taken from https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-09-19
            Type listType = new TypeToken<ArrayList<HabitEvent>>(){}.getType();
            habitEvents = gson.fromJson(in, listType);

            Log.d("--LOAD HABIT EVENTS--"," NUMBER OF EVENTS " + habitEvents.size());



        } catch (FileNotFoundException e) {
            Log.d("---- ERROR -----"," Could not load habit events from memory, caught exception: " + e);
        }

    }

    /**
     * method to add a HabitEvent to the server
     * !!!!!Must be called Async!!!!!
     *
     * @param habitEvent the HabtEvent to add to the server storage
     * @see ServerCommandManager
     * @see HabitEvent
     */
    public void addHabitEventToServer(HabitEvent habitEvent) throws Exception {

        Boolean isNew = true;

        Index.Builder builder = new Index.Builder(habitEvent).index(ServerCommandManager.INDEX).type(ServerCommandManager.HABIT_EVENT_TYPE);

        if (habitEvent.getObjectID() != null) {
            builder.id(habitEvent.getObjectID());
            isNew = false;
        }

        Index index = builder.build();

        DocumentResult result = ServerCommandManager.getClient().execute(index);
        if (result.isSucceeded() && isNew) {
            Log.d("---- ES -----"," Successfully saved event named " + habitEvent.getName() + " to ES.");
            //habitEvent.setEventID(result.getId());
            //saveLocal();
        } else if (result.isSucceeded()) {
            Log.d("---- ES -----","Successfully updated event name: " + habitEvent.getName() + " to ES.");
        } else {
            Log.d("---- ES -----","Something went wrong: " + result.getErrorMessage());

        }
    }

    /**
     * Method to delete a HabitEvent from the server
     * !!!!!Must be called Async!!!!!
     *
     * @param habitEvent the HabitEvent to be deleted
     * @throws Exception throws an exception if it cant delete from the server
     * @see HabitEvent
     */
    public void deleteHabitEventFromServer(HabitEvent habitEvent) throws Exception {

        Delete.Builder builder = new Delete.Builder(habitEvent.getObjectID()).index(ServerCommandManager.INDEX).type(ServerCommandManager.HABIT_EVENT_TYPE);

        Delete index = builder.build();
        DocumentResult result = ServerCommandManager.getClient().execute(index);
        if (result.isSucceeded()) {
            Log.d("---- ES -----"," Successfully Deleted event named " + habitEvent.getName() + " from ES.");
        }

    }



}
