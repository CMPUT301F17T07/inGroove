package com.cmput301f17t07.ingroove.DataManagers.QueryTasks;

import android.os.AsyncTask;
import android.text.BoringLayout;
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

public class AcceptFollowRequestTask extends AsyncTask<String, Void, ArrayList<Boolean>> {

    private String type;
    private String currentUserID;
    private String followID;
    private AsyncResultHandler<Boolean> handler;

    /**
     * Default Ctor
     *
     * @param type the name of the index to query, for example: "habit" or "habit_event"
     */
    public AcceptFollowRequestTask(String type, String currentUserID, AsyncResultHandler<Boolean> handler) {
        this.type = type;
        this.currentUserID = currentUserID;
        this.handler = handler;
    }


    @Override
    protected ArrayList<Boolean> doInBackground(String... ids) {

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
                Log.i("-- ACCEPT FOL REQ --", "Failed to query ES: " + ids[0]);
            }
        } catch (Exception e) {
            Log.i("-- ACCEPT FOL REQ --", "Error: Something went wrong when we tried to communicate with the Elasticsearch server!" + e);
        }

        ArrayList<Boolean> result = new ArrayList<>();

        if (follow.size() == 1) {
            Follow fol =  follow.get(0);
            fol.setAccepted(true);
            fol.setAcceptedDate(new Date());
            Index index = new Index.Builder(fol).index(ServerCommandManager.INDEX).type(ServerCommandManager.FOLLOW).build();
            try {
                ServerCommandManager.getClient().execute(index);
                result.add(Boolean.TRUE);
            } catch (Exception e) {
                Log.d("-- ACCEPT FOL REQ --", "Error accepting follow req." + e);
                result.add(Boolean.FALSE);
            }
        } else {
            result.add(Boolean.FALSE);
        }


        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<Boolean> isSucceded) {
        super.onPostExecute(isSucceded);
        handler.handleResult(isSucceded);
    }
}
