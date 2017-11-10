package com.cmput301f17t07.ingroove.HabitStats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
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

import static com.cmput301f17t07.ingroove.Model.Day.FRIDAY;
import static com.cmput301f17t07.ingroove.Model.Day.SATURDAY;
import static com.cmput301f17t07.ingroove.Model.Day.THURSDAY;
import static com.cmput301f17t07.ingroove.Model.Day.TUESDAY;

public class HabitStatsActivity extends AppCompatActivity {

    DataManagerAPI data = DataManager.getInstance();
    //DataManagerAPI data = new MockDataManager().getInstance();

    TextView completedHabits;
    TextView missedHabits;

    ProgressBar habitProgress;

    CalendarView habitCalendar;

    ArrayList<HabitEvent> habitEvents;

    Habit passedHabit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_stats);

        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null){      // use this for the real app
        //if (bundle == null) {       // use this for testing with the mock data manager where things aren't getting passed

            // make work for mock data manager
            //data.addUser("test");
            //User user = data.getUser();
            //ArrayList<Habit> habits = data.getHabit(user);
            //passedHabit = habits.get(0);
            //ArrayList<Day> repeat = new ArrayList<Day>();
            //repeat.add(TUESDAY);
            //repeat.add(THURSDAY);
            //repeat.add(FRIDAY);
            //repeat.add(SATURDAY);
            //passedHabit.setRepeatedDays(repeat);
            // end whats needed for mock data manager

            // for the real data manager
            //passedHabit = (Habit) bundle.getSerializable("display_stats_for_habit");
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
            // TextView habitTitle = (TextView) findViewById(R.id.habitStatsTitle);
            // habitTitle.setText("super awesome habit to do");

            // give completed habits the number of habit events
            completedHabits = (TextView) findViewById(R.id.completed_value);
            completedHabits.setText(String.valueOf(completedDays));

            // give the missed habits the number of calculate missed habit events
            missedHabits = (TextView) findViewById(R.id.missed_value);
            missedHabits.setText(String.valueOf(totalPossibleDays - completedDays));

            // give the progress the bar the calculated progress level
            habitProgress = (ProgressBar) findViewById(R.id.habitStatsProgressBar);
            habitProgress.setProgress(progress);

            // view the needed calendar
            habitCalendar = (CalendarView) findViewById(R.id.habitStatsCalendarView);
            habitCalendar.setFirstDayOfWeek(1);

        } else {
            // show blank settings

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
