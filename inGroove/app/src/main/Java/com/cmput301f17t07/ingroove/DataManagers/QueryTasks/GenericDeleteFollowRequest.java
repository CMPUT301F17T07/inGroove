package com.cmput301f17t07.ingroove.DataManagers.QueryTasks;

import android.os.AsyncTask;
import android.util.Log;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import com.searchly.jestdroid.JestDroidClient;
import java.io.IOException;
import java.util.ArrayList;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;

/**
 * [Model Class]
 * Generic Delete request for follow objects from ElasticSearch
 *
 * Created by fraserbulbuc on 2017-11-28.
 */
public class GenericDeleteFollowRequest extends AsyncTask<String, Void, ArrayList<Boolean>> {

    private AsyncResultHandler<Boolean> resultHandler;
    private String followerID;

    /**
     * Default Ctor
     *  @param resultHandler the result to update upon completion of the request
     *
     */
    public GenericDeleteFollowRequest(String followerID, AsyncResultHandler<Boolean> resultHandler) {
        this.resultHandler = resultHandler;
        this.followerID = followerID;
    }

    @Override
    protected ArrayList<Boolean> doInBackground(String... followeeIDs) {
        Log.d("--- GEN DEL FOL ---", "Trying to delete follow obj.");
        ArrayList<Boolean> results = new ArrayList<>();

        JestDroidClient client = ServerCommandManager.getClient();

        for (String followeeID : followeeIDs) {

            Delete delete = new Delete.Builder(followerID + followeeID).index(ServerCommandManager.INDEX).type(ServerCommandManager.FOLLOW).build();

            try {
                DocumentResult result = client.execute(delete);
                if (result.isSucceeded()) {
                    results.add(true);
                    Log.d("--- GEN DEL FOL ---", "Succeeded in deleting follow Request for ID: " + followerID + followeeID);
                } else {
                    results.add(false);
                    Log.d("--- GEN DEL FOL ---", "Failed in deleting follow Request for ID: " + followerID + followeeID);
                }
            } catch (IOException e) {
                results.add(false);
                Log.d("--- GEN DEL FOL ---", "Error in deleting follow request with ID: " + followerID + followeeID + " error = " + e);
            }
        }
        return results;
    }

    @Override
    protected void onPostExecute(ArrayList<Boolean> booleans) {
        super.onPostExecute(booleans);
//        resultHandler.handleResult(booleans);
    }
}
