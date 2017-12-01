package com.cmput301f17t07.ingroove.DataManagers.QueryTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommand;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import com.cmput301f17t07.ingroove.Model.Follow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by Fraser Bulbuc on 2017-11-30.
 */

public class AcceptFollowRequestTask extends AsyncTask<String, Void, ArrayList<Follow>> {

    private String type;
    private String currentUserID;
    private String followID;

    /**
     * Default Ctor
     *
     * @param type the name of the index to query, for example: "habit" or "habit_event"
     */
    public AcceptFollowRequestTask(String type, String currentUserID) {
        this.type = type;
        this.currentUserID = currentUserID;
    }


    @Override
    protected ArrayList<Follow> doInBackground(String... ids) {

        ArrayList<Follow> follow = new ArrayList<>();

        String query = "{\n" +
                "   \"query\": {\n" +
                "       \"bool\": {\n" +
                "           \"must\": [\n" +
                "               {\"match\": { \"follower\": \"" + ids[0] + "\" }},\n" +
                "               {\"match\": { \"followee\": \"" + currentUserID + "\" }},\n" +
                "               {\"match\": { \"accepted\": false }}\n" +
                "           ]\n" +
                "       }\n" +
                "   }\n" +
                "}";

        Search search = new Search.Builder(query).addIndex(ServerCommandManager.INDEX).addType(type).build();

        try {
            SearchResult res = ServerCommandManager.getClient().execute(search);

            if (res.isSucceeded()) {

                List<Follow> returned = res.getSourceAsObjectList(Follow.class);
                follow.addAll(returned);

            } else {
                Log.i(" ---- QUERY ----", "Failed to query ES: " + ids[0]);
            }
        } catch (Exception e) {
            Log.i("Error", "Something went wrong when we tried to communicate with the Elasticsearch server!");
        }
        return follow;
    }

    @Override
    protected void onPostExecute(ArrayList<Follow> follows) {
        super.onPostExecute(follows);

        if (follows.size() == 1) {
            Follow fol =  follows.get(0);
            fol.setAccepted(true);
            fol.setAcceptedDate(new Date());
            Index index = new Index.Builder(fol).index(ServerCommandManager.INDEX).type(ServerCommandManager.FOLLOW).build();
            try {
                ServerCommandManager.getClient().execute(index);
            } catch (Exception e) {
                Log.d("--- Accept ----", "Error accepting follow req.");
            }
        } else {
            Log.d("--- Accept ---","Could not identify 1 follow request");
        }
    }
}
