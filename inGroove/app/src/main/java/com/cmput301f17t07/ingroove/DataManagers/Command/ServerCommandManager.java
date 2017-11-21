package com.cmput301f17t07.ingroove.DataManagers.Command;

import android.os.AsyncTask;
import android.util.Log;

import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.User;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * A singleton class to manage all the command objects and execute them when a network connection can
 * be established
 *
 * Created by Christopher Walter on 2017-11-03.
 */

public class ServerCommandManager {

    /**
     * Singleton instance
     */
    private static final ServerCommandManager instance = new ServerCommandManager();
    private static JestDroidClient client = null;

    /**
     * Queue of command objects
     */
    private ArrayList<ServerCommand> commands;

    /**
     * Constructs a new CommandManager, creates an empty queue on initialization
     */
    private ServerCommandManager() {
        commands = new ArrayList<>();
    }

    /**
     * Method to access the singleton instance of the command manager application wide
     *
     * @return the singleton instance of the command manager
     */
    public static ServerCommandManager getInstance() {
        return instance;
    }

    /**
     * Adds a command to the queue
     *
     * @param command the command object to be added
     */
    public void addCommand(ServerCommand command){
        commands.add(command);
    }

    /**
     * Access to the jest client for Elastic Search execution
     *
     * @return an instance of the JestDroidClient to interface with Elastic Search
     */
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

    /**
     * Creates an Async task and passes the queue of command to be executed
     */
    public void execute() {

        ExecuteAsync executeAsync = new ExecuteAsync();

        executeAsync.execute(commands);

    }

    /**
     * Specific Async task for adding a new user
     */
    public static class AddUserAsync extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {

            for (User user: users) {
                DataManager.getInstance().addUserToServer(user);
            }
            return null;
        }
    }

    /**
     * Static inner class to execute commands on a background thread
     *
     * Loops through all commands in the queue and calls their execute method, upon execution
     * removes them from the queue
     */
    public static class ExecuteAsync extends AsyncTask<ArrayList<ServerCommand>, Void, Void> {
        @Override
        protected Void doInBackground(ArrayList<ServerCommand>... commandArrays) {

            for (ArrayList<ServerCommand> commandArray: commandArrays) {

                while (!commandArray.isEmpty()) {
                    ServerCommand command = commandArray.get(0);

                    try {
                        command.execute();
                    }
                    catch (Exception e) {
                        break;
                    }

                    commandArray.remove(command);

                }
            }

            return null;
        }
    }

}


















