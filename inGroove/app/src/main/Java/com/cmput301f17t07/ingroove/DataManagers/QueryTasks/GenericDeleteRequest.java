package com.cmput301f17t07.ingroove.DataManagers.QueryTasks;

import android.os.AsyncTask;
import android.util.Log;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import io.searchbox.core.DeleteByQuery;

/**
 * Generic Delete request for follow objects from ElasticSearch
 *
 * Created by fraserbulbuc on 2017-11-28.
 */
public class GenericDeleteRequest<T> extends AsyncTask<String, Void, Void> {

    private String type;
    private AsyncResultHandler<T> resultHandler;
    private String currentUserID;

    /**
     * Default Ctor
     *
     * @param resultHandler the result to update upon completion of the request
     * @param type the name of the index to query, for example: "habit" or "habit_event"
     */
    public GenericDeleteRequest(AsyncResultHandler<T> resultHandler, String type, String currentUserID) {
        this.type = type;
        this.resultHandler = resultHandler;
        this.currentUserID = currentUserID;
    }

    @Override
    protected Void doInBackground(String... ids) {
        Log.d("--- IN BGN ---", "Trying to delete follow obj.");

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

        DeleteByQuery del = new DeleteByQuery.Builder(query).addIndex("ingroove").addType(type).build();

        try {
            ServerCommandManager.getClient().execute(del);
            Log.d("--- IN BGN ---", "Should have deleted follow obj.");
        }
        catch (Exception e) {
            Log.i("Error", "Something went wrong when we tried to communicate with the Elasticsearch server!");
        }
        return null;
    }

}
