package com.cmput301f17t07.ingroove.DataManagers.Command;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.InGroove;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static JestDroidClient client = null;

    /**
     * strings for ES
     */
    public static final String INDEX = "cmput301f17t07_ingroove";
    public static final String USER_TYPE = "user";
    public static final String HABIT_TYPE = "habit";
    public static final String HABIT_EVENT_TYPE = "habit_event";
    public static final String FOLLOW = "follow";

    private static final String HABIT_COMMAND = "add_habit_command.sav";
    private static final String HABIT_EVENT_COMMAND = "add_habit_event_command.sav";
    private static final String DEL_HABIT_COMMAND = "del_habit_command.sav";
    private static final String DEL_HABIT_EVENT_COMMAND = "del_habit_event_command.sav";
    private static final String UPD_USER_COMMAND = "update_user_command.sav";

    private ExecuteAsync executeAsync;

    /**
     * Queue of command objects
     */
    private ArrayList<ServerCommand> commands = new ArrayList<>();
    private static final ServerCommandManager instance = new ServerCommandManager();


    /**
     * Return the size of the command queue
     *
     * @return an int representing the number of commands in the queue
     */
    public int getTopIndex() {
        return commands.size();
    }

    /**
     * Constructs a new CommandManager, creates an empty queue on initialization
     */
    private ServerCommandManager() {
        loadCommands();
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
        Log.d("--- S_CMD_M ---","Adding" + command.toString() + ", now up to " + commands.size() + " cmds.");
        saveCommands();
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
            Log.d("--- S_CMD_M ---","Starting Async");
            for (ArrayList<ServerCommand> commandArray: commandArrays) {

                while (!commandArray.isEmpty()) {
                    ServerCommand command = commandArray.get(0);

                    try {
                        command.execute();
                        Log.d("--- S_CMD_M ---","Executed: " + command.toString());


                    }
                    catch (Exception e) {
                        Log.d("--- S_CMD_M ---","Unable to execute command");

                        break;
                    }
                    commandArray.remove(command);
                    Log.d("--- S_CMD_M ---","Executed and removed " + command.toString() + " " + commandArray.size() + " outstanding cmds.");
                }
            }
            Log.d("--- S_CMD_M ---","Finished Async");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("--- S_CMD_M ---","Starting Post Execute");
            ServerCommandManager.getInstance().saveCommands();
            Log.d("--- S_CMD_M ---","Finished Post Execute");
        }
    }

    /**
     * Load the commands to try and sync data with the server
     */
    private void loadCommands() {
        Log.d("--- S_CMD_M ---","---------- LOADING COMMANDS FROM DISK ---------- ");

        ArrayList<AddHabitCommand> aHabit = new ArrayList<>();
        ArrayList<AddHabitEventCommand> aHabitEvent = new ArrayList<>();
        ArrayList<DeleteHabitCommand> delHabit = new ArrayList<>();
        ArrayList<DeleteHabitEventCommand> delHabitEvent = new ArrayList<>();
        ArrayList<UpdateUserCommand> updUser = new ArrayList<>();

        try {

            Context context = InGroove.getInstance();
            Gson gson = new Gson();

            FileInputStream fis1 = context.openFileInput(HABIT_COMMAND);
            BufferedReader in1 = new BufferedReader(new InputStreamReader(fis1));
            Type listType1 = new TypeToken<ArrayList<AddHabitCommand>>(){}.getType();
            aHabit = gson.fromJson(in1, listType1);
            in1.close();
            fis1.close();

            for (AddHabitCommand cmd: aHabit) {
                Log.d("--- S_CMD_M ---","Loaded: " + cmd.toString());
            }

            FileInputStream fis2 = context.openFileInput(HABIT_EVENT_COMMAND);
            BufferedReader in2 = new BufferedReader(new InputStreamReader(fis2));
            Type listType2 = new TypeToken<ArrayList<AddHabitEventCommand>>(){}.getType();
            aHabitEvent = gson.fromJson(in2, listType2);
            in2.close();
            fis2.close();

            for (AddHabitEventCommand cmd: aHabitEvent) {
                Log.d("--- S_CMD_M ---","Loaded: " + cmd.toString());
            }


            FileInputStream fis3 = context.openFileInput(DEL_HABIT_COMMAND);
            BufferedReader in3 = new BufferedReader(new InputStreamReader(fis3));
            Type listType3 = new TypeToken<ArrayList<DeleteHabitCommand>>(){}.getType();
            delHabit = gson.fromJson(in3, listType3);
            in3.close();
            fis3.close();

            for (DeleteHabitCommand cmd: delHabit) {
                Log.d("--- S_CMD_M ---","Loaded: " + cmd.toString());
            }


            FileInputStream fis4 = context.openFileInput(DEL_HABIT_EVENT_COMMAND);
            BufferedReader in4 = new BufferedReader(new InputStreamReader(fis4));
            Type listType4 = new TypeToken<ArrayList<DeleteHabitEventCommand>>(){}.getType();
            delHabitEvent = gson.fromJson(in4, listType4);
            in4.close();
            fis4.close();

            for (DeleteHabitEventCommand cmd: delHabitEvent) {
                Log.d("--- S_CMD_M ---","Loaded: " + cmd.toString());
            }

            FileInputStream fis5 = context.openFileInput(UPD_USER_COMMAND);
            BufferedReader in5 = new BufferedReader(new InputStreamReader(fis5));
            Type listType5 = new TypeToken<ArrayList<UpdateUserCommand>>(){}.getType();
            updUser = gson.fromJson(in5, listType5);
            in5.close();
            fis5.close();

            for (UpdateUserCommand cmd: updUser) {
                Log.d("--- S_CMD_M ---","Loaded: " + cmd.toString());
            }


        } catch (FileNotFoundException e) {
            Log.d("--- S_CMD_M ---","FAILED TO LOAD COMMANDS: error: " + e);
        } catch (IOException e) {
            Log.d("--- S_CMD_M ---","IOException: error: " + e);

        }

        commands.addAll(aHabit);
        commands.addAll(aHabitEvent);
        commands.addAll(delHabit);
        commands.addAll(delHabitEvent);
        commands.addAll(updUser);

        Collections.sort(commands, new Comparator<ServerCommand>() {
            @Override
            public int compare(ServerCommand s1, ServerCommand s2) {
                if (s1.getOrderAdded() > s2.getOrderAdded()) {
                    return 1;
                } else if (s1.getOrderAdded() < s2.getOrderAdded()) {
                    return -1;
                }
                return 0;
            }
        });
        Log.d("--- S_CMD_M ---","---------- Finished  loading " + commands.size() +  " cmds from disk ----------");
    }

    /**
     * Save the commands to the local disk for synchronization when connection resumes
     */
    public void saveCommands() {

        Log.d("--- S_CMD_M ---","---------- SAVING " + commands.size() + " COMMANDS TO DISK ---------- ");

        ArrayList<AddHabitCommand> aHabit = new ArrayList<>();
        ArrayList<AddHabitEventCommand> aHabitEvent = new ArrayList<>();
        ArrayList<DeleteHabitCommand> delHabit = new ArrayList<>();
        ArrayList<DeleteHabitEventCommand> delHabitEvent = new ArrayList<>();
        ArrayList<UpdateUserCommand> updUser = new ArrayList<>();

        for (ServerCommand command: commands) {
            if (command instanceof AddHabitCommand) {
                aHabit.add((AddHabitCommand) command);
            } else if (command instanceof AddHabitEventCommand) {
                aHabitEvent.add((AddHabitEventCommand) command);
            } else if (command instanceof DeleteHabitCommand) {
                delHabit.add((DeleteHabitCommand) command);
            } else if (command instanceof DeleteHabitEventCommand) {
                delHabitEvent.add((DeleteHabitEventCommand) command);
            } else if (command instanceof UpdateUserCommand ) {
                updUser.add((UpdateUserCommand) command);
            }
        }

        try {

            Context context = InGroove.getInstance();
            Gson gson = new Gson();

            Log.d("--- S_CMD_M ---","---------- SAVING " + aHabit.size() + " ADD__COMMANDS TO DISK ---------- ");

            FileOutputStream fos1 = context.openFileOutput(HABIT_COMMAND, Context.MODE_PRIVATE);
            BufferedWriter out1 = new BufferedWriter(new OutputStreamWriter(fos1));
            gson.toJson(aHabit, out1);
            out1.flush();
            out1.close();
            fos1.close();


            FileOutputStream fos2 = context.openFileOutput(HABIT_EVENT_COMMAND, Context.MODE_PRIVATE);
            BufferedWriter out2 = new BufferedWriter(new OutputStreamWriter(fos2));
            gson.toJson(aHabitEvent, out2);
            out2.flush();
            out2.close();
            fos2.close();


            FileOutputStream fos3 = context.openFileOutput(DEL_HABIT_COMMAND, Context.MODE_PRIVATE);
            BufferedWriter out3 = new BufferedWriter(new OutputStreamWriter(fos3));
            gson.toJson(delHabit, out3);
            out3.flush();
            out3.close();
            fos3.close();


            FileOutputStream fos4 = context.openFileOutput(DEL_HABIT_EVENT_COMMAND, Context.MODE_PRIVATE);
            BufferedWriter out4 = new BufferedWriter(new OutputStreamWriter(fos4));
            gson.toJson(delHabitEvent, out4);
            out4.flush();
            out4.close();
            fos4.close();


            FileOutputStream fos5 = context.openFileOutput(UPD_USER_COMMAND, Context.MODE_PRIVATE);
            BufferedWriter out5 = new BufferedWriter(new OutputStreamWriter(fos5));
            gson.toJson(updUser, out5);
            out5.flush();
            out5.close();
            fos5.close();

        } catch (Exception e) {
            Log.d("--- S_CMD_M ---","FAILED TO SAVE CMDS. error: " + e);
        }

        for (ServerCommand cmd: commands) {
            Log.d("--- S_CMD_M ---","Saved:" + cmd.toString());
        }
        Log.d("--- S_CMD_M ---","---------- Finished  saving " + commands.size() +  " cmds to disk ----------");



    }
}


















