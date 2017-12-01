package com.cmput301f17t07.ingroove.DataManagers.QueryTasks;

import android.os.AsyncTask;
import android.util.Log;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.GenericGetRequest;
import com.cmput301f17t07.ingroove.Model.Follow;
import com.cmput301f17t07.ingroove.Model.User;
import com.searchly.jestdroid.JestDroidClient;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Parent task for getting users who currently, or are requesting to follow the current user
 *
 * Created by Fraser Bulbuc on 2017-11-26.
 */

public class GetFollowsTask extends AsyncTask<String, Void, ArrayList<Follow>> implements AsyncResultHandler<User> {

    private AsyncResultHandler handler;
    private int count = 0;
    private int returns = 0;
    private ArrayList<User> followers = new ArrayList<>();
    private String isAccepted;
    private String attribute;
    private boolean isFollower;

    /**
     * Ctor
     *
     * @param handler the result handler
     * @param isAccepted if true, will return the current user's followers, else it returns follow
     *                   requests for that user
     */
    public GetFollowsTask(AsyncResultHandler handler, Boolean isAccepted, Boolean isFollower) {
        this.handler = handler;
        this.isAccepted = String.valueOf(isAccepted);
        if (isFollower) {
            this.attribute = "follower";
        } else {
            this.attribute = "followee";

        }

        this.isFollower = isFollower;
    }

    @Override
    protected ArrayList<Follow> doInBackground(String... userIDs) {

        JestDroidClient client = ServerCommandManager.getClient();

        ArrayList<Follow> follows = new ArrayList<>();

        try {

//            String query = "{\n" +
//                "    \"query\" : {\n" +
//                "        \"term\" : { \"followee\" : \"" + userIDs[0] + "\" }\n" +
//                "    }\n" +
//                "}";

            String query = "{\n" +
                    "   \"query\": {\n" +
                    "       \"bool\": {\n" +
                    "           \"must\": [\n" +
                    "               {\"match\": { \"" + this.attribute + "\": \"" + userIDs[0] + "\" }},\n" +
                    "               {\"match\": { \"accepted\":" + this.isAccepted + "}}\n" +
                    "           ]\n" +
                    "       }\n" +
                    "   }\n" +
                    "}";


            Search search = new Search.Builder(query).addIndex(ServerCommandManager.INDEX).addType(ServerCommandManager.FOLLOW).build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Follow> found = result.getSourceAsObjectList(Follow.class);
                    follows.addAll(found);
                }
            } catch (Exception e) {
                Log.i("--- FOLLOW REQ ---","Could not retrieve follow requests.");
            }


        }
        catch (Exception e) {
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
                GenericGetRequest<User> get = new GenericGetRequest<>(this, User.class, ServerCommandManager.USER_TYPE,"userID");
                String query;
                if (this.isFollower){
                    query = follow.getFollowee();
                } else {
                    query = follow.getFollower();
                }
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
