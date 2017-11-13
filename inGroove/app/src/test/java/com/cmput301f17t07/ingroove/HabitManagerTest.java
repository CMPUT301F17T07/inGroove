package com.cmput301f17t07.ingroove;

import com.cmput301f17t07.ingroove.DataManagers.HabitManager;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.User;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Class responsible for running a series of tests on the HabitManager control class
 *
 * @see HabitManager
 *
 * Created by fraserbulbuc on 2017-10-22.
 *
 */
public class HabitManagerTest {

    /**
     * Tests add habit method of HabitManager class
     */
    @Test
    public void addHabitTest() {

        HabitManager testManager = HabitManager.getInstance();

        Habit testHabit = new Habit("Drink water", "Cups of water to drink daily");
        User testUser = new User("tester");

        testManager.addHabit(testUser, testHabit);

    }

    /**
     * Tests remove method of HabitManager class
     * @see HabitManager
     */
    @Test
    public void removeHabitTest() {

        HabitManager testManager = HabitManager.getInstance();

        Habit testHabit = new Habit("Drink water", "Cups of water to drink daily");
        User testUser = new User("tester");
        testManager.addHabit(testUser, testHabit);
        
    }

}
