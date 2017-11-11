package com.cmput301f17t07.ingroove.Model;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by fraserbulbuc on 2017-10-17.
 */

public class Habit implements Serializable, Identifiable {

    private String name;
    private String comment;
    private ArrayList<Day> repeatedDays;
    private ArrayList<String> events;
    private String habitID;
    private String userID;
    // TODO: habitID is not in a constructor

    public String getHabitID() {
        return habitID;
    }

    public void setHabitID(String habitID) {
        this.habitID = habitID;
    }

    public String getID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean hasRepeatedDay(Day day) {
        return repeatedDays.contains(day);
    }

    public void setRepeatedDays(ArrayList<Day> repeatedDays) {
        this.repeatedDays = repeatedDays;
    }

    public ArrayList<Day> getRepeatedDays() { return repeatedDays; }

    public ArrayList<String> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<String> events) {
        this.events = events;
    }

    public Habit(String name, String comment, ArrayList<Day> repeatedDays, ArrayList<String> events) {
        this.name = name;
        this.comment = comment;
        this.repeatedDays = repeatedDays;
        this.events = events;
        this.habitID = null;

    }

    public Habit(String name, String comment, ArrayList<Day> repeatedDays) {
        this(name,comment, repeatedDays, new ArrayList<String>());
    }

    public Habit(String name, String comment) {
        this(name, comment,new ArrayList<Day>(), new ArrayList<String>());
    }

    public String getHabitString() {
        return "Habit: " + name + "\n Comment: " + comment + "\n";
    }

    public void addRepatedDay(Day day) {
        this.repeatedDays.add(day);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Habit temp = (Habit) obj;
        return temp.getHabitID().equals(this.getHabitID());

    }
}



















