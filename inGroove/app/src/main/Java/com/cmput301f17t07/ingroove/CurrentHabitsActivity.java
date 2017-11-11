
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
import com.cmput301f17t07.ingroove.Model.HabitEvent;
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
    private ArrayList<HabitEvent> HabitEventHolder;
    //This bool is used to determine if we edit Habits or Habit Events.
    private boolean habitsLoaded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_habits);
        super.onCreateDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        habitsLoaded = true;

        //From the HabitManager, get a list of todays habits
        //Ex: HabitHolder = HabitManager.getToday(userID);
        //This code only uses the MockDataManager.  So it will need to be changed later.  In the meantime it should populate HabitHolder with some habits that will be displayed.
        HabitHolder = ServerCommunicator9000.getHabit(new User("T-Rex Joe", "trexjoe@hotmail.com"));
        HabitEventHolder = new ArrayList<HabitEvent>();

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
                gridViewOnClickEvent(v, position);
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
                finishedButtonClick();
            }
        });

        b_addHabit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    Intent upcomingIntent = new Intent(v.getContext(), AddViewEditHabitActivity.class);
                    startActivityForResult(upcomingIntent, 0);
            }
        });
    }

    /**
     * This method handles the on click event for the gridview.
     * It behaves differently based on wheter the user is looking at habits or habit events.
     * @param position: The position in either the HabitHolder or HabitEventHolder array list we are looking in.
     * @param v: The view needed to start a new activity.
     */
    private void gridViewOnClickEvent(View v, int position)
    {
        if(habitsLoaded) {
            Intent upcomingIntent = new Intent(v.getContext(), AddViewEditHabitActivity.class);
            upcomingIntent.putExtra(AddViewEditHabitActivity.habit_key, HabitHolder.get(position));
            startActivityForResult(upcomingIntent, 0);
        }
        else {
            Intent upcomingIntent = new Intent(v.getContext(), HabitEventsActivity.class);
            upcomingIntent.putExtra(HabitEventsActivity.habitevent_key, HabitEventHolder.get(position));
            //Find the parent Habit of the habitID
            for (int i = 0; i < HabitHolder.size(); i++)
            {
                if(HabitHolder.get(i).getHabitID() == HabitEventHolder.get(position).getHabitID()) {
                    upcomingIntent.putExtra(HabitEventsActivity.habit_key, HabitHolder.get(i));
                    break;
                }
            }

            startActivityForResult(upcomingIntent, 0);
            habitsLoaded = true;
        }
    }

    /**
     * This method is used when the Finished button is clicked.
     * at the moment it will grab the first 20 habit events related to habits and display them
     * onto the gridview.
     */
    private void finishedButtonClick()
    {
        //ArrayList<HabitEvent> he_list = ServerCommunicator9000.getHabitEvents();
        habitsLoaded = false;
        int i = 1;
        for (Habit a : HabitHolder) {
            ArrayList<HabitEvent> he_list = ServerCommunicator9000.getHabitEvents(a);
            if (he_list.size() > 0) {
                for (int j = 0; j < he_list.size(); j++)
                {
                    i++;
                    HabitEventHolder.add( he_list.get(j) );
                    if (i > 20)
                        break;
                }
            }
            if (i > 20)
                break;
        }
        if(HabitEventHolder.size() == 0)
            return;
        PopulateGridView_HabitEvents(HabitEventHolder);
    }

    /**
     * This method is used to populate the gridview with habits.
     * Afterwards, the user can click on the habits to edit them.
     *
     * @param HabitList: A list of habits that will be added to the gridview.
     */
    private void PopulateGridView_Habits(ArrayList<Habit> HabitList) {
        ArrayList<String> gv_GridItems = new ArrayList<String>();
        for (int i = 0; i < HabitList.size(); i++) {
            gv_GridItems.add(HabitList.get(i).getName());
        }
        fillGrivView(gv_GridItems);
    }

    /**
     * This method is used to populate the gridview with habit events.
     *
     * @param HabitEventList: A list of habit events that will be added to the gridview.
     */
    private void PopulateGridView_HabitEvents(ArrayList<HabitEvent> HabitEventList) {
        ArrayList<String> gv_GridItems = new ArrayList<String>();
        for (int i = 0; i < HabitEventList.size(); i++) {
            if(HabitEventList.get(i).getName() != null && !HabitEventList.get(i).getName().isEmpty())
                gv_GridItems.add(HabitEventList.get(i).getName());
            else
                gv_GridItems.add("Nameless HabitEvent");
        }
        fillGrivView(gv_GridItems);
    }

    /**
     * This method populates the gridview with a list of passed in strings.
     *
     * @param gridItems: A list of strings that will be used to populate the gridview.
     */
    private void fillGrivView(ArrayList<String> gridItems) {
        gridViewArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, gridItems);
        habitViewer.setAdapter(gridViewArrayAdapter);
    }

    @Override
    public void onStart(){
        super.onStart();
        if(HabitHolder.size() > 0)
            PopulateGridView_Habits(HabitHolder);
    }
}
