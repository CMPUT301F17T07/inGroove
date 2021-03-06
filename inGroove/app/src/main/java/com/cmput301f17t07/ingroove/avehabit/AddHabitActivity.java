package com.cmput301f17t07.ingroove.avehabit;

import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DatePickerFragment;
import com.cmput301f17t07.ingroove.Model.Day;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * [Boundary Class]
 * Activity to allow the user to add a new habit
 *
 * @see Habit
 * @see DataManagerAPI
 */
public class AddHabitActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private final DataManagerAPI data = DataManager.getInstance();

    // Interface variables
    private CheckBox mon;
    private CheckBox tues;
    private CheckBox wed;
    private CheckBox thur;
    private CheckBox fri;
    private CheckBox sat;
    private CheckBox sun;
    private Button save_button;
    private Button cancel_button;
    private Button date_pick_button;
    private TextView date_text;

    // Temporary Date storage while the user has not yet chosen a date
    private Date start_date;

    private EditText habit_name;
    private EditText habit_comment;

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
        date_text = (TextView) findViewById(R.id.add_date_text);

        // Set up a default date
        start_date = new Date();
        SimpleDateFormat s_date_format = new SimpleDateFormat("dd MMM yyyy");
        date_text.setText(s_date_format.format(start_date));

        // Get the buttons to add on click listeners
        save_button = (Button) findViewById(R.id.add_save_btn);
        cancel_button = (Button) findViewById(R.id.add_cancel_btn);
        date_pick_button = (Button) findViewById(R.id.add_date_pick_btn);

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
        date_pick_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // The date picker fragment has returned a date
        start_date = new Date(year, month, day);
        SimpleDateFormat s_date_format = new SimpleDateFormat("dd MMM yyyy");
        date_text.setText(s_date_format.format(start_date));

    }

    /**
     * Saves the habit to storage through the DataManager
     *
     * @return true if successful
     * @see DataManagerAPI
     */
    private boolean saveHabit(){
        // @TODO boundary check the text inputs
        String name = habit_name.getText().toString();
        String comment = habit_comment.getText().toString();

        Boolean string_warn = false;
        if(name == ""){
            Toast toast = Toast.makeText(this,
                    "You cannot have a habit without a name", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        } else if (name.length() > 20){
            name = name.substring(0, 19);
            string_warn = true;
        } else if (comment.length() > 20){
            comment = comment.substring(0, 19);
            string_warn = true;
        }

        if (string_warn){
            Toast toast = Toast.makeText(this,
                    "Max string lengths are 20, cutting...", Toast.LENGTH_SHORT);
            toast.show();
        }

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
        Habit new_habit = new Habit(name, comment, days, start_date);

        // send the habit to be saved
        data.addHabit(new_habit);

        return true;

    }
}
