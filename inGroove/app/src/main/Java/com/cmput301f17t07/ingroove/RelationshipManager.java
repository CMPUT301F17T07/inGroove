package com.cmput301f17t07.ingroove;

import java.util.ArrayList;

/**
 * Created by Ashley on 2017-10-22.
 */

public class RelationshipManager {

    public RelationshipManager() {

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
        return Boolean.TRUE;
    }

    public Boolean isUserFollowing(int follower, int followee) {
        return Boolean.TRUE;
    }

    public void sendFollowRequest(int follower, int followee) {

    }

    public void acceptFollowRequest(int follower, int followee) {

    }
}
