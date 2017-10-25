package com.cmput301f17t07.ingroove;

import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.User;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by fraserbulbuc on 2017-10-22.
 */

/**
 *
 * Class responsible for running a series of tests on the HabitManager control class
 *
 * @author fraserbulbuc
 *
 */
public class HabitManagerTest {

    /**
     * Tests add habit method of HabitManager class
     */
    @Test
    public void addHabitTest() {

        HabitManager testManager = new HabitManager();

        Habit testHabit = new Habit("Drink water", "Cups of water to drink daily");
        User testUser = new User("Test User", "test@test.com", 0);
        testManager.addHabit(testUser, testHabit);

        assertTrue(HabitManager.hasHabit(testUser, testHabit));
    }

    /**
     * Tests remove method of HabitManager class
     * @see HabitManager
     */
    @Test
    public void removeHabitTest() {

        HabitManager testManager = new HabitManager();

        Habit testHabit = new Habit("Drink water", "Cups of water to drink daily");
        User testUser = new User("Test User", "test@test.com", 0);
        testManager.addHabit(testUser, testHabit);

        assertTrue(HabitManager.hasHabit(testUser, testHabit));

        testManager.removeHabit(testUser, testHabit);

        assertTrue(!HabitManager.hasHabit(testUser, testHabit));
    }

}
