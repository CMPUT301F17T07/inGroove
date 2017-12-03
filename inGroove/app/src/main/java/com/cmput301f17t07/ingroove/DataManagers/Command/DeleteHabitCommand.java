package com.cmput301f17t07.ingroove.DataManagers.Command;

import com.cmput301f17t07.ingroove.DataManagers.HabitManager;
import com.cmput301f17t07.ingroove.Model.Habit;

/**
 * Created by Christopher Walter on 2017-11-23.
 */

public class DeleteHabitCommand extends ServerCommand {

    private Habit habit;
    private int orderAdded;

    /**
     * Ctor
     *
     * @param habit the habit to be added
     */
    public DeleteHabitCommand(Habit habit) {
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
     * Called when the command is at the top of the queue
     *
     * @throws Exception if execution fails
     */
    @Override
    public void execute() throws Exception {
        HabitManager.getInstance().deleteHabitFromServer(habit);
    }

    /**
     * Called to undo a command if allowed.
     */
    @Override
    public void unexecute() {

    }

    /**
     * Checks whether a command is undo-able or not
     *
     * @return true if the command is undo-able, false if not
     */
    @Override
    public Boolean isUndoable() {
        return false;
    }

    /**
     * String describing the command
     *
     * @return description
     */
    @Override
    public String toString() {
        return " DEL HC with habit named: " + habit.getName();
    }
}
