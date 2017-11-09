package com.cmput301f17t07.ingroove.DataManagers.Command;

/**
 * Created by Christopher Walter on 2017-11-03.
 */

/**
 * Command interface for all queueable commands
 */
public interface ServerCommand {

    public void execute() throws Exception;
    public void unexecute();
    public Boolean isUndoable();

}
