package com.cmput301f17t07.ingroove.HabitStats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.MockDataManager;
import com.cmput301f17t07.ingroove.Model.Day;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HabitStatsActivity extends AppCompatActivity {

    DataManagerAPI data = new MockDataManager().getInstance();

    TextView completedHabits;
    TextView missedHabits;

    ProgressBar habitProgress;

    CalendarView habitCalendar;

    ArrayList<HabitEvent> habitEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_stats);

        Habit passedHabit;

        Bundle bundle = this.getIntent().getExtras();


        if (bundle != null){
        //if (bundle == null) {

            // this isn't working properly yet, but it will be
            // but right noe the other section in else is running anyway, so it's okayish and doesn't break right away
            
            //passedHabit = (Habit) bundle.getSerializable("display_stats_for_habit");

            // make work for mock data manager
            data.addUser("test");
            User user = data.getUser();
            ArrayList<Habit> habits = data.getHabit(user);
            passedHabit = habits.get(0);


            // unbundle or something
            Log.w("TEST TEST TEST","passed bundle != null");

            // fill in the habit data
            // TextView habitTitle = (TextView) findViewById(R.id.habitStatsTitle);
            // habitTitle.setText("super awesome habit to do");

            habitEvents = data.getHabitEvents(passedHabit);

            // first we get all the data we need
            // habit doesn't have a start day, so we parse through habit events
            // find the first start day, use as begining
            // look at repeat cycle and see how many we habit events we should have between
            // then and now
            // difference is missed
            // use this difference to get the progress bar amount

            // get the first day of the habit
            Date startDate = new Date();
            for (HabitEvent event : habitEvents) {
                if (event.getDay() != null && startDate.compareTo(event.getDay()) < 0) {
                    startDate = event.getDay();
                }
            }
            Log.w("TEST TEST TEST", "the min day is " + String.valueOf(startDate));

            // check to see how many habit events we should have
            int repeatedDays = passedHabit.getRepeatedDays().size(); // get number of days per week that we repeat
            int weeks = 0;
            Calendar cal = new GregorianCalendar();
            while (cal.getTime().before(new Date())) {
                cal.add(Calendar.WEEK_OF_YEAR, 1);
                weeks++;
            }
            int totalPossibleDays = repeatedDays * weeks;
            int completedDays = habitEvents.size();

            // then we set the data equal to what we need
            // give completed habits the number of habit events
            completedHabits = (TextView) findViewById(R.id.completed_value);
            completedHabits.setText(String.valueOf(completedDays));

            missedHabits = (TextView) findViewById(R.id.missed_value);
            missedHabits.setText(String.valueOf(totalPossibleDays - completedDays));

            habitProgress = (ProgressBar) findViewById(R.id.habitStatsProgressBar);
            habitProgress.setProgress((completedDays / totalPossibleDays) * 100);

            habitCalendar = (CalendarView) findViewById(R.id.habitStatsCalendarView);
            habitCalendar.setFirstDayOfWeek(1);

        } else {
            // show blank settings

            Log.w("TEST TEST TEST","went to else");


            // want to include a title for this, but for some reason it's not working
            // to display right now, will fix later
            // TextView habitTitle = (TextView) findViewById(R.id.habitStatsTitle);
            // habitTitle.setText("Habit Stats Unavailable");

            completedHabits = (TextView) findViewById(R.id.completed_value);
            completedHabits.setText("0");

            missedHabits = (TextView) findViewById(R.id.missed_value);
            missedHabits.setText("0");

            habitProgress = (ProgressBar) findViewById(R.id.habitStatsProgressBar);
            habitProgress.setProgress(0);

            habitCalendar = (CalendarView) findViewById(R.id.habitStatsCalendarView);
            habitCalendar.setFirstDayOfWeek(1);
        }
    }
}
