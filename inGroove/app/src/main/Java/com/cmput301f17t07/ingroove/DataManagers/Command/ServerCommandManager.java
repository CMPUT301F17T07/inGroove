package com.cmput301f17t07.ingroove.DataManagers.Command;

import java.util.ArrayList;

/**
 * Created by Christopher Walter on 2017-11-03.
 */

public class ServerCommandManager {

    private static final ServerCommandManager instance = new ServerCommandManager();

    private ArrayList<ServerCommand> commands;


    private ServerCommandManager() {
        commands = new ArrayList<>();
    }

    public ServerCommandManager getInstance() {
        return instance;
    }

    public void addCommand(ServerCommand command){
        commands.add(command);
    }

    public void execute() {
        while (!commands.isEmpty()) {
            ServerCommand command = commands.get(commands.size() - 1);
            command.execute();
            // TODO: deal with unexecutable command
            commands.remove(command);

        }
    }
}
