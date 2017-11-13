package com.cmput301f17t07.ingroove.DataManagers.Command;

import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.User;

import java.util.ArrayList;

/**
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
     * @param user the user for which the habits should be retrieved
     * @return a list of habit objects
     * @see User
     */
    ArrayList<Habit> getHabit(User user);

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
     * @param forUser the user in which the habitEvents are wanted
     * @return a list of all the habitEvents a user has
     * @see User
     */
    ArrayList<HabitEvent> getHabitEvents(User forUser);

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

    // TODO: CAN WE REMOVE THIS METHOD? WE DO NOT NEED IT.
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
     * @param s a string representing the user's username
     * @return 0 if success, -1 if any issues
     * @see User
     */
    // TODO: WHY ARE WE RETURNING A STRING?
    String addUser(String s);
}
