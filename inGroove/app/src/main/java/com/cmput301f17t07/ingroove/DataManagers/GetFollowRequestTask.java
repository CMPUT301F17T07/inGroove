package com.cmput301f17t07.ingroove.DataManagers;

import android.os.AsyncTask;
import android.util.Log;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.GenericGetRequest;
import com.cmput301f17t07.ingroove.Model.Follow;
import com.cmput301f17t07.ingroove.Model.User;
import com.searchly.jestdroid.JestDroidClient;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by Fraser Bulbuc on 2017-11-26.
 */

public class GetFollowRequestTask extends AsyncTask<String, Void, ArrayList<Follow>> implements AsyncResultHandler<User> {

    @Override
    protected ArrayList<Follow> doInBackground(String... userIDs) {

        JestDroidClient client = ServerCommandManager.getClient();

        ArrayList<Follow> follows = new ArrayList<>();

        Log.d("-- GET FOL REQ --", " Searching ID of: " + userIDs[0]);

        String query = "{\n" +
                "    \"query\" : {\n" +
                "        \"term\" : { \"followee\" : \"" + userIDs[0] + "\" }\n" +
                "    }\n" +
                "}";

        Search search = new Search.Builder(query).addIndex("cmput301f17t07_ingroove").addType("follow").build();

        try {
            SearchResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<Follow> found = result.getSourceAsObjectList(Follow.class);
                follows.addAll(found);
                Log.i(" ---- IN BGND ----", "Query results of size: " + found.size());
            }
        } catch (Exception e) {
            Log.i("--- FOLLOW REQ ---","Could not retrieve follow requests.");
        }

        return follows;
    }

    @Override
    protected void onPostExecute(ArrayList<Follow> queryResult) {
        super.onPostExecute(queryResult);

        GenericGetRequest<User> get = new GenericGetRequest<>(this, User.class, "user","userID");

        if (queryResult != null) {
            for (Follow follow : queryResult) {
                String query = follow.getFollowee();
                Log.d("--- SUB TASK ---", "Would retrieve user with id:" + query);
                //get.execute(query);
            }
        }

    }

    @Override
    public void handleResult(ArrayList<User> result) {
        for (User user: result) {
            Log.d("--- Followers ---","Found follower with name: " + user.getName());
        }
    }
}
