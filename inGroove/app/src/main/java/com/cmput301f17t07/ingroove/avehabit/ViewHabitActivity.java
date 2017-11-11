package com.cmput301f17t07.ingroove.avehabit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.HabitStats.HabitStatsActivity;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.ViewHabitEvent.ViewHabitEventActivity;

import java.util.ArrayList;
import java.util.Date;

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

    ListView habit_events;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null){
            passed_habit = (Habit) bundle.getSerializable(habit_to_view_key);
        }

        // Link up the text views
        habit_name = (TextView) findViewById(R.id.view_habit_name);
        habit_comment = (TextView) findViewById(R.id.view_habit_comment);

        // Populate them accordingly
        if (passed_habit == null){
            // adding a new habit
            habitEventsList = new ArrayList<HabitEvent>();
        } else {
            // editing a habit
            habit_name.setText(passed_habit.getName());
            habit_comment.setText(passed_habit.getComment());
            habitEventsList = data.getHabitEvents(passed_habit);
        }

        // Get a hook for the habit event list
        habit_events = (ListView) findViewById(R.id.view_habit_events);

        // Handle clicks on habit event items in the list
        habit_events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // @TODO change this to an edit view, or leave as just view?
                Intent upcomingIntent = new Intent(v.getContext(), ViewHabitEventActivity.class);
                upcomingIntent.putExtra(ViewHabitEventActivity.he_key, habitEventsList.get(position));
                startActivityForResult(upcomingIntent, 0);
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
                // @TODO save current habit changes?
                // @TODO pass this onto the create habit event activity instead
                // Intent intent = new Intent(getApplicationContext(), ViewHabitEventActivity.class);
                // getApplicationContext().startActivity(intent);
                HabitEvent event = new HabitEvent("Test Event", new Date());

                // This temporary code is so that we can see habit events being added
                // @TODO delete if this works since the add habit event view should be taking care of it
                data.addHabitEvent(passed_habit, event);

                // This temporary code just shows that the adaptor is working
                habitEventsList = data.getHabitEvents(passed_habit);
                hEL_Strings.clear();
                for (HabitEvent a : habitEventsList) {
                    hEL_Strings.add(a.getName());
                }
                hEL_adaptor.notifyDataSetChanged();

            }
        });
        stats_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HabitStatsActivity.class);
                getApplicationContext().startActivity(intent);
            }
        });
        edit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditHabitActivity.class);
                intent.putExtra(EditHabitActivity.habit_key, passed_habit);
                startActivityForResult(intent, 1);
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
        if (requestCode == 1 && resultCode == RESULT_OK){
            Habit new_habit = (Habit) data.getSerializableExtra(edited_habit_key);
            setFieldsTo(new_habit);
        }
    }

    private void setFieldsTo(Habit habit) {
        
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
    private void deleteHabit() {


        if (passed_habit != null) {
            Log.d("--- REMOVING ---", " Habit named: " + passed_habit.getName());
            data.removeHabit(passed_habit);
            finish();
        }
    }
}
