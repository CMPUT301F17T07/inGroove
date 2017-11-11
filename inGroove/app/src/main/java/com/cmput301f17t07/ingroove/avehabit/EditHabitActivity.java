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
    ArrayList<HabitEvent> habitEventsList;

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

    ListView habit_events;



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

        // Link up the text views
        habit_name = (EditText) findViewById(R.id.ave_habit_name);
        habit_comment = (EditText) findViewById(R.id.ave_habit_comment);


        // editing a habit
        habit_name.setText(passed_habit.getName());
        habit_comment.setText(passed_habit.getComment());

        habitEventsList = data.getHabitEvents(passed_habit);


        // Get a hook for the habit event list
        habit_events = (ListView) findViewById(R.id.ave_habit_events);

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

        // Get the button to add on click listeners
        save_button = (Button) findViewById(R.id.ave_save_btn);

        // add on click listeners
        save_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveHabit();
                finish();
            }
        });


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
