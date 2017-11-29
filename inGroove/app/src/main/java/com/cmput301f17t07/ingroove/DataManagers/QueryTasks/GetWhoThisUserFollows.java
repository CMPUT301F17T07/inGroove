package com.cmput301f17t07.ingroove.DataManagers.QueryTasks;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.cmput301f17t07.ingroove.DataManagers.Command.ServerCommandManager;
import com.cmput301f17t07.ingroove.Model.Follow;
import com.cmput301f17t07.ingroove.Model.User;

import java.util.ArrayList;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by Christopher Walter on 2017-11-25.
 */

public class GetWhoThisUserFollows extends AsyncTask<User, Void, ArrayList<User>> {

    private AsyncResultHandler resultHandler;

    public GetWhoThisUserFollows(AsyncResultHandler<User> resultHandler) {
        this.resultHandler = resultHandler;
    }

    @Override
    protected ArrayList<User> doInBackground(User... users) {
        // find all the follow objects with the given user is the follower
        // for each of the follow objects get the user that is being followed and add it to the return list

        ArrayList<Follow> followArrayList = new ArrayList<>();

        ArrayList<User> foundUsers = new ArrayList<>();

        String query =  "{\n" +
                        "    \"query\" : {\n" +
                        "        \"term\" : { \"follower\" : \"" + users[0].getUserID() + "\" }\n" +
                        "    }\n" +
                        "}";



        Log.i("GetWhoFollowed", "Looking For follower with ID: " + users[0].getUserID());



        Search search = new Search.Builder(query).addIndex("ingroove").addType("follow").build();

        try {
            SearchResult result = ServerCommandManager.getClient().execute(search);
            Log.i("GetWhoFollowed", "Result " + result.getJsonString());


            if (result.isSucceeded()) {
                Log.i("GetWhoFollowed", "found " + String.valueOf(result.getSourceAsStringList().size()) + " followees");

                followArrayList.addAll(result.getSourceAsObjectList(Follow.class));
                Log.i("GetWhoFollowed", "found " + String.valueOf(followArrayList.size()) + " followees");
            }

        } catch (Exception e) {
            Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
        }

        if (!followArrayList.isEmpty()) {

            for (Follow follow : followArrayList) {

                Get get = new Get.Builder("ingroove", follow.getFollowee()).type("user").build();

                try {
                    DocumentResult result = ServerCommandManager.getClient().execute(get);

                    if (result.isSucceeded()) {
                        foundUsers.add(result.getSourceAsObject(User.class));
                    }
                } catch (Exception e) {
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                    continue;
                }
            }
        }



        return foundUsers;
    }

    @Override
    protected void onPostExecute(ArrayList<User> users) {
        super.onPostExecute(users);

        resultHandler.handleResult(users);
    }
}
