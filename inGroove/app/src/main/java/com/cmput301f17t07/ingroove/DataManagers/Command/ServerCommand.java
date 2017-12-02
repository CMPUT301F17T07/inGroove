package com.cmput301f17t07.ingroove.DataManagers.Command;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Command interface for all queue-able commands
 *
 * @see ServerCommandManager
 * @see AddHabitCommand
 * @see AddHabitEventCommand
 *
 * Created by Christopher Walter on 2017-11-03.
 */
public abstract class ServerCommand {

    /**
     * Top of the command queue
     */
    public abstract int getOrderAdded();

    /**
     * Called when the command is at the top of the queue
     *
     * @throws Exception if execution fails
     */
    public abstract void execute() throws Exception;

    /**
     * Called to undo a command if allowed.
     */
    public abstract void unexecute();

    /**
     * Checks whether a command is undo-able or not
     *
     * @return true if the command is undo-able, false if not
     */
    public abstract Boolean isUndoable();

    /**
     * String describing the command
     *
     * @return description
     */
    public abstract String toString();

}
