package com.cmput301f17t07.ingroove;

import com.cmput301f17t07.ingroove.DataManagers.HabitEventManager;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;



/**
 * Created by Christopher Walter on 2017-10-22.
 */

public class HabitEventManagerTest {

    public void testAddHabitEvent() {

//        HabitEventManager eventManager = HabitEventManager.getInstance();
//
//        User user = new User("Test", "test@test.com", 0);
//
//        HabitEvent event = new HabitEvent("test", null, new Date(), null, 0, 0, user.getUserID(), null, Boolean.TRUE);
//
//        eventManager.addHabitEvent(event);
//
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -7);
//
//        ArrayList<HabitEvent> events = eventManager.getRecentEvents(user,cal.getTime());
//
//        assertTrue(events.contains(event));


    }

    public void testRemoveHabitEvent() {

//        HabitEventManager eventManager = HabitEventManager.getInstance();
//
//        User user = new User("Test", "test@test.com", 0);
//
//        HabitEvent event = new HabitEvent("test", null, new Date(), null, 0, 0, user.getUserID(), null, Boolean.TRUE);
//
//        eventManager.addHabitEvent(event);
//
//        eventManager.removeHabitEvent(event);
//
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -7);
//        ArrayList<HabitEvent> events = eventManager.getRecentEvents(user, cal.getTime());
//
//        assertTrue(events.isEmpty());
    }

    public void testGetRecentEvents() {

//        HabitEventManager eventManager = HabitEventManager.getInstance();
//
//        User user = new User("Test", "test@test.com", 0);
//
//        HabitEvent event = new HabitEvent("test", null, new Date(), null, 0, 0, user.getUserID(), null, Boolean.TRUE);
//
//        eventManager.addHabitEvent(event);
//
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -7);
//
//
//        ArrayList<HabitEvent> recentEvents = eventManager.getRecentEvents(user, cal.getTime());
//
//        assertTrue(recentEvents.contains(event));
    }

    public void testGetMissedEventsSince() {

//        HabitEventManager eventManager = HabitEventManager.getInstance();
//
//        User user = new User("Test", "test@test.com", 0);
//
//        HabitEvent missedEvent = new HabitEvent("missed", null, new Date(), null, 0, 0, user.getUserID(), null, Boolean.FALSE);
//
//        eventManager.addHabitEvent(missedEvent);
//
//        HabitEvent completedEvent = new HabitEvent("completed", null, new Date(), null, 1, 0, user.getUserID(), null, Boolean.TRUE);
//
//        Calendar cal = Calendar.getInstance();
//
//        cal.add(Calendar.DATE, -7);
//
//
//        ArrayList<HabitEvent> missedEvents = eventManager.getMissedEventsSince(user, cal.getTime());
//
//        assertTrue(missedEvents.contains(missedEvent));
//        assertFalse(missedEvents.contains(completedEvent));

    }

    public void testGetCompletedEventsSince() {

//        HabitEventManager eventManager = HabitEventManager.getInstance();
//
//        User user = new User("Test", "test@test.com", 0);
//
//        HabitEvent missedEvent = new HabitEvent("missedtest", null, new Date(), null, 0, 0, user.getUserID(), null, Boolean.FALSE);
//
//        eventManager.addHabitEvent(missedEvent);
//
//        HabitEvent completedEvent = new HabitEvent("test", null, new Date(), null, 1, 0, user.getUserID(), null, Boolean.TRUE);
//
//        Calendar cal = Calendar.getInstance();
//
//        cal.add(Calendar.DATE, -7);
//
//
//        ArrayList<HabitEvent> completedEvents = eventManager.getMissedEventsSince(user, cal.getTime());
//
//        assertFalse(completedEvents.contains(missedEvent));
//        assertTrue(completedEvents.contains(completedEvent));
    }

    public void testGetCompletionPercentageSince() {
//        HabitEventManager eventManager = HabitEventManager.getInstance();
//
//        User user = new User("Test", "test@test.com", 0);
//
//        HabitEvent missedEvent = new HabitEvent("test", null, new Date(), null, 0, 0, user.getUserID(), null, Boolean.FALSE);
//
//        eventManager.addHabitEvent(missedEvent);
//
//        HabitEvent completedEvent = new HabitEvent("test", null, new Date(), null, 1, 0, user.getUserID(), null, Boolean.TRUE);
//
//        eventManager.addHabitEvent(completedEvent);
//
//        Calendar cal = Calendar.getInstance();
//
//        cal.add(Calendar.DATE, -7);
//
//        int percentage = eventManager.getCompletionPercentageSince(user, cal.getTime());
//
//        assertEquals(percentage, 50);


    }
}
