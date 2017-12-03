package com.cmput301f17t07.ingroove.DataManagers.Command;

import android.util.Log;
import com.cmput301f17t07.ingroove.DataManagers.HabitManager;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.User;

/**
 * [Command Class]
 * AddHabit command subclasses ServerCommmand and represents a command object containing all
 * the data for a new habit to be added for a particular user. It also contains a reference to
 * its receiver which does the work of adding the habit when execute() is called.
 *
 * @see ServerCommand
 * @see ServerCommandManager
 *
 * Created by Christopher Walter on 2017-11-03.
 */

public class AddHabitCommand extends ServerCommand {

    private User user;
    private Habit habit;
    private Boolean isReversible =  false;
    private int orderAdded;

    /**
     *
     * Constructs a new instance of the AddHabitCommand, needs user data and habit data
     *
     * @param user the user creating the habit
     * @param habit the habit being created
     * @see Habit
     * @see User
     */
    public AddHabitCommand(User user, Habit habit) {
        this.user = user;
        this.habit = habit;
        this.orderAdded = ServerCommandManager.getInstance().getTopIndex();
    }

    /**
     * @return its position on the command queue
     */
    public int getOrderAdded() {
        return this.orderAdded;
    }

    /**
     *
     * Called by the command manager when there is a connection present, the HabitManager
     * adds the habit to server storage
     *
     * @throws Exception if the habit cannot be added to the server
     * @see Habit
     */
    @Override
    public void execute() throws Exception {
        Log.d("-- ADD H CMD --","Executing AHC with habit named: " + habit.getName());
        HabitManager.getInstance().addHabitToServer(habit);
    }

    /**
     *
     * General method which much be implemented for all command objects, looks at the
     * isIrreversible property and executes the code to undo the command if true
     *
     * Add habit commands are not reversible so there is no implementation
     *
     */
    @Override
    public void unexecute() {
        // Adding habits is not un-executable in this release
    }

    /**
     *
     * Method for caller to check if the command object instance is reversible or not
     *
     * @return false for the add habit command object
     */
    @Override
    public Boolean isUndoable() {
        return isReversible;
    }

    /**
     * Description of the command
     *
     * @return string describing the command
     */
    public String toString() {
        return "ADD HC with habit named: " + habit.getName();
    }
}
