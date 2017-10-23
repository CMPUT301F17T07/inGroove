package com.cmput301f17t07.ingroove;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Ashley on 2017-10-22.
 */

public class RelationshipManagerTest extends ActivityInstrumentationTestCase2 {

    public RelationshipManagerTest(String pkg, Class activityClass) {
        super(pkg, activityClass);
    }

    // note, at the moment the majority of these tests will not pass because the RelationshipManager.java
    // class is not complete and is not returning meaningful results

    public void testGetFollowersOf() {
        User user1 = new User("Test1", "test1@test.com", 1);
        User user2 = new User("Test2", "test2@test.com", 2);
        RelationshipManager RM = new RelationshipManager();

        // get the followers of user2, should be empty
        assertTrue(RM.getFollowersOf(user2.getUserID()).isEmpty());

        Follow follow = new Follow(user1.getUserID(), user2.getUserID()); // user1 requests to follow user 2
        follow.setAccepted(Boolean.TRUE); // accept the request

        // we added a follower, should no longer be empty and should contain user1
        assertTrue(RM.getFollowersOf(user2.getUserID()).contains(user1.getUserID()));
    }

    public void testGetRequestsToFollow() {
        User user1 = new User("Test1", "test1@test.com", 1);
        User user2 = new User("Test2", "test2@test.com", 2);
        RelationshipManager RM = new RelationshipManager();

        // get the followers of user2, should be empty
        assertTrue(RM.getFollowersOf(user2.getUserID()).isEmpty());

        Follow follow = new Follow(user1.getUserID(), user2.getUserID()); // user1 requests to follow user 2

        // check that the requests to follow user2 contain user1
        assertTrue(RM.getRequestsToFollow(user2.getUserID()).contains(user1.getUserID()));

        // accept the request
        follow.setAccepted(Boolean.TRUE);

        // check to make sure user1 is no longer in the list
        assertFalse(RM.getRequestsToFollow(user2.getUserID()).contains(user1.getUserID()));
    }

    public void testGetRequestsBy() {
        User user1 = new User("Test1", "test1@test.com", 1);
        User user2 = new User("Test2", "test2@test.com", 2);
        RelationshipManager RM = new RelationshipManager();

        // make sure that user1 has not requsted to follow user2 yet
        assertFalse(RM.getRequestsBy(user1.getUserID()).contains(user2.getUserID()));

        Follow follow = new Follow(user1.getUserID(), user2.getUserID()); // user1 requests to follow user 2

        // check to make sure user1 has requested to follow user2
        assertTrue(RM.getRequestsBy(user1.getUserID()).contains(user2.getUserID()));
    }

    public void testGetFollowingFor() {
        User user1 = new User("Test1", "test1@test.com", 1);
        User user2 = new User("Test2", "test2@test.com", 2);
        RelationshipManager RM = new RelationshipManager();

        // check that user1 is not yet following user2
        assertFalse(RM.getFollowingFor(user1.getUserID()).contains(user2.getUserID()));

        Follow follow = new Follow(user1.getUserID(), user2.getUserID()); // user1 requests to follow user 2
        follow.setAccepted(Boolean.TRUE);

        // check that user1 is following user2
        assertTrue(RM.getFollowingFor(user1.getUserID()).contains(user2.getUserID()));
    }

    public void testIsUserFollowedBy() {
        User user1 = new User("Test1", "test1@test.com", 1);
        User user2 = new User("Test2", "test2@test.com", 2);
        RelationshipManager RM = new RelationshipManager();

        // check to make sure user1 is not following user2
        assertFalse(RM.isUserFollowedBy(user1.getUserID(), user2.getUserID()));

        Follow follow = new Follow(user1.getUserID(), user2.getUserID()); // user1 requests to follow user 2

        // check to make sure user1 is following user2
        assertTrue(RM.isUserFollowedBy(user1.getUserID(), user2.getUserID()));
    }

    public void testIsUserFollowing() {
        User user1 = new User("Test1", "test1@test.com", 1);
        User user2 = new User("Test2", "test2@test.com", 2);
        RelationshipManager RM = new RelationshipManager();

        // make sure user1 is not following user2
        assertFalse(RM.isUserFollowing(user1.getUserID(), user2.getUserID()));

        Follow follow = new Follow(user1.getUserID(), user2.getUserID()); // user1 requests to follow user 2

        // make sure user1 is following user2
        assertTrue(RM.isUserFollowing(user1.getUserID(), user2.getUserID()));
    }

    public void testSendFollowRequest() {
        User user1 = new User("Test1", "test1@test.com", 1);
        User user2 = new User("Test2", "test2@test.com", 2);
        RelationshipManager RM = new RelationshipManager();

        // send the follow request for user1 to follow user2
        RM.sendFollowRequest(user1.getUserID(), user2.getUserID());

        // check to make sure the request is sent
        assertTrue(RM.getRequestsBy(user1.getUserID()).contains(user2.getUserID()));
    }

    public void testAcceptFollowRequest() {
        User user1 = new User("Test1", "test1@test.com", 1);
        User user2 = new User("Test2", "test2@test.com", 2);
        RelationshipManager RM = new RelationshipManager();

        RM.sendFollowRequest(user1.getUserID(), user2.getUserID()); // user1 requests to follow user2
        RM.acceptFollowRequest(user1.getUserID(), user2.getUserID()); // user2 accepts user1's follow request

        assertTrue(RM.getFollowersOf(user2.getUserID()).contains(user1.getUserID()));

    }

}
