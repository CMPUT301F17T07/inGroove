package com.cmput301f17t07.ingroove.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Abstraction of the application user.
 *
 * @see Habit
 * @see HabitEvent
 * @see Date
 *
 * Created by Ashley on 2017-10-22.
 */

public class User implements Serializable {

    // User attributes
    private String name;
    private String email;
    private String userID;
    private Date joinDate;
    private int streak;
    private Date streak_start;
    private Date streak_end;
    private int max_streak;

    /**
     * Default constructor, make a new user
     *
     * @param name the user's username
     * @param email the user's email
     * @param joinDate join date for user
     * @param streak current streak
     * @see Date
     */
    public User(String name, String email, Date joinDate, int streak, String uid, Date streak_start, Date streak_end, int max_streak) {
        this.name = name;
        this.email = email;
        this.joinDate = joinDate;
        this.streak = streak;
        this.userID = uid;
        this.streak_start = streak_start;
        this.streak_end = streak_end;
        this.max_streak = max_streak;
    }

    /**
     * Alternate constructor, make a new user
     *
     * @param name the user's username
     * @param email the user's email
     * @see Date
     */
    public User(String name, String email) {
        this(name, email, new Date(), 0, "", new Date(), new Date(), 0);
    }

    /**
     * Construct a new user with only a username
     *
     * @param name user's username
     */
    public User(String name) {
        this(name, "");
    }

    /**
     * Access to the user's name
     *
     * @return a String representing the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the user's username
     *
     * @param name a String representing the new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Access to the user's email
     *
     * @return a String representing the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the user's email
     *
     * @param email a String representing the user's email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Access to the user's ID
     *
     * @return a String representing the user's ID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Access to set the user's ID
     *
     * @param userID a String representing the ID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Access to the user's first time using the application
     *
     * @return a Date of the user's join data
     * @see Date
     */
    public Date getJoinDate() {
        return joinDate;
    }

    /**
     * Update the join date
     *
     * @param joinDate the new join date to set
     */
    // TODO: CAN WE REMOVE THIS? THE USER SHOULD NOT BE ABLE TO ALTER THEIR JOIN DATE?
    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    /**
     * Access to the user's current habit streak
     *
     * @return an int representing the user's ongoing streak
     */
    public int getStreak() {
        return streak;
    }

    /**
     * Update the user's current streak
     *
     * @param streak the new value of the streak
     */
    public void setStreak(int streak) {
        this.streak = streak;
    }

    /**
     * Get the date the current streak of the user started from
     * @return
     */
    public Date getStreak_start() {
        return streak_start;
    }

    /**
     * Set the date the users current streak starts from
     * @param streak_start
     */
    public void setStreak_start(Date streak_start) {
        this.streak_start = streak_start;
    }

    /**
     * Get the date the users current streak was last calculated up to
     * @return streak end calculation date
     */
    public Date getStreak_end() {
        return streak_end;
    }

    /**
     * Set the date the users current streak was last calculated up to
     * @param streak_end the new streak end calculation date
     */
    public void setStreak_end(Date streak_end) {
        this.streak_end = streak_end;
    }

    /**
     * Get the value of the users max streak
     * @return max streak value
     */
    public int getMax_streak() {
        return max_streak;
    }

    /**
     * Set the value of the max streak
     * @param max_streak the new max streak value
     */
    public void setMax_streak(int max_streak) {
        this.max_streak = max_streak;
    }

    /**
     * Override the equals method for comparison purposes, ensure that de-serialized users can still
     * be compared and identified as equals if they represent the same user
     *
     * @param obj the object to be compared
     * @return true if the users are the same, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        User temp = (User) obj;
        if (temp.getUserID() == this.userID) {
            return true;
        } else {
            return false;
        }

    }
}
