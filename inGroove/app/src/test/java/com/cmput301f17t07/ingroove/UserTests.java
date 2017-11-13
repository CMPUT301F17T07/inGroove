package com.cmput301f17t07.ingroove;

import com.cmput301f17t07.ingroove.Model.User;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the User entity
 *
 * Created by Fraser Bulbuc on 2017-11-12.
 */

public class UserTests {

    /**
     * Test the comparison method of the user class
     *
     * @see User
     */
    @Test
    public void equalsTest() {

        User testUser1 = new User("test user 1");
        User testUser2 = new User("test user 2");
        User testUser3 = testUser1;

        assertFalse(testUser1.equals(testUser2));
        assertTrue(testUser1.equals(testUser3));

       testUser1.setEmail("test@test.com");
       assertTrue(testUser1.equals(testUser3));
    }

}
