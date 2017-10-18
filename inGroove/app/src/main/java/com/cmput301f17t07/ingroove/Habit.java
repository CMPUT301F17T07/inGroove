package com.cmput301f17t07.ingroove;

import java.util.ArrayList;

/**
 * Created by fraserbulbuc on 2017-10-17.
 */

class Habit {

    private String name;
    private String comment;
    private ArrayList<String> repeatedDays;
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

    public ArrayList<String> getRepeatedDays() {
        return repeatedDays;
    }

    public void setRepeatedDays(ArrayList<String> repeatedDays) {
        this.repeatedDays = repeatedDays;
    }

    public ArrayList<String> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<String> events) {
        this.events = events;
    }

    public Habit(String name, String comment, ArrayList<String> repeatedDays, ArrayList<String> events) {
        this.name = name;
        this.comment = comment;
        this.repeatedDays = repeatedDays;
        this.events = events;
    }

}
