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

    private AsyncResultHandler handler;
    private int count = 0;
    private int returns = 0;
    private ArrayList<User> followers = new ArrayList<>();

    public GetFollowRequestTask(AsyncResultHandler handler) {
        this.handler = handler;
    }

    @Override
    protected ArrayList<Follow> doInBackground(String... userIDs) {

        JestDroidClient client = ServerCommandManager.getClient();

        ArrayList<Follow> follows = new ArrayList<>();

        String query = "{\n" +
                "    \"query\" : {\n" +
                "        \"term\" : { \"followee\" : \"" + userIDs[0] + "\" }\n" +
                "    }\n" +
                "}";

        Search search = new Search.Builder(query).addIndex("ingroove").addType("follow").build();

        try {
            SearchResult result = client.execute(search);
            if (result.isSucceeded()) {
                List<Follow> found = result.getSourceAsObjectList(Follow.class);
                follows.addAll(found);
            }
        } catch (Exception e) {
            Log.i("--- FOLLOW REQ ---","Could not retrieve follow requests.");
        }

        return follows;
    }

    @Override
    protected void onPostExecute(ArrayList<Follow> queryResult) {
        super.onPostExecute(queryResult);

        if (queryResult != null) {
            this.count = queryResult.size();
            this.returns = 0;
            Log.d("-- SET COUNT ---", "Should iterate, " + this.count + " times.");
            for (Follow follow : queryResult) {
                GenericGetRequest<User> get = new GenericGetRequest<>(this, User.class, "user","userID");
                String query = follow.getFollower();
                get.execute(query);
            }
        }

    }

    @Override
    public void handleResult(ArrayList<User> result) {

        if ( returns < count) {
            followers.addAll(result);
            returns = returns + 1;
        }

        if (returns == count) {
            followers.addAll(result);
            handler.handleResult(followers);
        }
    }
}
