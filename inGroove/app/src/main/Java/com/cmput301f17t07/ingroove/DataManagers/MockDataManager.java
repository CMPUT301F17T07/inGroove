package com.cmput301f17t07.ingroove.DataManagers;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.User;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

/**
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
        Following.add( new User("T-Rex Joe", "TRexJoe@Hotmail.com", new Date(), 9999, "RAWR") );
        Following.add( new User("Sheriff of Nottingham", "RobinHoodSux@Hotmail.com", new Date(), 2, "BigMeanie") );
        Following.add( new User("The Duke of Dude", "TheDudeAbides@Hotmail.com", new Date(), 100, "CoolMan") );
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
     * @param user the user for which the habits should be retrieved
     * @return a list of habits
     *
     * @see Habit
     */
    public ArrayList<Habit> getHabit(User user) {
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
     * @param forUser the user in which the habitEvents are wanted
     * @return a list of test HabitEvents
     */
    @Override
    public ArrayList<HabitEvent> getHabitEvents(User forUser) {
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

    // TODO: CAN WE REMOVE THIS?
    @Override
    public int editUser(User user) {
        return 0;
    }

    /**
     * Add a new user
     *
     * @param userName String representing the user's username
     * @return Can we return 0 if success, -1 if any issues instead of a string
     */
    public boolean addUser(String userName) {
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



    /*  ------------------------- These methods return null data for now ------------------------- */

    /**
     * Retrieve the current users who want to follow the current user
     *
     * @return an array list of users who want to follow the current user
     */
    @Override
    public ArrayList<User> getFollowRequests() {
        return null;
    }

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
     * @return true if the rejection was successful, false if not
     */
    @Override
    public Boolean rejectRequest(User user) {
        return null;
    }

    /**
     * Get the users which the specified user follows
     *
     * @param user the user you want to get the followers of
     * @return a list of the particular user's followers
     */
    @Override
    public ArrayList<User> getWhoThisUserFollows(User user) {
        return Following;
    }

    /**
     * Gets the followers of a particular user
     *
     * @param user a list of users who follow the specified user
     * @return a list of users who follow the specified user
     */
    @Override
    public ArrayList<User> getWhoFollows(User user) {
        return Following;
    }

    /**
     * Search users
     *
     * @param query            the search query
     * @param alreadyFollowing if true, do not include the users you are already following
     * @param minStreak        the min streak to include
     * @return a list of the users who meet the criteria
     */
    @Override
    public ArrayList<User> findUsers(String query, Boolean alreadyFollowing, int minStreak) {
        return null;
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
     * Search Habits
     *
     * @param query the search query
     * @return a list of habits that contain the search query
     */
    @Override
    public ArrayList<Habit> findHabits(String query) {
        return null;
    }

    /**
     * Search HabitEvents
     *
     * @param query the search query
     * @return a list of habits that contain the search query
     */
    @Override
    public ArrayList<HabitEvent> findHabitEvents(String query) {
        return null;
    }

    /**
     * Get the habit events within a specified radius
     *
     * @param radius the radius in kilometers
     * @param centre the centre of the circle to find habit events within
     * @return a list of the habit events
     */
    @Override
    public ArrayList<HabitEvent> getHabitEventsWithinRange(int radius, LatLng centre) {
        return null;
    }
}
