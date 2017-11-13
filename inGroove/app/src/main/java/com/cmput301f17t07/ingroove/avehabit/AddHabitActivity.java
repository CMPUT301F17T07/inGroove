package com.cmput301f17t07.ingroove.avehabit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.HabitStats.HabitStatsActivity;
import com.cmput301f17t07.ingroove.Model.Day;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.ViewHabitEvent.ViewHabitEventActivity;

import java.util.ArrayList;
import java.util.Date;

public class AddHabitActivity extends AppCompatActivity {

    DataManagerAPI data = DataManager.getInstance();

    // Interface variables
    CheckBox mon;
    CheckBox tues;
    CheckBox wed;
    CheckBox thur;
    CheckBox fri;
    CheckBox sat;
    CheckBox sun;
    Button save_button;
    Button cancel_button;

    EditText habit_name;
    EditText habit_comment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        // Link up the CheckBoxes
        mon = (CheckBox) findViewById(R.id.add_habit_day_mon);
        tues = (CheckBox) findViewById(R.id.add_habit_day_tues);
        wed = (CheckBox) findViewById(R.id.add_habit_day_wed);
        thur = (CheckBox) findViewById(R.id.add_habit_day_thur);
        fri = (CheckBox) findViewById(R.id.add_habit_day_fri);
        sat = (CheckBox) findViewById(R.id.add_habit_day_sat);
        sun = (CheckBox) findViewById(R.id.add_habit_day_sun);

        // Link up the text views
        habit_name = (EditText) findViewById(R.id.add_habit_name);
        habit_comment = (EditText) findViewById(R.id.add_habit_comment);

        // Get the buttons to add on click listeners
        save_button = (Button) findViewById(R.id.add_save_btn);
        cancel_button = (Button) findViewById(R.id.add_cancel_btn);

        // add on click listeners
        save_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (saveHabit()){
                    // return to the previous activity
                    finish();
                }

            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // return to the previous activity
                finish();
            }
        });
    }

    private boolean saveHabit(){
        // @TODO boundary check the text inputs
        String name = habit_name.getText().toString();
        String comment = habit_comment.getText().toString();

        ArrayList<Day> days = new ArrayList<>();

        if (mon.isChecked()){
            days.add(Day.MONDAY);
        }
        if (tues.isChecked()){
            days.add(Day.TUESDAY);
        }
        if (wed.isChecked()){
            days.add(Day.WEDNESDAY);
        }
        if (thur.isChecked()){
            days.add(Day.THURSDAY);
        }
        if (fri.isChecked()){
            days.add(Day.FRIDAY);
        }
        if (sat.isChecked()){
            days.add(Day.SATURDAY);
        }
        if (sun.isChecked()){
            days.add(Day.SUNDAY);
        }

        // create a new habit object
        Habit new_habit = new Habit(name, comment, days);

        // send the habit to be saved
        data.addHabit(new_habit);

    }
}
