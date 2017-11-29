package com.cmput301f17t07.ingroove.DataManagers.QueryTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * A generic getRequest that retrieves data from the ElasticSearch server and notifies
 * the result handler on completion
 * 
 * Created by Fraser Bulbuc on 2017-11-26.
 */

public class GenericGetRequest<T> extends AsyncTask<String, Void, ArrayList<T>> {

    private String type;
    private String matchingAttribute;
    private Class<T> typeOfT;
    private AsyncResultHandler<T> resultHandler;

    /**
     * Default Ctor
     *
     * @param resultHandler the result to update upon completion of the request
     * @param typeOfT the class of the type T
     * @param type the name of the index to query, for example: "habit" or "habit_event"
     * @param matchingAttribute the name of the object attribute to filter based on the query
     *                          for example: "name" or "comment"
     *
     */
    public GenericGetRequest(AsyncResultHandler<T> resultHandler, Class<T> typeOfT, String type, String matchingAttribute) {
        this.typeOfT = typeOfT;
        this.matchingAttribute = matchingAttribute;
        this.type = type;
        this.resultHandler = resultHandler;
    }

    @Override
    protected ArrayList<T> doInBackground(String... searchParam) {

        ArrayList<T> result = new ArrayList<>();
        String query;

        if (searchParam[0].equals("")) {
            query = searchParam[0];
        }
        else {
            query = "{\n" +
                    "    \"query\" : {\n" +
                    "        \"wildcard\" : { \"" + matchingAttribute + "\" : \"" + searchParam[0] + "*\" }\n" +
                    "    }\n" +
                    "}";

        }

        Search search = new Search.Builder(query).addIndex("ingroove").addType(type).build();


        try {
            SearchResult res  = ServerCommandManager.getClient().execute(search);

            if (res.isSucceeded())
            {
                List<T> found = res.getSourceAsObjectList(typeOfT);
                result.addAll(found);
            }
            else
            {
                Log.i(" ---- QUERY ----", "Failed to query ES: " + searchParam[0]);
            }
        }
        catch (Exception e) {
            Log.i("Error", "Something went wrong when we tried to communicate with the Elasticsearch server!");
        }

        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<T> queryResult) {
        super.onPostExecute(queryResult);
        Log.d("--- GEN GET ---","Get request succeeded, found " + queryResult.size() + " matching " + type + "(s).");
        resultHandler.handleResult(queryResult);
    }
}
