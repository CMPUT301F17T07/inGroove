package com.cmput301f17t07.ingroove.DataManagers;

import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.GetFollowsTask;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.SendFollowRequestTask;
import com.cmput301f17t07.ingroove.Model.Follow;
import com.cmput301f17t07.ingroove.Model.User;

/**
 * [Model Class]
 * Class that managers the relationships between users. The class handles all interactions with follow
 * objects and updates the server upon any changes.
 *
 * @see Follow
 * @see User
 *
 */
public class RelationshipManager {

    private static RelationshipManager instance = new RelationshipManager();

    private RelationshipManager() {}

    public static RelationshipManager getInstance() {
        return instance;
    }

    public void getFollowersOf(AsyncResultHandler resultHandler, String userID) {
        // will query elastic search and get a list of all the other users who
        // follow the given user
        GetFollowsTask task = new GetFollowsTask(resultHandler, true, false);
        task.execute(userID);
    }

    public void getFollowRequests(AsyncResultHandler resultHandler, String userID) {
        // will query elastic search and get a list of all the users who are
        // requesting to follow the given user
        GetFollowsTask task = new GetFollowsTask(resultHandler, false, false);
        task.execute(userID);
    }

    public void getRequestsBy(AsyncResultHandler resultHandler, String userID) {
        // will query elastic search and get a list of all the users the
        // given user is requesting to follow
        GetFollowsTask task = new GetFollowsTask(resultHandler, false, true);
        task.execute(userID);
    }

    public void getWhoThisUserFollows(AsyncResultHandler handler, User user) {
        // will query elastic search to get a list of all the users that
        // the given user is following
        GetFollowsTask task = new GetFollowsTask(handler, true, true);
        task.execute(user.getUserID());

    }


    public int sendFollowRequest(User userRequestingToFollow, User userBeingFollowed) {
        Follow follow = new Follow(userRequestingToFollow.getUserID(), userBeingFollowed.getUserID());
        try {
            new SendFollowRequestTask().execute(follow);
            return 0;
        }
        catch (Exception e) {
            return -1;
        }
    }

}
