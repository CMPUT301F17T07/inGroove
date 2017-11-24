package com.cmput301f17t07.ingroove.DataManagers.Command;

import com.cmput301f17t07.ingroove.DataManagers.HabitManager;
import com.cmput301f17t07.ingroove.Model.Habit;

/**
 * Created by Christopher Walter on 2017-11-23.
 */

public class DeleteHabitCommand implements ServerCommand {

    private Habit habit;

    public DeleteHabitCommand(Habit habit) {
        this.habit = habit;
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
}
