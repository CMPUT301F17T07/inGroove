package com.cmput301f17t07.ingroove.DataManagers.QueryTasks;

import android.os.AsyncTask;
import android.util.Log;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import com.cmput301f17t07.ingroove.Model.Follow;
import com.searchly.jestdroid.JestDroidClient;
import java.io.IOException;
import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;

/**
 * [Model Class]
 * Async task to send a new follow object to Elastic Search
 *
 * Created by Fraser Bulbuc on 2017-11-26.
 */

public class SendFollowRequestTask extends AsyncTask<Follow, Void, Void> {

    @Override
    protected Void doInBackground(Follow... follows) {

        JestDroidClient client = ServerCommandManager.getClient();

        for (Follow follow: follows) {
            Get.Builder getBuilder = new Get.Builder(ServerCommandManager.INDEX, follow.getObjectID()).type(ServerCommandManager.FOLLOW);
            Get get = getBuilder.build();

            try {
                JestResult result = client.execute(get);
                Follow getFollowResult = result.getSourceAsObject(Follow.class);

                if (getFollowResult == null || !result.isSucceeded()) { //if this request is not on the server
                    follow.setAccepted(Boolean.FALSE);
                    Index index = new Index.Builder(follow).id(follow.getObjectID()).index(ServerCommandManager.INDEX).type(ServerCommandManager.FOLLOW).build();
                    DocumentResult res = client.execute(index);
                    if (res.isSucceeded()) {
                        Log.i("---SEND FOLLOW REQUEST ---", "Succeeded in sending follow request");
                    } else {
                        Log.i("---SEND FOLLOW REQUEST ---", "Cant send request " + res.getErrorMessage());
                    }

                } else {
                    Log.i("---SEND FOLLOW REQUEST ---", "Follow Request already sent...");
                }

            } catch (IOException e) {
                Log.i("---SEND FOLLOW REQUEST ---", "Error communicating with server: " + e);
            }
        }
        return null;
    }
}
