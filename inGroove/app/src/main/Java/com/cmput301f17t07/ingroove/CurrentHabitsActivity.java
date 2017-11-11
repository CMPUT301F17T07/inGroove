
package com.cmput301f17t07.ingroove;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.MockDataManager;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.avehabit.AddViewEditHabitActivity;
import com.cmput301f17t07.ingroove.navDrawer.NavigationDrawerActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CurrentHabitsActivity extends NavigationDrawerActivity{

    DataManagerAPI ServerCommunicator9000 = DataManager.getInstance();
    //DataManagerAPI ServerCommunicator9000 = new MockDataManager().getInstance();


    private GridView habitViewer;
    ArrayAdapter<String> gridViewArrayAdapter;

    private Button b_upcoming;
    private Button b_finished;
    private FloatingActionButton b_addHabit;

    private ArrayList<Habit> HabitHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_habits);
        super.onCreateDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //From the HabitManager, get a list of todays habits
        //Ex: HabitHolder = HabitManager.getToday(userID);
        //This code only uses the MockDataManager.  So it will need to be changed later.  In the meantime it should populate HabitHolder with some habits that will be displayed.
        HabitHolder = ServerCommunicator9000.getHabit(new User("T-Rex Joe", "trexjoe@hotmail.com"));

        //Populate the GridView
        habitViewer = (GridView) findViewById(R.id.HabitViewer);
        /*//Just a test, nothing to see here.
        String[] gvTest = new String[] {"This", "Is", "A", "Test", "0_0"};
        List<String> gv_GridItems = new ArrayList<String>(Arrays.asList(gvTest));
        ArrayAdapter<String> gridViewArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, gv_GridItems);
        */
        //Populate an array list with the name of each habit that was fetched.

        habitViewer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent upcomingIntent = new Intent(v.getContext(), AddViewEditHabitActivity.class);
                upcomingIntent.putExtra(AddViewEditHabitActivity.habit_key, HabitHolder.get(position));
                startActivityForResult(upcomingIntent, 0);
            }
        });

        //Initialize Buttons.  Create listener to handle on click events.
        b_upcoming = (Button) findViewById(R.id.Upcomingbutton);
        b_finished = (Button) findViewById(R.id.FinishedButton);
        b_addHabit = (FloatingActionButton) findViewById(R.id.AddHabitButton);

        b_upcoming.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Habit Manager: Grab Future Habits
            }
        });

        b_finished.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Habit Manager: Grab Past Habits
            }
        });

        b_addHabit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent upcomingIntent = new Intent(v.getContext(), AddViewEditHabitActivity.class);
                startActivityForResult(upcomingIntent, 0);
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        ArrayList<String> gv_GridItems = new ArrayList<String>();
        for (Habit a : HabitHolder) {
            gv_GridItems.add(a.getName());
        }
        gridViewArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, gv_GridItems);

        habitViewer.setAdapter(gridViewArrayAdapter);
    }
}
