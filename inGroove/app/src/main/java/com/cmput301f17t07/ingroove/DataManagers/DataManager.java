package com.cmput301f17t07.ingroove.DataManagers;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;
import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommand;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import com.cmput301f17t07.ingroove.DataManagers.Command.UpdateUserCommand;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AcceptFollowRequestObjIDTask;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.GenericDeleteFollowRequest;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.GenericGetRequest;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.SuperCombinedManagerObjectToManageTheMostRecentHabitForUser;
import com.cmput301f17t07.ingroove.Model.User;
import com.google.android.gms.maps.model.LatLng;
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
import java.util.Collections;
import java.util.Comparator;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * [Singleton Model Class]
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
        ServerCommandManager.getInstance().execute();
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
     * only called once on start up
     *
     * @param userName a string representing the user's username
     * @return true if success, false if any issues
     * @see User
     */
    @Override
    public boolean addUser(String userName, AsyncResultHandler handler) {

        user = new User(userName);
        ServerCommandManager.InitializeUserAsync addUserTask = new ServerCommandManager.InitializeUserAsync(handler);
        System.out.println("---- NEW USER ---- with name " + user.getName());
        addUserTask.execute(user);
        System.out.println("---- NEW USER ---- with name " + user.getName());

//        saveLocal();

        return true;
    }


    public int editUser(User user) {

        user.setUserID(this.user.getUserID());

        this.user = user;
        
        saveLocal();

        ServerCommand updateUserCommand = new UpdateUserCommand(DataManager.getInstance().getUser());
        ServerCommandManager.getInstance().addCommand(updateUserCommand);
        ServerCommandManager.getInstance().execute();
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
     * @return a list of habit objects
     * @see Habit
     */
    public ArrayList<Habit> getHabits() {
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
     * @return a list of all the habitEvents a user has
     * @see HabitEvent
     * @see User
     */
    @Override
    public ArrayList<HabitEvent> getHabitEvents() {
        return habitEventManager.getHabitEvents();
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
    public void addUserToServer(User user) throws Exception {

        boolean isNew = true;

        Index.Builder builder = new Index.Builder(user).index(ServerCommandManager.INDEX).type(ServerCommandManager.USER_TYPE);

        if (user.getUserID() != null && !user.getUserID().isEmpty()){
            builder.id(user.getUserID());
            isNew = false;
        }

        Index index = builder.build();

        DocumentResult result = ServerCommandManager.getClient().execute(index);
        if (result.isSucceeded() && isNew) {
            user.setUserID(result.getId());
            saveLocal();

        } else if (result.isSucceeded()) {
            saveLocal();
        } else {
            Exception e = new Exception("Failed to add User to ES");

            Log.d("---- USER ----"," Failed to add user with name " + user.getName() + " to server. Caught " + e);

            throw e;
        }
    }

    /*  ------------------------- TODO: METHODS BELOW THIS HAVE YET TO BE IMPLEMENTED ------------------------- */

    /**
     * Retrieve the current users who want to follow the current user
     *
     * @return an array list of users who want to follow the current user
     */
    @Override
    public int getFollowRequests(AsyncResultHandler resultHandler) {
        RelationshipManager.getInstance().getFollowRequests(resultHandler, this.user.getUserID());
        return 0;
    }

    /**
     * Accept a follow request by a user
     *
     * @param user the user that is allowed to follow the current user
     * @return true if the acceptance was successful, false if not
     */
    @Override
    public Boolean acceptRequest(final User user) {
        AcceptFollowRequestObjIDTask acc = new AcceptFollowRequestObjIDTask(ServerCommandManager.FOLLOW, this.user.getUserID(), new AsyncResultHandler<Boolean>() {
            @Override
            public void handleResult(ArrayList<Boolean> result) {
                if (result.get(0) != null) {
                    Log.d("--- DATA MNG ---", "Successfully accepted " + user.getName() + "'s follow request.");
                } else {
                    Log.d("--- DATA MNG ---", "Failed to accept " + user.getName() + "'s follow request.");
                }
            }
        });
        acc.execute(user.getUserID());
        return true;
    }

    /**
     * Reject a pending follow request
     *
     * @param user
     * @return true if the rejection was successful, false if not
     */
    @Override
    public Boolean rejectRequest(User user, AsyncResultHandler handler) {
        GenericDeleteFollowRequest del = new GenericDeleteFollowRequest(user.getUserID(), handler);
        del.execute(this.user.getUserID());
        return true;
    }

    @Override
    public void unFollow(User user) {
        GenericDeleteFollowRequest del = new GenericDeleteFollowRequest(this.getUser().getUserID(), null);
        del.execute(user.getUserID());
    }

    /**
     * Cancel a pending follow request
     *
     * @param user
     * @return true if the rejection was successful, false if not
     */
    @Override
    public Boolean cancelRequest(User user, AsyncResultHandler handler) {
        GenericDeleteFollowRequest del = new GenericDeleteFollowRequest(this.user.getUserID(), handler);
        del.execute(user.getUserID());
        return true;
    }

    /**
     * Get the users which the specified user follows
     *
     * @param user    the user you want to get the followers of
     * @param handler
     * @return a list of the particular user's followers
     */
    @Override
    public int getWhoThisUserFollows(User user, AsyncResultHandler handler) {
        RelationshipManager.getInstance().getWhoThisUserFollows(handler, user);
        return 0;
    }

    /**
     * Gets the followers of a particular user
     *
     * @param user    a list of users who follow the specified user
     * @param handler
     * @return a list of users who follow the specified user
     */
    @Override
    public int getWhoFollows(User user, AsyncResultHandler handler) {
        RelationshipManager.getInstance().getFollowersOf(handler, user.getUserID());
        return 0;
    }

    /**
     * Search users
     *
     * @param minStreak        the min streak to include
     * @param query            the search query
     * @param alreadyFollowing if true, do not include the users you are already following
     * @param handler
     * @return a list of the users who meet the criteria
     */
    @Override
    public int findUsers(final int minStreak, String query, Boolean alreadyFollowing, final AsyncResultHandler handler) {
        GenericGetRequest<User> get = new GenericGetRequest<>(new AsyncResultHandler<User>() {
            @Override
            public void handleResult(ArrayList<User> result) {

                ArrayList<User> aboveMin = new ArrayList<>();

                for (User user : result) {
                    if (user.getMax_streak() >= minStreak) {
                        aboveMin.add(user);
                    }
                }

                Collections.sort(result, new Comparator<User>() {
                    @Override
                    public int compare(User u1, User u2) {
                        if (u1.getMax_streak() > u2.getMax_streak()) {
                            return -1;
                        } else if (u1.getMax_streak() < u2.getMax_streak()) {
                            return 1;
                        }
                        return 0;
                    }
                });

                handler.handleResult(aboveMin);
            }
        }, User.class, "user", "name");
        get.execute(query.toLowerCase());
        return 0;
    }

    /**
     * Send a request to follow the user
     *
     * @param user the user the current user wants to follow
     * @return true if success, false if not
     */
    @Override
    public Boolean sendFollowRequest(User user) {
        if (RelationshipManager.getInstance().sendFollowRequest(this.user, user) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Search Habits
     *
     * @param forUser the search query
     * @return a list of habits that contain the search query
     */
    @Override
    public int findHabits(User forUser, AsyncResultHandler<Habit> handler) {
        habitManager.findHabits(handler, forUser);
        return 0;
    }

    /**
     * Search HabitEvents
     *
     * @param forHabit   the search query
     * @param handler
     * @return a list of habits that contain the search query
     */
    @Override
    public int findHabitEvents(Habit forHabit, AsyncResultHandler<HabitEvent> handler) {
        habitEventManager.findHabitEvents(forHabit, handler);
        return 0;
    }

    @Override
    public int findHabitEvents(User forUser, AsyncResultHandler handler) {
        habitEventManager.findHabitEvents(forUser, handler);
        return 0;
    }

    /**
     * returns an Array of SuperCombinedManagerObjectToManageTheMostRecentHabitForUser that holds the Habit and its most recent event for the User
     *
     * @param forUser the User to get the array of most recent events
     * @param handler what to call when the results come back
     */
    @Override
    public void findMostRecentEvent(final User forUser, final AsyncResultHandler<SuperCombinedManagerObjectToManageTheMostRecentHabitForUser> handler) {
        Log.d("FIND MOST RECENT EVENTS-- 1", "Starting");

        findHabits(forUser, new AsyncResultHandler<Habit>() {

            int total;
            @Override
            public void handleResult(ArrayList<Habit> result) {
                if (result.size() == 0) {
                    ArrayList<SuperCombinedManagerObjectToManageTheMostRecentHabitForUser> temp = new ArrayList<>();
                    handler.handleResult(temp);
                } else {
                    total = result.size();
                    Log.d("FIND MOST RECENT EVENTS-- 2", "Number of habits " + String.valueOf(total));

                    for (final Habit habit: result) {
                        findHabitEvents(habit, new AsyncResultHandler<HabitEvent>() {
                            @Override
                            public void handleResult(ArrayList<HabitEvent> habitEvents) {
                                Log.d("FIND MOST RECENT EVENTS-- 3", "habitEvents size: " + String.valueOf(habitEvents.size()));
                                if (habitEvents.size() == 0) {
                                    ArrayList<SuperCombinedManagerObjectToManageTheMostRecentHabitForUser> temp = new ArrayList<>();
                                    finalHandler.handleResult(temp);
                                } else {
                                    HabitEvent mostRecentEvent = habitEvents.get(0);
                                    for (HabitEvent habitEvent : habitEvents) {
                                        Log.d("FIND MOST RECENT EVENTS-- 3.1", "habitEvent on day: " + habitEvent.getDay().toString());
                                        Log.d("FIND MOST RECENT EVENTS-- 3.2", "mostRecent : " + mostRecentEvent.getDay().toString());
                                        Log.d("FIND MOST RECENT EVENTS-- 3.3", "mostRecent.compareTo(habitEvent) = : " + String.valueOf(mostRecentEvent.getDay().compareTo(habitEvent.getDay())));



                                        if (mostRecentEvent.getDay().compareTo(habitEvent.getDay()) < 0){
                                            mostRecentEvent = habitEvent;
                                        }
                                    }

                                    Log.d("FIND MOST RECENT EVENTS-- 3.4", "mostRecent = : " + mostRecentEvent.getDay().toString());


                                    SuperCombinedManagerObjectToManageTheMostRecentHabitForUser result = new SuperCombinedManagerObjectToManageTheMostRecentHabitForUser(forUser, habit, mostRecentEvent);
                                    ArrayList<SuperCombinedManagerObjectToManageTheMostRecentHabitForUser> temp = new ArrayList<>();
                                    temp.add(result);
                                    finalHandler.handleResult(temp);
                                }
                            }
                        });
                    }
                }

            }

            ArrayList<SuperCombinedManagerObjectToManageTheMostRecentHabitForUser> superCombinedManagerObjectToManageTheMostRecentHabitForUserArrayList = new ArrayList<>();

            AsyncResultHandler<SuperCombinedManagerObjectToManageTheMostRecentHabitForUser> finalHandler = new AsyncResultHandler<SuperCombinedManagerObjectToManageTheMostRecentHabitForUser>() {
                int count = 0;

                @Override
                public void handleResult(ArrayList<SuperCombinedManagerObjectToManageTheMostRecentHabitForUser> result) {
                    if (count < total) {
                        count++;
                        Log.d("FIND MOST RECENT EVENTS-- 4", "Adding: " + String.valueOf(result.size()) + " to the final return Array");
                        if (result.size() != 0){
                            superCombinedManagerObjectToManageTheMostRecentHabitForUserArrayList.add(result.get(0));
                        }
                    }

                    if (count == total) {
                        Log.d("FIND MOST RECENT EVENTS-- Finished", "Returning: " + String.valueOf(superCombinedManagerObjectToManageTheMostRecentHabitForUserArrayList.size()) + " results");

                        handler.handleResult(superCombinedManagerObjectToManageTheMostRecentHabitForUserArrayList);
                    }
                }
            };


        });
    }



}























