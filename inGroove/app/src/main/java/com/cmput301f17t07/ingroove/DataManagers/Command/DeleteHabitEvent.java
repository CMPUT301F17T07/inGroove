package com.cmput301f17t07.ingroove.DataManagers.Command;

import com.cmput301f17t07.ingroove.DataManagers.HabitEventManager;
import com.cmput301f17t07.ingroove.Model.HabitEvent;

/**
 * Created by Christopher Walter on 2017-11-22.
 */

public class DeleteHabitEvent implements ServerCommand {

    private HabitEvent habitEvent;


    public DeleteHabitEvent(HabitEvent habitEvent) {
        this.habitEvent = habitEvent;
    }


    /**
     * Called when the command is at the top of the queue
     *
     * @throws Exception if execution fails
     */
    @Override
    public void execute() throws Exception {
        HabitEventManager.getInstance().deleteHabitEventFromServer(habitEvent);
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
