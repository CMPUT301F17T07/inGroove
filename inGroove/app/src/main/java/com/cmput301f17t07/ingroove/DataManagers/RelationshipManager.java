package com.cmput301f17t07.ingroove.DataManagers;

import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.SendFollowRequestTask;
import com.cmput301f17t07.ingroove.Model.Follow;
import com.cmput301f17t07.ingroove.Model.User;
import java.util.ArrayList;

/**
 * Created by Ashley on 2017-10-22.
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

    public Boolean isUserFollowedBy(int follower, int followee) {
        // will query elastic search to to see if a user is followed by
        // the other user
        return Boolean.TRUE;
    }

    public Boolean isUserFollowing(int follower, int followee) {
        // will query elastic search to see if user is following the
        // other user

        return Boolean.TRUE;
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

    public void acceptFollowRequest(int follower, int followee) {
        // accept a follow request
    }
}
