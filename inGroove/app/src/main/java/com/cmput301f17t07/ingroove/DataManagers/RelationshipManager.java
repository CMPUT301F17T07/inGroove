package com.cmput301f17t07.ingroove.DataManagers;

import java.util.ArrayList;

/**
 * Created by Ashley on 2017-10-22.
 */

public class RelationshipManager {

    private static RelationshipManager instance = new RelationshipManager();

    private RelationshipManager() {

    }

    public static RelationshipManager getInstance() {
        return instance;
    }

    public ArrayList<Integer> getFollowersOf(int user) {
        // will query elastic search and get a list of all the other users who
        // follow the given user

        return new ArrayList<Integer>();
    }

    public ArrayList<Integer> getRequestsToFollow(int user) {
        // will query elastic search and get a list of all the users who are
        // requesting to follow the given user

        return new ArrayList<Integer>();
    }

    public ArrayList<Integer> getRequestsBy(int user) {
        // will query elastic search and get a list of all the users the
        // given user is requesting to follow

        return new ArrayList<Integer>();
    }

    public ArrayList<Integer> getFollowingFor(int user) {
        // will query elastic search to get a list of all the users that
        // the given user is following

        return new ArrayList<Integer>();
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

    public void sendFollowRequest(int follower, int followee) {
        // send follow request
    }

    public void acceptFollowRequest(int follower, int followee) {
        // accept a follow request
    }
}
