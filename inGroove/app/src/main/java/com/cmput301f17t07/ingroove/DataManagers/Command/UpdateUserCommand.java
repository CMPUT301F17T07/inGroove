package com.cmput301f17t07.ingroove.DataManagers.Command;

import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.User;

/**
 * Created by Christopher Walter on 2017-11-27.
 */

public class UpdateUserCommand extends ServerCommand {

    private User user;
    private int orderAdded;

    /**
     * default ctor
     *
     * @param user the user to be added to the server
     */
    public UpdateUserCommand(User user) {
        this.user = user;
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
        DataManager.getInstance().addUserToServer(user);
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
        return null;
    }

    /**
     * String describing the command
     *
     * @return description
     */
    @Override
    public String toString() {
        return " UPD UC with user named: " + user.getName();
    }
}
