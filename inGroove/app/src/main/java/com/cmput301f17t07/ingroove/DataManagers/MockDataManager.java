package com.cmput301f17t07.ingroove.DataManagers;

import android.arch.lifecycle.MutableLiveData;
import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.User;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Date;

/**
 * [TESTING ONLY - Singleton Model Class]
 * A singleton instance of the DataManagerAPI for the frontend objects to interact with while the real data
 * manager is being created and tested. Returns hard coded objects for each method defined in the
 * interface
 *
 * @see DataManagerAPI
 * @see HabitEvent
 * @see Habit
 *
 * Created by fraserbulbuc on 2017-11-07.
 */
@Deprecated
public class MockDataManager implements DataManagerAPI {

    /**
     * Create the singleton instance
     */
    private static MockDataManager instance = new MockDataManager();

    private ArrayList<Habit> habits;
    private ArrayList<HabitEvent> events;
    private ArrayList<User> users;
    private ArrayList<User> Following;

    private User passedUser;
    private Habit passedHabit;
    private HabitEvent passedHabitEvent;

    /**
     * Constructor to make the singleton instance of the MockDataManager, adds some data to the arrays
     *
     * @see HabitEvent
     * @see Habit
     * @see User
     */
    public MockDataManager() {

        habits = new ArrayList<>();
        habits.add(new Habit("Test Habit 1", "Test Habit Comment"));
        habits.add(new Habit("Test Habit 2", "Test Habit Comment"));
        habits.add(new Habit("Test Habit 3", "Test Habit Comment"));

        events = new ArrayList<>();
        events.add(new HabitEvent("Test Habit 1", new Date()));
        events.add(new HabitEvent("Test Habit 2", new Date()));
        events.add(new HabitEvent("Test Habit 3", new Date()));

        users = new ArrayList<User>();

        Following = new ArrayList<User>();
        Following.add( new User("T-Rex Joe", "TRexJoe@Hotmail.com", new Date(), 9999, "RAWR", new Date(), new Date(), 10001));
        Following.add( new User("Sheriff of Nottingham", "RobinHoodSux@Hotmail.com", new Date(), 2, "BigMeanie", new Date(), new Date(), 2) );
        Following.add( new User("The Duke of Dude", "TheDudeAbides@Hotmail.com", new Date(), 100, "CoolMan", new Date(), new Date(), 99) );
    }

    /**
     * Provides access to the singleton instance
     *
     * @return a singleton Instance of the MockDataManager
     */
    public static MockDataManager getInstance(){
        return instance;
    }

    /**
     * Access to some test habits
     *
     * @return a list of habits
     *
     * @see Habit
     */
    public ArrayList<Habit> getHabits() {
        return habits;
    }

    /**
     * Access to some test habit events
     *
     * @param forHabit the habit in which the habitEvents are wanted
     * @return a list of test HabitEvents
     */
    public ArrayList<HabitEvent> getHabitEvents(Habit forHabit) {
        return events;
    }

    /**
     * Access to some test habit Events
     *
     * @return a list of test HabitEvents
     */
    @Override
    public ArrayList<HabitEvent> getHabitEvents() {
        return events;
    }

    /**
     * Add a habit to the test habit list
     *
     * @param habit the new habit to be added
     * @return 0 if success, -1 if any issues
     */
    public int addHabit(Habit habit) {
        habits.add(habit);
        return 0;
    }

    /**
     * Remove a habit from the list of test habits
     *
     * @param habit the habit to be removed
     * @return 0 if success, -1 if any issues
     */
    public int removeHabit(Habit habit) {
        habits.remove(habit);
        return 0;
    }

    /**
     * Update a test habit
     *
     * @param oldHabit the habit to be updated
     * @param newHabit the new habit data to replace the old data
     * @return0 if success, -1 if any issues
     */
    public int editHabit(Habit oldHabit, Habit newHabit) {
        return 0;
    }

    /**
     * Add a habit event to the list of test events
     *
     * @param habit the habit for which the event is being logged
     * @param habitEvent the habitEvent to add
     * @return 0 if success, -1 if any issues
     */
    public int addHabitEvent(Habit habit, HabitEvent habitEvent) {
        events.add(habitEvent);
        return 0;
    }

    /**
     * Remove a test habitEvent from the list
     *
     * @param habitEvent the habitEvent to be removed
     * @return 0 if success, -1 if any issues
     */
    public int removeHabitEvent(HabitEvent habitEvent) {
        events.remove(habitEvent);
        return 0;
    }

    /**
     * Update a habit event
     *
     * @param oldHabitEvent the habitEvent being updated
     * @param newHabitEvent the new data to update the old event with
     * @return 0 if success, -1 if any issues
     */
    public int editHabitEvent(HabitEvent oldHabitEvent, HabitEvent newHabitEvent) {
        return 0;
    }

    @Override
    public int editUser(User user) {
        return 0;
    }

    /**
     * Add a new user
     *
     * @param userName String representing the user's username
     * @return true if success, false if not
     */
    public boolean addUser(String userName, AsyncResultHandler handler) {
        users.add(new User(userName, "HARDCODED EMAIL"));
        return true;
    }

    /**
     * Remove a user
     *
     * @param user the user to be removed
     * @return 0 if success, -1 if any issues
     */
    public int removeUser(User user) {
        return 0;
    }

    /**
     * Access to the test user
     *
     * @return the test User
     */
    public User getUser() {
        // just returning the first user for now, null if unavailable
        if (users.size() == 0){
            return null;
        }
        return users.get(0);
    }

    public User getPassedUser() {
        User temp = passedUser;
        passedUser = null;
        return temp;
    }

    public void setPassedUser(User passedUser) {
        this.passedUser = passedUser;
    }

    public Habit getPassedHabit() {
        Habit temp = passedHabit;
        passedHabit = null;
        return temp;
    }

    public void setPassedHabit(Habit passedHabit) {
        this.passedHabit = passedHabit;
    }

    public HabitEvent getPassedHabitEvent() {
        HabitEvent temp = passedHabitEvent;
        passedHabitEvent = null;
        return temp;
    }

    public void setPassedHabitEvent(HabitEvent passedHabitEvent) {
        this.passedHabitEvent = passedHabitEvent;
    }

    /**
     * Retrieve the current users who want to follow the current user
     *
     * @param resultHandler
     * @return an array list of users who want to follow the current user
     */
    @Override
    public int getFollowRequests(AsyncResultHandler resultHandler) {
        return 0;
    }

    /*  ------------------------- These methods return null data for now ------------------------- */

    /**
     * Accept a follow request by a user
     *
     * @param user the user that is allowed to follow the current user
     * @return true if the acceptance was successful, false if not
     */
    @Override
    public Boolean acceptRequest(User user) {
        return null;
    }

    /**
     * Reject a pending follow request
     *
     * @param user
     * @param handler
     * @return true if the rejection was successful, false if not
     */
    @Override
    public Boolean rejectRequest(User user, AsyncResultHandler handler) {
        return null;
    }

    /**
     * Unfollows the given user
     *
     * @param user the user to unfollow
     */
    @Override
    public void unFollow(User user) {

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
    public int findUsers(int minStreak, String query, Boolean alreadyFollowing, AsyncResultHandler handler) {
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
        return null;
    }

    /**
     * Cancel a pending follow request
     *
     * @param user
     * @param handler
     * @return true if the rejection was successful, false if not
     */
    @Override
    public Boolean cancelRequest(User user, AsyncResultHandler handler) {
        return null;
    }

    /**
     * Search Habits
     *
     * @param forUser the search query
     * @return a list of habits that contain the search query
     */
    @Override
    public int findHabits(User forUser, AsyncResultHandler handler) {
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
    public int findHabitEvents(Habit forHabit, AsyncResultHandler handler) {
        return 0;
    }

    @Override
    public int findHabitEvents(User forUser, AsyncResultHandler handler) {
        return 0;
    }

    /**
     * returns an Array of SuperCombinedManagerObjectToManageTheMostRecentHabitForUser that holds the Habit and its most recent event for the User
     *
     * @param forUser the User to get the array of most recent events
     * @param handler what to call when the results come back
     */
    @Override
    public void findMostRecentEvent(User forUser, AsyncResultHandler handler) {
        
    }

}
