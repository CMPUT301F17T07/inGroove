package com.cmput301f17t07.ingroove.DataManagers.Command;

import com.cmput301f17t07.ingroove.DataManagers.HabitEventManager;
import com.cmput301f17t07.ingroove.Model.HabitEvent;

/**
 * [Command Class]
 * Command to delete a habit event from the server
 *
 * Created by Christopher Walter on 2017-11-22.
 */

public class DeleteHabitEventCommand extends ServerCommand {

    private HabitEvent habitEvent;
    private int orderAdded;


    public DeleteHabitEventCommand(HabitEvent habitEvent) {
        this.habitEvent = habitEvent;
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

    /**
     * String describing the command
     *
     * @return description
     */
    @Override
    public String toString() {
        return " DEL HEC with habitEvent named: " + habitEvent.getName();
    }
}
