package com.cmput301f17t07.ingroove.DataManagers.QueryTasks;

import android.os.AsyncTask;
import android.util.Log;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import java.util.ArrayList;
import java.util.List;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Generic get request
 *
 * Retrieves all the objects of type T from Elastic Search
 * that have a property names matchingAttribute and a value
 * that contains the query within it.
 *
 * Created by Fraser Bulbuc on 2017-11-25.
 */


public class GetRequest<T> extends AsyncTask<String, Void, ArrayList<T>> {

    private String index;
    private String matchingAttribute;
    private Class<T> typeOfT;


    /**
     * Default Ctor
     *
     * @param typeOfT the class of the type T
     * @param index the name of the index to query, for example: "habit" or "habit_event"
     * @param matchingAttribute the name of the object attribute to filter based on the query
     *                          for example: "name" or "comment"
     *
     */
    public GetRequest(Class<T> typeOfT, String index, String matchingAttribute) {
        this.typeOfT = typeOfT;
        this.matchingAttribute = matchingAttribute;
        this.index = index;
    }

    @Override
    protected ArrayList doInBackground(String... searchParam) {
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

        Search search = new Search.Builder(query).addIndex("cmput301f17t07_ingroove").addType(index).build();


        try {
            SearchResult res  = ServerCommandManager.getClient().execute(search);

            if (res.isSucceeded())
            {
                List<T> found = res.getSourceAsObjectList(typeOfT);
                result.addAll(found);
            }
            else
            {
                Log.i(" ---- EVENT QUERY ----", "Failed to query events with: " + searchParam[0]);
            }
        }
        catch (Exception e) {
            Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
        }

        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<T> queryResult) {
        super.onPostExecute(queryResult);

        switch (index) {
            case "habit":
                DataManager.getInstance().getFindHabitsQueryResults().setValue((ArrayList<Habit>) queryResult);
                break;
            case "habit_event":
                DataManager.getInstance().getFindHabitEventsQueryResults().setValue((ArrayList<HabitEvent>) queryResult);
                break;
            case "user":
                break;
            default:

        }
    }
}


