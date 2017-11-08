package com.cmput301f17t07.ingroove.DataManagers.Command;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;


/**
 * Created by Christopher Walter on 2017-11-03.
 */

public class ServerCommandManager {

    private static final ServerCommandManager instance = new ServerCommandManager();
    private static JestDroidClient client = null;

    private ArrayList<ServerCommand> commands;

    private ServerCommandManager() {
        commands = new ArrayList<>();
    }

    public static ServerCommandManager getInstance() {
        return instance;
    }

    public void addCommand(ServerCommand command){
        commands.add(command);
    }

    public static JestDroidClient getClient() {

        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.
                    Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }

        return client;

    }

    public void execute() {

        for (ServerCommand command: commands) {
            command.execute();
            // TODO: deal with un-executable command
        }
    }

    public void removeCommant(ServerCommand command) {
        commands.remove(command);
    }


}
