package com.cmput301f17t07.ingroove.DataManagers.QueryTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import com.cmput301f17t07.ingroove.Model.Follow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by Fraser Bulbuc on 2017-12-02.
 */

public class AcceptFollowRequestObjIDTask extends AsyncTask<String, Void, ArrayList<Boolean>> {

    private String type;
    private String currentUserID;
    private String followID;
    private AsyncResultHandler<Boolean> handler;

    /**
     * Default Ctor
     *
     * @param type the name of the index to query, for example: "habit" or "habit_event"
     */
    public AcceptFollowRequestObjIDTask(String type, String currentUserID, AsyncResultHandler<Boolean> handler) {
        this.type = type;
        this.currentUserID = currentUserID;
        this.handler = handler;
    }

    @Override
    protected ArrayList<Boolean> doInBackground(String... ids) {

        ArrayList<Follow> follow = new ArrayList<>();
        ArrayList<Boolean> boolRes = new ArrayList<>();

        String folID = ids[0] + currentUserID;

        Get get = new Get.Builder(ServerCommandManager.INDEX, folID).type(ServerCommandManager.FOLLOW).build();

        try {
            
            JestResult res = ServerCommandManager.getClient().execute(get);
            Follow followResult = res.getSourceAsObject(Follow.class);

            if (res.isSucceeded() && followResult != null && followResult.getFollower().equals(ids[0])
                    && followResult.getFollowee().equals(currentUserID) && !followResult.getAccepted()) {

                Log.d("--- ACCEPT_FOL_REQ_OBJ_ID ---", "Successfully retrieved follow object.");

                followResult.setAcceptedDate(new Date());
                followResult.setAccepted(true);

                Index index = new Index.Builder(followResult).index(ServerCommandManager.INDEX).type(ServerCommandManager.FOLLOW).id(followResult.getObjectID()).build();

                try {
                    DocumentResult result = ServerCommandManager.getClient().execute(index);
                    if (result.isSucceeded()) {
                        boolRes.add(Boolean.TRUE);
                    } else {
                        boolRes.add(Boolean.FALSE);
                    }
                } catch (Exception e) {
                    Log.d("--- ACCEPT_FOL_REQ_OBJ_ID ---", "Error querying elastic search." + e);
                    boolRes.add(Boolean.FALSE);
                }

            } else {
                Log.d("--- ACCEPT_FOL_REQ_OBJ_ID ---", "Failed to retrieve matching follow object.");
                boolRes.add(Boolean.FALSE);
            }
        } catch (Exception e) {
            Log.i("--- ACCEPT_FOL_REQ_OBJ_ID ---", "Error querying elastic search." + e);
            boolRes.add(Boolean.FALSE);
        }

        return boolRes;
    }

    @Override
    protected void onPostExecute(ArrayList<Boolean> isSucceded) {
        super.onPostExecute(isSucceded);
        handler.handleResult(isSucceded);
    }
}
