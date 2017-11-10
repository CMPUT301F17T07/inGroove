package com.cmput301f17t07.ingroove.HabitStats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.R;

public class HabitStatsActivity extends AppCompatActivity {

    TextView completedHabits;
    TextView missedHabits;

    ProgressBar habitProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_stats);

        Habit passedHabit;

        Log.w("TEST TEST TEST","started onCreate");

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null){
            //passedHabit = (Habit) bundle.getSerializable(key:"display_stats_for_habit");

            // unbundle or something
            Log.w("TEST TEST TEST","passed bundle != null");

            // fill in the habit data
            // TextView habitTitle = (TextView) findViewById(R.id.habitStatsTitle);
            // habitTitle.setText("super awesome habit to do");

            completedHabits = (TextView) findViewById(R.id.completed_value);
            completedHabits.setText("100");

            missedHabits = (TextView) findViewById(R.id.missed_value);
            missedHabits.setText("42");

            ProgressBar habitProgress = (ProgressBar) findViewById(R.id.habitStatsProgressBar);
            habitProgress.setProgress(50);
        }
        else {
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
        }
    }
}
