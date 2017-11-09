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

    public AddHabitCommand(User user, Habit habit) {
        this.user = user;
        this.habit = habit
    }

    @Override
    public void execute() {
        HabitManager.getInstance().addHabitToServer(habit, user);
    }

    @Override
    public void unexecute() {
        // TODO: implement un-execute
    }

    @Override
    public Boolean isUndoable() {
        return false;
    }
}
