package com.cmput301f17t07.ingroove.DataManagers.Command;

import com.cmput301f17t07.ingroove.DataManagers.HabitEventManager;
import com.cmput301f17t07.ingroove.Model.HabitEvent;

/**
 * AddHabitEvent command subclasses ServerCommmand and represents a command object containing all
 * the data for a new habitEvent to be added for a particular user. It also contains a reference to
 * its receiver which does the work of adding the habitEvent when execute() is called.
 *
 * @see ServerCommandManager
 * @see ServerCommand
 *
 * Created by Christopher Walter on 2017-11-09.
 */

public class AddHabitEventCommand implements ServerCommand {

    private HabitEvent habitEvent;
    private Boolean isReversible = false;

    /**
     *
     * Constructs a new instance of the AddHabitCommand, needs user data and habit data
     *
     * @param habitEvent the habitEvent being created
     * @see HabitEvent
     */
    public AddHabitEventCommand(HabitEvent habitEvent) {
        this.habitEvent = habitEvent;
    }

    /**
     *
     * Called by the command manager when there is a connection present, the HabitEventManager
     * adds the habit to server storage
     *
     * @throws Exception if the habit cannot be added to the server
     * @see HabitEvent
     */
    @Override
    public void execute() throws Exception {
        HabitEventManager.getInstance().addHabitEventToServer(habitEvent);
    }

    /**
     *
     * General method which much be implemented for all command objects, looks at the
     * isIrreversible property and executes the code to undo the command if true
     *
     * Add habitEvent commands are not reversible so there is no implementation
     *
     */
    @Override
    public void unexecute() {
        // Adding habit events is not un-executable in this release
    }

    /**
     *
     * Method for caller to check if the command object instance is reversible or not
     *
     * @return false for the add habitEvent command object
     */
    @Override
    public Boolean isUndoable() {
        return isReversible;
    }
}
