package com.cmput301f17t07.ingroove.DataManagers;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by fraserbulbuc on 2017-11-07.
 */

public class MockDataManager implements DataManagerAPI {

    private ArrayList<Habit> habits;
    private ArrayList<HabitEvent> events;


    public MockDataManager() {

        habits = new ArrayList<>();
        habits.add(new Habit("Test Habit 1", "Test Habit Comment"));
        habits.add(new Habit("Test Habit 2", "Test Habit Comment"));
        habits.add(new Habit("Test Habit 3", "Test Habit Comment"));

        events = new ArrayList<>();
        events.add(new HabitEvent("Test Habit 1", new Date()));
        events.add(new HabitEvent("Test Habit 2", new Date()));
        events.add(new HabitEvent("Test Habit 3", new Date()));

    }

    public ArrayList<Habit> getHabit(User user) {
        return habits;
    }

    public ArrayList<HabitEvent> getHabitEvents(Habit habit) {
        return events;
    }

    public int addHabit(Habit habit) {
        habits.add(habit);
        return 0;
    }

    public int removeHabit(Habit habit) {
        habits.remove(habit);
        return 0;
    }

    public int editHabit(Habit oldHabit, Habit newHabit) {
        return 0;
    }

    public int addHabitEvent(HabitEvent habitEvent) {
        events.add(habitEvent);
        return 0;
    }

    public int removeHabitEvent(HabitEvent habitEvent) {
        events.remove(habitEvent);
        return 0;
    }

    public int editHabitEvent(HabitEvent oldHabitEvent, HabitEvent newHabitEvent) {
        return 0;
    }

    public int addUser(String userName) {
        return 0;
    }

    public int removeUser(User user) {
        return 0;
    }

    public User getUser() {
        return null;
    }
}
