package com.cmput301f17t07.ingroove.DataManagers.Command;

import android.os.AsyncTask;
import android.util.Log;

import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
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
     * strings for ES
     */
    public static final String INDEX = "cmput301f17t07_ingroove";
    public static final String USER_TYPE = "user";
    public static final String HABIT_TYPE = "habit";
    public static final String HABIT_EVENT_TYPE = "habit_event";
    public static final String FOLLOW = "follow";



    /**
     * Queue of command objects
     */
    private ArrayList<ServerCommand> commands;

    private ExecuteAsync executeAsync = null;

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


        if (executeAsync == null || (executeAsync.getStatus() != AsyncTask.Status.RUNNING  && executeAsync.getStatus() != AsyncTask.Status.PENDING)) {
            executeAsync = new ExecuteAsync();
            executeAsync.execute(commands);
        }
    }

    /**
     * Specific Async task for adding a new user
     */
    public static class InitializeUserAsync extends AsyncTask<User, Void, Void> {

        private AsyncResultHandler<User> resultHandler;

        public InitializeUserAsync(AsyncResultHandler<User> resultHandler) {
            this.resultHandler = resultHandler;
        }

        @Override
        protected Void doInBackground(User... users) {

            for (User user: users) {
                try {
                    DataManager.getInstance().addUserToServer(user);

                    ServerCommand updateUserCommand = new UpdateUserCommand(DataManager.getInstance().getUser());
                    ServerCommandManager.getInstance().addCommand(updateUserCommand);


                    ArrayList<User> list = new ArrayList<>();
                    list.add(DataManager.getInstance().getUser());
                    resultHandler.handleResult(list);
                } catch (Exception e){
                    Log.d("---- USER ----"," Failed to add user with name " + user.getName() + " to server. Caught " + e);
                    resultHandler.handleResult(null);
                }

                break; // this should only ever be passed one user
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //TODO: update this to the job scheduler
            ServerCommandManager.getInstance().execute();
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
            Log.d("-ServerCommandManager-","Starting Asnyc");
            for (ArrayList<ServerCommand> commandArray: commandArrays) {

                while (!commandArray.isEmpty()) {
                    ServerCommand command = commandArray.get(0);

                    try {
                        command.execute();
                    }
                    catch (Exception e) {
                        Log.d("-ServerCommandManager-"," cant execute command");

                        break;
                    }

                    commandArray.remove(command);

                }
            }
            Log.d("-ServerCommandManager-","Finished Asnyc");


            return null;
        }
    }

}


















