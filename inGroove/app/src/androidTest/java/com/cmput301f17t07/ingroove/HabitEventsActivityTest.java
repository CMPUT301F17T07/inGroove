package com.cmput301f17t07.ingroove;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * This test is meant for the activity HabitEventsActivity.
 * It assumes that the app has already navigated to the HabitEventsActivity screen, where a new habit event will be logged.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class HabitEventsActivityTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        onView(withId(R.id.nameTextBox)).perform(typeText("New Habit Event"));

        onView(withId(R.id.commentText)).perform(typeText("New Habit Event Comment"));

        onView(withId(R.id.SaveButton)).perform(click());
        //If the app doesn't crash, then everything is good.
    }
}