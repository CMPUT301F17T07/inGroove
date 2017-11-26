package com.cmput301f17t07.ingroove.DataManagers.QueryTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by Fraser Bulbuc on 2017-11-25.
 */

public class GetHabitEventTask extends AsyncTask<String, Void, ArrayList<HabitEvent>> {

    private DataManagerAPI data = DataManager.getInstance();

    @Override
    protected ArrayList<HabitEvent> doInBackground(String... searchParam) {

        ArrayList<HabitEvent> matchingEvents = new ArrayList<>();
        String query;

        if (searchParam[0].equals("")) {
            query = searchParam[0];
        }
        else {

            query = "{\n" +
                    "    \"query\" : {\n" +
                    "        \"term\" : { \"name\" : \"" + searchParam[0] + "\" }\n" +
                    "    }\n" +
                    "}";

        }

        Search search = new Search.Builder(query).addIndex("cmput301f17t07_ingroove").addType("event").build();


        try {
            SearchResult result = ServerCommandManager.getClient().execute(search);

            if (result.isSucceeded())
            {
                List<HabitEvent> foundEvents = result.getSourceAsObjectList(HabitEvent.class);
                matchingEvents.addAll(foundEvents);
            }
            else
            {
                Log.i(" ---- EVENT QUERY ----", "Failed to query events with: " + searchParam[0]);
            }
        }
        catch (Exception e) {
            Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
        }

        return matchingEvents;
    }

    @Override
    protected void onPostExecute(ArrayList<HabitEvent> events) {
        Log.i(" ---- POST EXEC ----", "Event query results of size: " + events.size());

        data.getFindHabitEventsQueryResults().setValue(events);

    }

}