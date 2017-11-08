package com.cmput301f17t07.ingroove.DataManagerTests;

import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.HabitManager;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.User;

import org.junit.Test;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by fraserbulbuc on 2017-11-07.
 */

public class AddHabitCommandTest {

    private DataManager model = DataManager.getInstance();

    @Test
    public void addHabitToServerTest() {
        User user = new User("Test","test@test.com", 123);
        Habit habit = new Habit("Test Habit", "Test Comment");

        model.addHabit(habit);

        ServerCommandManager.getInstance().execute();

        assertTrue(true);
    }
}
