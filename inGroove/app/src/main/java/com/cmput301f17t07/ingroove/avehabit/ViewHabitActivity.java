package com.cmput301f17t07.ingroove.avehabit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.HabitEventsActivity;
import com.cmput301f17t07.ingroove.HabitStats.HabitStatsActivity;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.ViewHabitEvent.ViewHabitEventActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * [Boundary Class]
 * Activity to allow the user to view habits and their events
 *
 * @see HabitEvent
 * @see Habit
 * @see DataManagerAPI
 */

public class ViewHabitActivity extends AppCompatActivity {

    DataManagerAPI data = DataManager.getInstance();

    // Information for getting habits that are passed to this activity from it's parent
    public static String habit_to_view_key = "habit_to_edit";
    Habit passed_habit = null;
    ArrayList<HabitEvent> habitEventsList;

    // Information for getting habits from its children
    public static String edited_habit_key = "edited_habit";

    // Adaptor for the habit events list view
    ArrayList<String> hEL_Strings;
    ArrayAdapter<String> hEL_adaptor;

    // Interface variables
    Button log_button;
    Button stats_button;
    Button edit_button;
    Button del_button;

    TextView habit_name;
    TextView habit_comment;
    TextView habit_start_date;

    ListView habit_events;

    // Request codes
    int HANDLE_EVENT_EDITS = 0;
    int EDIT_HABIT = 1;
    int REQUEST_LOG_EVENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit);

        passed_habit = data.getPassedHabit();


        // Link up the text views
        habit_name = (TextView) findViewById(R.id.view_habit_name);
        habit_comment = (TextView) findViewById(R.id.view_habit_comment);
        habit_start_date = (TextView) findViewById(R.id.view_habit_start_date_text);

        // Set the text views
        setTextFields();

        // Get a hook for the habit event list
        habit_events = (ListView) findViewById(R.id.view_habit_events);

        // Handle clicks on habit event items in the list
        habit_events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent upcomingIntent = new Intent(v.getContext(), ViewHabitEventActivity.class);
                data.setPassedHabitEvent(habitEventsList.get(position));
                startActivityForResult(upcomingIntent, HANDLE_EVENT_EDITS);
            }
        });

        // Get the buttons to add on click listeners
        log_button = (Button) findViewById(R.id.view_habit_log_event_btn);
        stats_button = (Button) findViewById(R.id.view_habit_stats_btn);
        edit_button = (Button) findViewById(R.id.view_habit_edit_btn);
        del_button = (Button) findViewById(R.id.view_habit_del_btn);

        // add on click listeners
        log_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HabitEventsActivity.class);
                data.setPassedHabit(passed_habit);
                startActivityForResult(intent, REQUEST_LOG_EVENT);

            }
        });
        stats_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HabitStatsActivity.class);
                data.setPassedHabit(passed_habit);
                getApplicationContext().startActivity(intent);
            }
        });
        edit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditHabitActivity.class);
                data.setPassedHabit(passed_habit);
                startActivityForResult(intent, EDIT_HABIT);
            }
        });
        del_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (passed_habit != null){
                    data.removeHabit(passed_habit);
                }
                finish();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == EDIT_HABIT && resultCode == RESULT_OK){
            Habit new_habit = this.data.getPassedHabit();
            passed_habit = new_habit;
            setTextFields();
        } else if (requestCode == REQUEST_LOG_EVENT && resultCode == RESULT_OK){
            habitEventsList = this.data.getHabitEvents(passed_habit);
            hEL_Strings.clear();
            for (HabitEvent a : habitEventsList) {
                hEL_Strings.add(a.getName());
            }
        } else if (requestCode == HANDLE_EVENT_EDITS && resultCode == RESULT_OK){
            habitEventsList = this.data.getHabitEvents(passed_habit);
            hEL_Strings.clear();
            for (HabitEvent a : habitEventsList) {
                hEL_Strings.add(a.getName());
            }
        }
    }

    private void setTextFields() {
        habit_name.setText(passed_habit.getName());
        habit_comment.setText(passed_habit.getComment());
        habitEventsList = data.getHabitEvents(passed_habit);
        SimpleDateFormat s_date_format = new SimpleDateFormat("dd MMM yyyy");
        habit_start_date.setText(s_date_format.format(passed_habit.getStartDate()));
    }

    @Override
    public void onStart(){
        super.onStart();
        // hook up the habit events list to an adaptor
        hEL_Strings = new ArrayList<String>();
        if (habitEventsList != null) {
            for (HabitEvent a : habitEventsList) {
                hEL_Strings.add(a.getName());
            }
            hEL_adaptor = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_1, hEL_Strings);

            habit_events.setAdapter(hEL_adaptor);
        }
    }
}
