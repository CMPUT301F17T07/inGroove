package com.cmput301f17t07.ingroove.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Representing a recurring action to be done. Habits have names and optional descriptions. They may
 * repeat at regular intervals each week, but can only occur once a day.
 *
 * @see Identifiable
 * @see Serializable
 *
 * Created by fraserbulbuc on 2017-10-17.
 */

public class Habit implements Serializable, Identifiable {

    private String name;
    private String comment;
    private ArrayList<Day> repeatedDays;
    private ArrayList<String> events;
    private Date startDate;
    private String habitID;
    private String userID;

    /**
     * Provide access to the Habit ID
     *
     * @return the String ID
     */
    public String getHabitID() {
        return habitID;
    }

    /**
     * Set the habit ID
     *
     * @param habitID a String representing the new ID
     */
    public void setHabitID(String habitID) {
        this.habitID = habitID;
    }

    /**
     * Get the start date of the habit
     * @return the start date of the habit
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Set the start date of the habit
     * @param start_date
     */
    public void setStartDate(Date start_date) {
        this.startDate = start_date;
    }

    /**
     * Access to the identifiable String ID
     *
     * @return a String of the Habit ID
     * @see Identifiable
     */
    public String getID() {
        return userID;
    }

    /**
     * Access to the habit name
     *
     * @return a String representing the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name
     *
     * @param name a String representing the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Access to the comment
     *
     * @return a String representing the comment description of the habit
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set the comment
     *
     * @param comment a String representing the comment to be set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Check if the habit repeats on a particular day
     *
     * @param day the day to check
     * @return true if it does repeat, false if not
     * @see Day
     */
    public Boolean hasRepeatedDay(Day day) {
        return repeatedDays.contains(day);
    }

    /**
     * Set the repeated days
     *
     * @param repeatedDays a list of the days for the habit to repeat
     * @see Day
     */
    public void setRepeatedDays(ArrayList<Day> repeatedDays) {
        this.repeatedDays = repeatedDays;
    }

    /**
     * Get the repeating days for the habit to occur
     *
     * @return a list of the days the habit repeats on
     * @see Day
     */
    public ArrayList<Day> getRepeatedDays() { return repeatedDays; }

    /**
     * Get events logged for this habit
     *
     * @return a list of event IDs
     * @see Identifiable
     * @see HabitEvent
     */
    public ArrayList<String> getEvents() {
        return events;
    }

    /**
     * Update the events for this habit
     *
     * @param events a list of String ID of events
     * @see Identifiable
     * @see HabitEvent
     */
    public void setEvents(ArrayList<String> events) {
        this.events = events;
    }

    /**
     * Construct a new Habit
     *
     * @param name a String representing the name
     * @param comment a String representing the optional comment description
     * @param repeatedDays a list of repeating days for the habit to occur
     * @param events a list of String IDs representing events logged for the habit
     *
     * @see HabitEvent
     * @see Day
     */
    public Habit(String name, String comment, ArrayList<Day> repeatedDays, ArrayList<String> events, Date startDate) {
        this.name = name;
        this.comment = comment;
        this.repeatedDays = repeatedDays;
        this.events = events;
        this.habitID = "";
        this.startDate = startDate;

    }

    /**
     * Alternative constructor
     *
     * @param name a String representing the name
     * @param comment a String representing the comment description
     * @param repeatedDays a list of repeating Days
     * @param startDate a day the habit should start from
     *
     * @see Day
     */
    public Habit(String name, String comment, ArrayList<Day> repeatedDays, Date startDate) {
        this(name,comment, repeatedDays, new ArrayList<String>(), startDate);
    }

    /**
     * Alternative constructor
     *
     * @param name a String representing the name
     * @param comment a String representing the comment description
     * @param repeatedDays a list of repeating Days
     *
     * @see Day
     */
    public Habit(String name, String comment, ArrayList<Day> repeatedDays) {
        this(name,comment, repeatedDays, new ArrayList<String>(), new Date());
    }

    /**
     * Alternative constructor
     *
     * @param name a String representing the name
     * @param comment a String representing the comment description
     *
     * @see Day
     */
    public Habit(String name, String comment) {
        this(name, comment,new ArrayList<Day>(), new ArrayList<String>(), new Date());
    }

    /**
     * Prints a string describing the Habit
     *
     * @return a String describing the habit instance
     */
    public String getHabitString() {
        return "Habit: " + name + "\n Comment: " + comment + "\n";
    }

    /**
     * Add a single repeated day to the current repeating days
     *
     * @param day the extra day to be added
     * @see Day
     */
    public void addRepatedDay(Day day) {
        this.repeatedDays.add(day);
    }

    /**
     * Overridden equals method for comparison between habit objects, ensures that habits can still be compared
     * safely if they are serialized throughout the application
     *
     * @param obj the object to compare
     * @return true if they are representing the same habit, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Habit temp = (Habit) obj;
        return temp.getHabitID().equals(this.getHabitID()) && temp.getName().equals(this.getName())
                && temp.getComment().equals(this.getComment()) && temp.getRepeatedDays().equals(this.getRepeatedDays());
    }
}



















