package com.cmput301f17t07.ingroove.DataManagers.Command;

import com.cmput301f17t07.ingroove.DataManagers.HabitEventManager;
import com.cmput301f17t07.ingroove.Model.HabitEvent;

/**
 * Created by Christopher Walter on 2017-11-09.
 */

public class AddHabitEventCommand implements ServerCommand {

    private HabitEvent habitEvent;

    public AddHabitEventCommand(HabitEvent habitEvent) {
        this.habitEvent = habitEvent;
    }


    @Override
    public void execute() throws Exception {
        HabitEventManager.getInstance().addHabitEventToServer(habitEvent);
    }

    @Override
    public void unexecute() {

    }

    @Override
    public Boolean isUndoable() {
        return false;
    }
}
