package com.cmput301f17t07.ingroove.DataManagers.Command;

import android.arch.lifecycle.MutableLiveData;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.SuperCombinedManagerObjectToManageTheMostRecentHabitForUser;
import com.cmput301f17t07.ingroove.Model.User;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

/**
 * [Facade Interface]
 * An interface which represents all the methods for frontend objects to interact with the data.
 * All frontend objects, which need data, should contain a reference to a DataManagerAPI object
 * so that they can call the methods listed below.
 *
 * Both the real and mock data manager facades should implement this interface.
 *
 * Created by fraserbulbuc on 2017-11-07.
 */

public interface DataManagerAPI {

    /**
     * Retrieves the habits for a user
     *
     * @return a list of habit objects
     * @see User
     */
    ArrayList<Habit> getHabits();

    /**
     *  Retrieves the habitEvents for a particular habit
     *
     * @param forHabit the habit in which the habitEvents are wanted
     * @return a list of habitEvents for that particular habit
     * @see HabitEvent
     */
    ArrayList<HabitEvent> getHabitEvents(Habit forHabit);

    /**
     * Retrieves the habitEvents for a particular user
     *
     * @return a list of all the habitEvents a user has
     * @see User
     */
    ArrayList<HabitEvent> getHabitEvents();

    /**
     * Adds a habit object to the data storage
     *
     * @param habit the new habit to be added
     * @return 0 if success, -1 if any issues
     * @see User
     */
    int addHabit(Habit habit);

    /**
     * Removes a habit from storage
     *
     * @param habit the habit to be removed
     * @return 0 if success, -1 if any issues
     * @see Habit
     */
    int removeHabit(Habit habit);

    /**
     * Updates the contents of a habit in storage with new information
     *
     * @param oldHabit the habit to be updated
     * @param newHabit the new habit data to replace the old data
     * @return 0 if success, -1 if any issues
     * @see Habit
     */
    int editHabit(Habit oldHabit, Habit newHabit);

    /**
     * Adds a habitEvent for a habit to storage
     *
     * @param habit the habit for which the event is being logged
     * @param habitEvent the habitEvent to add
     * @return 0 if success, -1 if any issues
     * @see Habit
     * @see HabitEvent
     */
    int addHabitEvent(Habit habit, HabitEvent habitEvent);

    /**
     * Removes a habitEvent from storage
     *
     * @param habitEvent the habitEvent to be removed
     * @return 0 if success, -1 if any issues
     * @see HabitEvent
     */
    int removeHabitEvent(HabitEvent habitEvent);

    /**
     * Updates an existing habitEvent with new data
     *
     * @param oldHabitEvent the habitEvent being updated
     * @param newHabitEvent the new data to update the old event with
     * @return 0 if success, -1 if any issues
     * @see HabitEvent
     */
    int editHabitEvent(HabitEvent oldHabitEvent, HabitEvent newHabitEvent);

    int editUser(User user);

    /**
     * Removes a user from the application, all data will be lost for that user
     *
     * @param user the user to be removed
     * @return 0 if success, -1 if any issues
     * @see User
     */
    int removeUser(User user);

    /**
     * Retrieves the current user
     *
     * @return a User instance representing the current user
     * @see User
     */
    User getUser();

    /**
     * Adds a new user to storage.
     *
     * @param userName a string representing the user's username
     * @return true if success, false if any issues
     * @see User
     */
    boolean addUser(String userName, AsyncResultHandler handler);

    /**
     * used to pass users between activities
     * only returns the user once and then returns null til a new user is set by setPassedUser(User passedUser)
     *
     * @return the last user passed using setPassedUser(User passedUser)
     */
    User getPassedUser();

    /**
     * used to pass users between activities
     *
     * @param passedUser the user to be passed, is return by getPassedUser()
     */
    void setPassedUser(User passedUser);

    /**
     * used to pass habit between activities
     * only returns the habit once and then returns null til a new user is set by setPassedHabit(Habit passedHabit)
     *
     * @return the last habit passed using setPassedHabit(Habit passedHabit)
     */
    Habit getPassedHabit();

    /**
     * used to pass users between activities
     *
     * @param passedHabit the user to be passed, is return by getPassedHabit()
     */
    void setPassedHabit(Habit passedHabit);

    /**
     * used to pass habitEvents between activities
     * only returns the habitEvent once and then returns null til a new user is set by setPassedHabitEvent(HabitEvent passedHabitEvent)
     *
     * @return the last habit passed using setPassedHabitEvent(HabitEvent passedHabitEvent)
     */
    HabitEvent getPassedHabitEvent();

    /**
     * used to pass users between activities
     *
     * @param passedHabitEvent the user to be passed, is return by getPassedHabitEvent()
     */
    void setPassedHabitEvent(HabitEvent passedHabitEvent);

    /**
     * Retrieve the current users who want to follow the current user
     *
     * @return an array list of users who want to follow the current user
     */
    int getFollowRequests(AsyncResultHandler handler);

    /**
     * Accept a follow request by a user
     *
     * @param user the user that is allowed to follow the current user
     * @return true if the acceptance was successful, false if not
     */
    Boolean acceptRequest(User user);

    /**
     * Reject a pending follow request
     *
     * @param user
     * @return true if the rejection was successful, false if not
     */
    Boolean rejectRequest(User user, AsyncResultHandler handler);

    /**
     * Unfollows the given user
     * @param user the user to unfollow
     */
    void unFollow(User user);

    /**
     * Get the users which the specified user follows
     *
     * @param user the user you want to get the followers of
     * @return a list of the particular user's followers
     */
    int getWhoThisUserFollows(User user, AsyncResultHandler handler);

    /**
     * Gets the followers of a particular user
     *
     * @param user a list of users who follow the specified user
     * @return a list of users who follow the specified user
     */
    int getWhoFollows(User user, AsyncResultHandler handler);

    /**
     * Search users
     *
     * @param minStreak the min streak to include
     * @param query the search query
     * @param alreadyFollowing if true, do not include the users you are already following
     * @return a list of the users who meet the criteria
     */
    int findUsers(int minStreak, String query, Boolean alreadyFollowing, AsyncResultHandler handler);

    /**
     * Send a request to follow the user
     *
     * @param user the user the current user wants to follow
     * @return true if success, false if not
     */
    Boolean sendFollowRequest(User user);

    /**
     * Cancel a pending follow request
     *
     * @param user
     * @return true if the rejection was successful, false if not
     */
    public Boolean cancelRequest(User user, AsyncResultHandler handler);

    /**
     * Search Habits
     *
     * @param forUser the search query
     * @return a list of habits that contain the search query
     */
     int findHabits(User forUser, AsyncResultHandler<Habit> handler);

    /**
     * Search HabitEvents
     *
     * @param forHabit the search query
     * @return a list of habits that contain the search query
     */
    int findHabitEvents(Habit forHabit, AsyncResultHandler<HabitEvent> handler);

    /**
     * Search HabitEvents
     *
     * @param forUser the search query
     * @return a list of habits that contain the search query
     */
    int findHabitEvents(User forUser, AsyncResultHandler<HabitEvent> handler);

    /**
     * returns an Array of SuperCombinedManagerObjectToManageTheMostRecentHabitForUser that holds the Habit and its most recent event for the User
     *
     * @param forUser the User to get the array of most recent events
     * @param handler what to call when the results come back
     */
    void findMostRecentEvent(User forUser, AsyncResultHandler<SuperCombinedManagerObjectToManageTheMostRecentHabitForUser> handler);


}
