package com.cmput301f17t07.ingroove.DataManagers.QueryTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.Habit;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * ASync class to retrieve habits from the server
 *
 * Created by Fraser Bulbuc on 2017-11-23.
 */
public class GetHabitTask extends AsyncTask<String, Void, ArrayList<Habit>> {

    private DataManagerAPI data = DataManager.getInstance();


    @Override
    protected ArrayList<Habit> doInBackground(String... searchParam) {

        ArrayList<Habit> matchingHabits = new ArrayList<>();
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

        Search search = new Search.Builder(query).addIndex("cmput301f17t07_ingroove").addType("habit").build();


        try {
            SearchResult result = ServerCommandManager.getClient().execute(search);

            if (result.isSucceeded())
            {
                List<Habit> foundHabits = result.getSourceAsObjectList(Habit.class);
                matchingHabits.addAll(foundHabits);
                Log.i(" ---- IN BGND ----", "Query results of size: " + foundHabits.size());

            }
            else
            {
                Log.i(" ---- HABIT QUERY ----", "Failed to query habits with: " + searchParam[0]);
            }
        }
        catch (Exception e) {
            Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
        }

        return matchingHabits;
    }

    @Override
    protected void onPostExecute(ArrayList<Habit> habits) {
        Log.i(" ---- POST EXEC ----", "Query results of size: " + habits.size());

        data.getFindHabitsQueryResults().setValue(habits);

    }

}
