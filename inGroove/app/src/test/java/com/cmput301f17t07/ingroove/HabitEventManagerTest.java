package com.cmput301f17t07.ingroove;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;



/**
 * Created by Christopher Walter on 2017-10-22.
 */

public class HabitEventManagerTest {

    public void testAddHabitEvent() {

        HabitEventManager eventManager = new HabitEventManager();

        User user = new User("Test", "test@test.com", 0);

        HabitEvent event = new HabitEvent(User.getUserID(), new Date());

        eventManager.addHabitEvent(event);

        ArrayList<HabitEvent> events = eventManager.getRecentEvents();

        assertTrue(events.contains(event));

        // TODO: fix errors
    }

    public void testRemoveHabitEvent() {

        HabitEventManager eventManager = new HabitEventManager();

        User user = new User("Test", "test@test.com", 0);

        HabitEvent event = new HabitEvent(User.getUserID(), new Date());

        eventManager.addHabitEvent(event);

        eventManager.removeHabitEvent(event);

        ArrayList<HabitEvent> events = eventManager.getRecentEvents();

        assertTrue(events.isEmpty());

        // finished
    }

    public void testGetRecentEvents() {

        HabitEventManager eventManager = new HabitEventManager();

        User user = new User("Test", "test@test.com", 0);

        HabitEvent event = new HabitEvent(User.getUserID(), new Date());

        eventManager.addHabitEvent(event);

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, -7);


        ArrayList<HabitEvent> recentEvents = eventManager.getRecentEvents(user, cal.getTime());

        assertTrue(recentEvents.contains(event));
    }

    public void testGetMissedEventsSince() {

        HabitEventManager eventManager = new HabitEventManager();

        User user = new User("Test", "test@test.com", 0);

        HabitEvent missedEvent = new HabitEvent(User.getUserID(), new Date());
        //TODO: set as missed

        eventManager.addHabitEvent(missedEvent);

        HabitEvent completedEvent = new HabitEvent(User.getUserID(), new Date());
        // TODO: set as completed

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, -7);


        ArrayList<HabitEvent> missedEvents = eventManager.getMissedEventsSince(user, cal.getTime());

        assertTrue(missedEvents.contains(missedEvent));
        assertFalse(missedEvents.contains(completedEvent));

    }

    public void testGetCompletedEventsSince() {

        HabitEventManager eventManager = new HabitEventManager();

        User user = new User("Test", "test@test.com", 0);

        HabitEvent missedEvent = new HabitEvent(User.getUserID(), new Date());
        //TODO: set as missed

        eventManager.addHabitEvent(missedEvent);

        HabitEvent completedEvent = new HabitEvent(User.getUserID(), new Date());
        // TODO: set as completed

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, -7);


        ArrayList<HabitEvent> completedEvents = eventManager.getMissedEventsSince(user, cal.getTime());

        assertFalse(completedEvents.contains(missedEvent));
        assertTrue(completedEvents.contains(completedEvent));
    }

    public void testGetCompletionPercentageSince() {
        HabitEventManager eventManager = new HabitEventManager();

        User user = new User("Test", "test@test.com", 0);

        HabitEvent missedEvent = new HabitEvent(User.getUserID(), new Date());
        //TODO: set as missed

        eventManager.addHabitEvent(missedEvent);

        HabitEvent completedEvent = new HabitEvent(User.getUserID(), new Date());
        // TODO: set as completed

        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, -7);

        int percentage = eventManager.getCompletionPercentageSince(user, cal.getTime());

        assertEquals(percentage, 50);
        

    }
}
