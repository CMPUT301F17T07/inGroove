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

        while (!commands.isEmpty()) {
            ServerCommand command = commands.get(0);

            command.execute();
            // TODO: deal with un-executable command
            commands.remove(command);
        }
    }






    public static class ExecuteAsync extends AsyncTask<Index, Void, Void> {
        @Override
        protected Void doInBackground(Index... indices) {

            for (Index index: indices) {

                try {
                    DocumentResult result = client.execute(index);


                } catch (IOException e){

                }
            }


            return null;
        }
    }


}


















