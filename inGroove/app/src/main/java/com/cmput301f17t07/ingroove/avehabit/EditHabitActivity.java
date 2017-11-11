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

public class EditHabitActivity extends AppCompatActivity {

    DataManagerAPI data = DataManager.getInstance();

    // Information for getting habits that are passed to this activity
    public static String habit_key = "habit_to_edit";
    Habit passed_habit = null;

    // Adaptor for the habit events list view
    ArrayList<String> hEL_Strings;
    ArrayAdapter<String> hEL_adaptor;

    // Interface variables
    CheckBox mon;
    CheckBox tues;
    CheckBox wed;
    CheckBox thur;
    CheckBox fri;
    CheckBox sat;
    CheckBox sun;

    Button save_button;

    EditText habit_name;
    EditText habit_comment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null){
            passed_habit = (Habit) bundle.getSerializable(habit_key);
        } else {
            // We must have a habit to edit, we might crash otherwise
            finish();
        }

        // Link up the CheckBoxes
        mon = (CheckBox) findViewById(R.id.edit_habit_day_mon);
        tues = (CheckBox) findViewById(R.id.edit_habit_day_tues);
        wed = (CheckBox) findViewById(R.id.edit_habit_day_wed);
        thur = (CheckBox) findViewById(R.id.edit_habit_day_thur);
        fri = (CheckBox) findViewById(R.id.edit_habit_day_fri);
        sat = (CheckBox) findViewById(R.id.edit_habit_day_sat);
        sun = (CheckBox) findViewById(R.id.edit_habit_day_sun);

        // Link up the text views
        habit_name = (EditText) findViewById(R.id.edit_habit_name);
        habit_comment = (EditText) findViewById(R.id.edit_habit_comment);


        // editing a habit, so fill in the values to the different inputs
        habit_name.setText(passed_habit.getName());
        habit_comment.setText(passed_habit.getComment());

        if (passed_habit.getRepeatedDays().contains(Day.MONDAY)){
            mon.setChecked(true);
        }
        if (passed_habit.getRepeatedDays().contains(Day.TUESDAY)){
            tues.setChecked(true);
        }
        if (passed_habit.getRepeatedDays().contains(Day.WEDNESDAY)){
            wed.setChecked(true);
        }
        if (passed_habit.getRepeatedDays().contains(Day.THURSDAY)){
            thur.setChecked(true);
        }
        if (passed_habit.getRepeatedDays().contains(Day.FRIDAY)){
            fri.setChecked(true);
        }
        if (passed_habit.getRepeatedDays().contains(Day.SATURDAY)){
            sat.setChecked(true);
        }
        if (passed_habit.getRepeatedDays().contains(Day.SUNDAY)){
            sun.setChecked(true);
        }

        // Get the button to add on click listeners
        save_button = (Button) findViewById(R.id.edit_save_btn);

        // add on click listeners
        save_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveHabit();
                finish();
            }
        });


    }

    private void saveHabit(){

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

        // send the habit to overwrite the old habit
        data.editHabit(passed_habit, new_habit);

    }
}
