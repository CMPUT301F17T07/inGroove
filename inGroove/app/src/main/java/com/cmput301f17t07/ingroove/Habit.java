package com.cmput301f17t07.ingroove;

import java.util.ArrayList;

/**
 * Created by fraserbulbuc on 2017-10-17.
 */

public class Habit {

    private String name;
    private String comment;
    private ArrayList<Day> repeatedDays;
    private ArrayList<String> events;

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
    }

    public Habit(String name, String comment, ArrayList<Day> repeatedDays) {
        this.name = name;
        this.comment = comment;
        this.repeatedDays = repeatedDays;
        this.events = new ArrayList<>();
    }

    public Habit(String name, String comment) {
        this.name = name;
        this.comment = comment;
        this.repeatedDays = new ArrayList<>();
        this.events = new ArrayList<>();
    }

    public String getHabitString() {
        return "Habit: " + name + "\n Comment: " + comment + "\n";
    }

    public void addRepatedDay(Day day) {
        this.repeatedDays.add(day);
    }

}
