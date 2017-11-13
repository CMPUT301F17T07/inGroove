package com.cmput301f17t07.ingroove;

import com.cmput301f17t07.ingroove.Model.Day;
import com.cmput301f17t07.ingroove.Model.Habit;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * class responsible for running a series of tests on the Habit entity class
 *
 * Created by fraserbulbuc on 2017-10-17.
 */
public class HabitTest {


    /**
     * Tests the addRepeatedDay method of Habit class
     */
    @Test
    public void addRepeatedDayTest() {

        Habit habit = new Habit("Test Habit", "This is a test habit.");

        Day testDay = Day.TUESDAY;

        habit.addRepatedDay(testDay);

        assertTrue(habit.hasRepeatedDay(testDay));

    }

    /**
     * Test the comparison of habits
     *
     * @see Habit
     */
    @Test public void equalsTest() {

        Habit testHabit1 = new Habit("test habit 1","this is a test habit.");
        Habit testHabit2 = new Habit("test habit 1", "this is another test habit.");
        Habit testHabit3 = testHabit1;

        assertFalse(testHabit1.equals(testHabit2));
        assertTrue(testHabit1.equals(testHabit3));

        testHabit1.addRepatedDay(Day.TUESDAY);

        assertTrue(testHabit1.equals(testHabit3));

        testHabit1.setName("changed the test name");

        assertTrue(testHabit1.equals(testHabit3));

    }


}