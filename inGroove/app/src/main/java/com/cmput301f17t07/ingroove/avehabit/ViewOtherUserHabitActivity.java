package com.cmput301f17t07.ingroove.avehabit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.HabitEventsActivity;
import com.cmput301f17t07.ingroove.HabitStats.HabitStatsActivity;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.ViewHabitEvent.ViewHabitEventActivity;
import com.cmput301f17t07.ingroove.ViewHabitEvent.ViewOtherUsersHabitEventActivity;

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

public class ViewOtherUserHabitActivity extends AppCompatActivity {

    DataManagerAPI data = DataManager.getInstance();


    Habit passed_habit = null;
    ArrayList<HabitEvent> habitEventsList;


    // Adaptor for the habit events list view
    ArrayList<String> hEL_Strings;
    ArrayAdapter<String> hEL_adaptor;

    // Interface variables

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
        setContentView(R.layout.activity_view_other_user_habit);

        passed_habit = data.getPassedHabit();


        // Link up the text views
        habit_name = (TextView) findViewById(R.id.view_habit_name);
        habit_comment = (TextView) findViewById(R.id.view_habit_comment);
        habit_start_date = (TextView) findViewById(R.id.view_habit_start_date_text);

        // Set the text views
        setTextFields();

        // Get a hook for the habit event list
        habit_events = (ListView) findViewById(R.id.view_habit_events);
        hEL_Strings = new ArrayList<>();
        hEL_adaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, hEL_Strings);
        habit_events.setAdapter(hEL_adaptor);

        data.findHabitEvents(passed_habit, new AsyncResultHandler<HabitEvent>() {
            @Override
            public void handleResult(ArrayList<HabitEvent> result) {
                hEL_Strings = new ArrayList<>();
                hEL_adaptor.clear();
                habitEventsList = result;

                for (HabitEvent a : habitEventsList) {
                    hEL_Strings.add(a.getName());
                }
                hEL_adaptor.addAll(hEL_Strings);
                hEL_adaptor.notifyDataSetChanged();
            }
        });

        // Handle clicks on habit event items in the list
        habit_events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                data.setPassedHabitEvent(habitEventsList.get(position));
                Intent upcomingIntent = new Intent(v.getContext(), ViewOtherUsersHabitEventActivity.class);
                startActivity(upcomingIntent);
            }
        });

        // Get the buttons to add on click listeners
//        stats_button = (Button) findViewById(R.id.view_habit_stats_btn);

        // add on click listeners
//        stats_button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), HabitStatsActivity.class);
//                data.setPassedHabit(passed_habit);
//                getApplicationContext().startActivity(intent);
//            }
//        });


    }


    private void setTextFields() {
        habit_name.setText(passed_habit.getName());
        habit_comment.setText(passed_habit.getComment());
        habitEventsList = data.getHabitEvents(passed_habit);
        SimpleDateFormat s_date_format = new SimpleDateFormat("dd MMM yyyy");
        habit_start_date.setText(s_date_format.format(passed_habit.getStartDate()));
    }

//    @Override
//    public void onStart(){
//        super.onStart();
//        // hook up the habit events list to an adaptor
//        hEL_Strings = new ArrayList<String>();
//        if (habitEventsList != null) {
//            for (HabitEvent a : habitEventsList) {
//                hEL_Strings.add(a.getName());
//            }
//            hEL_adaptor = new ArrayAdapter<String>
//                    (this, android.R.layout.simple_list_item_1, hEL_Strings);
//
//            habit_events.setAdapter(hEL_adaptor);
//        }
//    }
}
