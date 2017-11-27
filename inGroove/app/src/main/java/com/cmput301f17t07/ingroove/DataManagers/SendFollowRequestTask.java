package com.cmput301f17t07.ingroove.DataManagers;

import android.os.AsyncTask;
import android.util.Log;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import com.cmput301f17t07.ingroove.Model.Follow;
import com.searchly.jestdroid.JestDroidClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * Async task to send a new follow object to Elastic Search
 *
 * Created by Fraser Bulbuc on 2017-11-26.
 */

public class SendFollowRequestTask extends AsyncTask<Follow, Void, Void> {

    @Override
    protected Void doInBackground(Follow... follows) {

        JestDroidClient client = ServerCommandManager.getClient();

        for (Follow follow: follows) {
            Index index = new Index.Builder(follow).index("cmput301f17t07_ingroove").type("follow").build();

            try {
                DocumentResult res = client.execute(index);
            } catch (Exception e) {
                Log.i("--- FOLLOW ---", "Failed to send follow to server, there may not be a connection");
            }
        }
        return null;
    }
}
