package com.cmput301f17t07.ingroove;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * This test is meant for the activity CurrentHabits.
 * It assumes that a habit called "Drink Water" is present and it has an associated habitevent called "8 cups".
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class CurrentHabitsTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    

    @Test
    public void CurrentHabitsTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        //Test if the upcoming button works.
        onView(withId(R.id.Upcomingbutton)).perform(click());
        onView(withId(R.id.HabitViewer)).check(matches(withText("Drink Water")));

        onView(withId(R.id.FinishedButton)).perform(click());
        onView(withId(R.id.HabitViewer)).check(matches(withText("8 cups")));

        onView(withId(R.id.ListHabitsButton)).perform(click());
        onView(withId(R.id.HabitViewer)).check(matches(withText("Drink Water")));
    }
}