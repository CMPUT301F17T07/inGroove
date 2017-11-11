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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class HabitStatsActivity extends AppCompatActivity {

    DataManagerAPI data = DataManager.getInstance();

    TextView completedHabits;
    TextView missedHabits;
    TextView progressText;

    ProgressBar habitProgress;

    ArrayList<HabitEvent> habitEvents;

    Habit passedHabit;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_stats);

        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null){      // use this for the real app

            passedHabit = (Habit) bundle.getSerializable("display_stats_for_habit");
            habitEvents = data.getHabitEvents(passedHabit);

            // get the first day of the habit
            Date startDate = new Date();
            for (HabitEvent event : habitEvents) {
                if (event.getDay() != null && startDate.compareTo(event.getDay()) < 0) {
                    startDate = event.getDay();
                }
            }

            // check to see how many habit events we should have
            int repeatedDays = passedHabit.getRepeatedDays().size(); // get number of days per week that we repeat
            int weeks = 1;
            Calendar cal = new GregorianCalendar();
            while (cal.getTime().before(new Date())) {
                cal.add(Calendar.WEEK_OF_YEAR, 1);
                weeks++;
            }

            int totalPossibleDays = repeatedDays * weeks;
            int completedDays = habitEvents.size();
            int progress = (completedDays * 100) / totalPossibleDays;

            // fill in the habit data
            // give completed habits the number of habit events
            completedHabits = (TextView) findViewById(R.id.completed_value);
            completedHabits.setText(String.valueOf(completedDays));

            // give the missed habits the number of calculate missed habit events
            missedHabits = (TextView) findViewById(R.id.missed_value);
            missedHabits.setText(String.valueOf(totalPossibleDays - completedDays));

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
