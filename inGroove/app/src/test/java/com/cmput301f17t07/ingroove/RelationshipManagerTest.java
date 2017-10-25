package com.cmput301f17t07.ingroove;

import com.cmput301f17t07.ingroove.Model.Follow;
import com.cmput301f17t07.ingroove.Model.User;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Ashley on 2017-10-22.
 */

/**
 *
 * Class is responsible for testing the RelationshipManager control class
 *
 * @author Ashley Holgate
 *
 */
public class RelationshipManagerTest {

    // note, at the moment the majority of these tests will not pass because the RelationshipManager.java
    // class is not complete and is not returning meaningful results

    /**
     * Tests getFollowersOf(user) method in RelationshipManager class
     */
    @Test
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

    /**
     * Tests getRequestsToFollow(user) method in RelationshipManager class
     */
    @Test
    public void testGetRequestsToFollow() {
        User user1 = new User("Test1", "test1@test.com", 1);
        User user2 = new User("Test2", "test2@test.com", 2);
        RelationshipManager RM = new RelationshipManager();

        // check to make sure user1 is not requesting to follow user2
        assertFalse(RM.getRequestsToFollow(user2.getUserID()).contains(user1.getUserID()));

        Follow follow = new Follow(user1.getUserID(), user2.getUserID()); // user1 requests to follow user 2

        // check that the requests to follow user2 contain user1
        assertTrue(RM.getRequestsToFollow(user2.getUserID()).contains(user1.getUserID()));

        // accept the request
        follow.setAccepted(Boolean.TRUE);

        // check to make sure user1 is no longer in the list
        assertFalse(RM.getRequestsToFollow(user2.getUserID()).contains(user1.getUserID()));
    }

    /**
     * Tests getRequestsBy(user) method in RelationshipManager class
     */
    @Test
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

    /**
     * Tests getFollowingFor(user) method in RelationshipManager class
     */
    @Test
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

    /**
     * Tests isUserFollowedBy(follower, followee) method in RelationshipManager class
     */
    @Test
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

    /**
     * Tests isUserFollowing(follower, followee) method in RelationshipManager class
     */
    @Test
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

    /**
     * Tests sendFollowRequest(follower, followee) method in RelationshipManager class
     */
    @Test
    public void testSendFollowRequest() {
        User user1 = new User("Test1", "test1@test.com", 1);
        User user2 = new User("Test2", "test2@test.com", 2);
        RelationshipManager RM = new RelationshipManager();

        // send the follow request for user1 to follow user2
        RM.sendFollowRequest(user1.getUserID(), user2.getUserID());

        // check to make sure the request is sent
        assertTrue(RM.getRequestsBy(user1.getUserID()).contains(user2.getUserID()));
    }

    /**
     * Tests getFollowersOf(follower, followee) method in RelationshipManager class
     */
    @Test
    public void testAcceptFollowRequest() {
        User user1 = new User("Test1", "test1@test.com", 1);
        User user2 = new User("Test2", "test2@test.com", 2);
        RelationshipManager RM = new RelationshipManager();

        RM.sendFollowRequest(user1.getUserID(), user2.getUserID()); // user1 requests to follow user2
        RM.acceptFollowRequest(user1.getUserID(), user2.getUserID()); // user2 accepts user1's follow request

        assertTrue(RM.getFollowersOf(user2.getUserID()).contains(user1.getUserID()));

    }

}
