package com.cmput301f17t07.ingroove;

import com.cmput301f17t07.ingroove.Model.Habit;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by fraserbulbuc on 2017-10-17.
 */

/**
 *
 * class responsible for running a series of tests on the Habit entity class
 *
 * @author fraserbulbuc
 *
 */
public class HabitTest {


    /**
     * tests the addRepeatedDay method of Habit class
     */
    @Test
    public void addRepeatedDayTest() {

        Habit habit = new Habit("Test Habit", "This is a test habit.");

        Day testDay = Day.TUESDAY;

        habit.addRepatedDay(testDay);

        assertTrue(habit.hasRepeatedDay(testDay));

    }


}