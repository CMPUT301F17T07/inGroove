package com.cmput301f17t07.ingroove.DataManagers.Command;

import com.cmput301f17t07.ingroove.DataManagers.HabitManager;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.User;

/**
 * Created by Christopher Walter on 2017-11-03.
 */

public class AddHabitCommand implements ServerCommand {

    private User user;
    private Habit habit;

    private HabitManager habitManager;

    public AddHabitCommand(User user, Habit habit, HabitManager habitManager) {
        this.user = user;
        this.habit = habit;
        this.habitManager = habitManager;
    }

    @Override
    public void execute() {
        // TODO: run HabitManager.addHabitToServer
    }

    @Override
    public void unexecute() {
        // TODO: implement unexecute
    }

    @Override
    public Boolean isUndoable() {
        return false;
    }
}
