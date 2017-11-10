package com.cmput301f17t07.ingroove.DataManagers.Command;

import android.os.AsyncTask;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;


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

        ExecuteAsync executeAsync = new ExecuteAsync();

        executeAsync.execute(commands);

    }






    public static class ExecuteAsync extends AsyncTask<ArrayList<ServerCommand>, Void, Void> {
        @Override
        protected Void doInBackground(ArrayList<ServerCommand>... commandArrays) {

            for (ArrayList<ServerCommand> commandArray: commandArrays) {

                while (!commandArray.isEmpty()) {
                    ServerCommand command = commandArray.get(0);

                    try {
                        command.execute();
                    } catch (Exception e) {
                        break;
                    }

                    commandArray.remove(command);

                }



            }


            return null;
        }
    }


}


















