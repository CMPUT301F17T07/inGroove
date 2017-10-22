package com.cmput301f17t07.ingroove;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by fraserbulbuc on 2017-10-22.
 */

public class HabitManagerTest {

    @Test
    public void addHabitTest() {

        HabitManager testManager = new HabitManager();

        Habit testHabit = new Habit("Drink water", "Cups of water to drink daily");
        User testUser = new User("Test User", "test@test.com");
        testManager.addHabit(testUser, testHabit);

        assertTrue(HabitManager.retrieveHabits(testUser).contains(testHabit));
    }

    @Test
    public void removeHabitTest() {

        HabitManager testManager = new HabitManager();

        Habit testHabit = new Habit("Drink water", "Cups of water to drink daily");
        User testUser = new User("Test User", "test@test.com");
        testManager.addHabit(testUser, testHabit);

        assertTrue(HabitManager.retrieveHabits(testUser).contains(testHabit));

        testManager.removeHabit(testUser, testHabit);

        assertTrue(!HabitManager.retrieveHabits(testUser).contains(testHabit));
    }

}
