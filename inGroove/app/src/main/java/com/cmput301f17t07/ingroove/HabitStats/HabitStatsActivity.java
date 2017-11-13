package com.cmput301f17t07.ingroove.HabitStats;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.R;

import org.joda.time.DateTime;
import org.joda.time.Weeks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Displays some statistics for a given habit.
 *
 * @see com.cmput301f17t07.ingroove.avehabit.ViewHabitActivity
 * @see com.cmput301f17t07.ingroove.avehabit.AddViewEditHabitActivity
 */
public class HabitStatsActivity extends AppCompatActivity {

    DataManagerAPI data = DataManager.getInstance();

    // elements on the habit stats page
    TextView completedHabits;
    TextView missedHabits;
    TextView progressText;
    ProgressBar habitProgress;

    ArrayList<HabitEvent> habitEvents;

    Habit passedHabit;

    // statistics variables
    int totalExpectedDays;
    int completedDays;
    int progress;
    int missingEvents;
    int weeks;
    int repeatedDays;
    Date startDate;

    /**git
     * Starts  displaying all the habit statistics
     *
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_stats);

        passedHabit = data.getPassedHabit();

        if (passedHabit != null){      // use this for the real app

            habitEvents = data.getHabitEvents(passedHabit);


            // get the first day of the habit

           startDate = passedHabit.getStartDate();
            /* By using a search for the earliest logged event
            for (HabitEvent event : habitEvents) {
                if (event.getDay() != null && startDate.compareTo(event.getDay()) < 0) {
                    startDate = event.getDay();
                }
            }
            */

            // startDate = passedHabit.getStartDate();


            // check to see how many habit events we should have
            repeatedDays = passedHabit.getRepeatedDays().size(); // get number of days per week that we repeat

            // get the number of weeks we are expecting
            weeks = Weeks.weeksBetween(new DateTime(startDate), new DateTime()).getWeeks();
            if (weeks < 1) {
                weeks = 1;
            }

            // calculate the needed info
            totalExpectedDays = repeatedDays * weeks;               // expected number of days we should have habit events
            completedDays = habitEvents.size();                     // number of habit events we have

            // make sure we don't try to divide by zero
            if (totalExpectedDays == 0 && completedDays == 0) {
                progress = 0;
            } else if (totalExpectedDays == 0 && completedDays > 0) {
                progress = 100;
            } else {
                progress = (completedDays * 100) / totalExpectedDays;
            }

            missingEvents = totalExpectedDays - completedDays;      // number of missing habit events

            // check to make sure the stats are valid
            if (progress > 100){                                    // make sure we only have up to 100%
                progress = 100;
            }
            if (missingEvents < 0) {                                // make sure if user has extra habit events
                missingEvents = 0;                                  // we don't get negative values
            }

            // fill in the habit data
            // give completed habits the number of habit events
            completedHabits = (TextView) findViewById(R.id.completed_value);
            completedHabits.setText(String.valueOf(completedDays));

            // give the missed habits the number of calculate missed habit events
            missedHabits = (TextView) findViewById(R.id.missed_value);
            missedHabits.setText(String.valueOf(missingEvents));

            // fill in the progress percentage text view
            progressText = (TextView) findViewById(R.id.progressLevel);
            progressText.setText(String.valueOf(progress) + "%");

            // give the progress the bar the calculated progress level
            habitProgress = (ProgressBar) findViewById(R.id.habitStatsProgressBar);
            habitProgress.setProgress(progress);

        } else {

            // shows everything with default blank settings

            completedHabits = (TextView) findViewById(R.id.completed_value);
            completedHabits.setText("0");

            missedHabits = (TextView) findViewById(R.id.missed_value);
            missedHabits.setText("0");

            progressText = (TextView) findViewById(R.id.progressLevel);
            progressText.setText("0%");

            habitProgress = (ProgressBar) findViewById(R.id.habitStatsProgressBar);
            habitProgress.setProgress(0);


        }
    }
}
