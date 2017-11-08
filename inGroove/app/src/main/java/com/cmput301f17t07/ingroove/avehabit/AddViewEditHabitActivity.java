package com.cmput301f17t07.ingroove.avehabit;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.MockDataManager;
import com.cmput301f17t07.ingroove.HabitEventsActivity;
import com.cmput301f17t07.ingroove.HabitStats.HabitStatsActivity;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.R;

public class AddViewEditHabitActivity extends AppCompatActivity {

    DataManagerAPI data = new MockDataManager();

    Button log_button;
    Button stats_button;
    Button interval_button;
    Button save_button;
    Button del_button;

    EditText habit_name;
    EditText habit_comment;

    Habit passed_habit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_view_edit_habit);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null){
            passed_habit = (Habit) bundle.getSerializable("habit_to_edit");
        }

        if (passed_habit == null){
            // @TODO adding a new habit
        } else {
            // @TODO editing a current habit
        }

        // Link up the text views
        habit_name = (EditText) findViewById(R.id.ave_habit_name);
        habit_comment = (EditText) findViewById(R.id.ave_habit_comment);

        // Get the buttons to add on click listeners
        log_button = (Button) findViewById(R.id.ave_log_event_btn);
        stats_button = (Button) findViewById(R.id.ave_stats_btn);
        interval_button = (Button) findViewById(R.id.ave_interval_btn);
        save_button = (Button) findViewById(R.id.ave_save_btn);
        del_button = (Button) findViewById(R.id.ave_del_btn);

        // add on click listeners
        log_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // @TODO save current habit changes?
                Intent intent = new Intent(getApplicationContext(), HabitEventsActivity.class);
                getApplicationContext().startActivity(intent);
            }
        });
        stats_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // @TODO save current habit changes?
                Intent intent = new Intent(getApplicationContext(), HabitStatsActivity.class);
                getApplicationContext().startActivity(intent);
            }
        });
        interval_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // @TODO we have no activity or action for this
            }
        });
        save_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveHabit();
            }
        });
        del_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // @TODO delete
            }
        });


    }


    private void saveHabit(){

        String name = habit_name.getText().toString();
        String comment = habit_comment.getText().toString();
        // create a new habit object
        Habit new_habit = new Habit(name, comment);

        //
        if (passed_habit == null){
            // create a new habit, then set ourselves to edit this habit later
            data.addHabit(new_habit);
            passed_habit = new_habit;
        } else {
            data.editHabit(passed_habit, new_habit);
        }

    }



}
