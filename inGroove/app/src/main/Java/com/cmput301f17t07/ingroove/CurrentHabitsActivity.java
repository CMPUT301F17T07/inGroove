
package com.cmput301f17t07.ingroove;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.Day;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.ViewHabitEvent.ViewHabitEventActivity;
import com.cmput301f17t07.ingroove.avehabit.AddHabitActivity;
import com.cmput301f17t07.ingroove.avehabit.ViewHabitActivity;
import com.cmput301f17t07.ingroove.navDrawer.NavigationDrawerActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentHabitsActivity extends NavigationDrawerActivity{

    //Initilize variables.
    DataManagerAPI ServerCommunicator9000 = DataManager.getInstance();

    private GridView habitViewer;
    ArrayAdapter<String> gridViewArrayAdapter;

    private Button b_upcoming;
    private Button b_finished;
    private Button b_listHabits;
    private FloatingActionButton b_addHabit;

    //These two lists are used to hold the users habits and habit events.
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
        HabitEventHolder = new ArrayList<HabitEvent>();

        //Initilize the GridView
        habitViewer = (GridView) findViewById(R.id.HabitViewer);

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
        b_listHabits = (Button) findViewById(R.id.ListHabitsButton);

        b_upcoming.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Habit Manager: Grab Future Habits
                upcomingButtonClick();
            }
        });

        b_finished.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Habit Manager: Grab Past Habit events
                finishedButtonClick();
            }
        });

        b_addHabit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent upcomingIntent = new Intent(v.getContext(), AddHabitActivity.class);
                startActivityForResult(upcomingIntent, 0);
            }
        });

        b_listHabits.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                habitsLoaded = true;
                HabitHolder = ServerCommunicator9000.getHabit(new User("T-Rex Joe", "trexjoe@hotmail.com"));
                PopulateGridView_Habits(HabitHolder);
            }
        });
    }

    /**
     * This method handles the on click event for the gridview.
     * It behaves differently based on whether the user is looking at habits or habit events.
     * @param position: The position in either the HabitHolder or HabitEventHolder array list we are looking in.
     * @param v: The view needed to start a new activity.
     */
    private void gridViewOnClickEvent(View v, int position)
    {
        if(habitsLoaded) {
            String habitName = "";
            Object ob = habitViewer.getAdapter().getItem(position);
            if(ob instanceof String)
            {
                habitName = ((String)ob);
            }
            else if (ob instanceof Map)
            {
                habitName = ((Map<String, String>)ob).get("title").toString();
            }
            else
                return;
            Intent upcomingIntent = new Intent(v.getContext(), ViewHabitActivity.class);
            ServerCommunicator9000.setPassedHabit(findHabit(habitName));
            startActivityForResult(upcomingIntent, 0);
        }
        else {
            Intent upcomingIntent = new Intent(v.getContext(), ViewHabitEventActivity.class);
            ServerCommunicator9000.setPassedHabitEvent(HabitEventHolder.get(position));
            startActivityForResult(upcomingIntent, 0);
            habitsLoaded = true;
        }
    }

    /**
     *This method is used to find the habit given the habits name.
     */
    private Habit findHabit(String name)
    {
        for (int i = 0; i < HabitHolder.size(); i++)
        {
            if(HabitHolder.get(i).getName().equals(name)) {
                return HabitHolder.get(i);
            }
        }
        return null;
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
        HabitEventHolder = new ArrayList<>();
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
        PopulateGridView_HabitEvents(HabitEventHolder);
    }

    /**
     *This method sorts the users habits so they can view them chronologically.
     */
    private void upcomingButtonClick() {
        habitsLoaded = true;
        habitViewer.setAdapter(sortHabitsByDay(HabitHolder));
    }

    /**
     * A selection sort for the habits based on dates.  Used for the upcoming button.
     * Creates a list of habits showing what day of the week they need to be done, chonologically.
     * It only shows the habits that need to be compeleted in the following week.
     * Returns an adapter that can be used to update a listview/gridview.
     * @param HabitList
     */
    private SimpleAdapter sortHabitsByDay(ArrayList<Habit> HabitList)
    {
        //Initilize variables
        ArrayList<String> Monday = new ArrayList<String>();
        ArrayList<String> Tuesday = new ArrayList<String>();
        ArrayList<String> Wednesday = new ArrayList<String>();
        ArrayList<String> Thursday = new ArrayList<String>();
        ArrayList<String> Friday = new ArrayList<String>();
        ArrayList<String> Saturday = new ArrayList<String>();
        ArrayList<String> Sunday = new ArrayList<String>();
        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        String today = format.format(new Date()).toLowerCase();
        int dayNumber, count;
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        //Sort the habits into the seven days of the week.
        for (int i = 0; i < HabitList.size(); i++) {
            ArrayList<Day> days = HabitList.get(i).getRepeatedDays();
            for (int j = 0; j < days.size(); j++) {
                switch (days.get(j).name().toLowerCase())
                {
                    case "monday":
                        Monday.add(HabitList.get(i).getName());
                        break;
                    case "tuesday":
                        Tuesday.add(HabitList.get(i).getName());
                        break;
                    case "wednesday":
                        Wednesday.add(HabitList.get(i).getName());
                        break;
                    case "thursday":
                        Thursday.add(HabitList.get(i).getName());
                        break;
                    case "friday":
                        Friday.add(HabitList.get(i).getName());
                        break;
                    case "saturday":
                        Saturday.add(HabitList.get(i).getName());
                        break;
                    case "sunday":
                        Sunday.add(HabitList.get(i).getName());
                        break;
                }
            }
        }

        //Find out what today is and map it to dayNumber.
        dayNumber = 0;
        switch (today)
        {
            case "monday": dayNumber = 0; break;
            case "tuesday": dayNumber = 1; break;
            case "wednesday": dayNumber = 2; break;
            case "thursday": dayNumber = 3; break;
            case "friday": dayNumber = 4; break;
            case "saturday": dayNumber = 5; break;
            case "sunday": dayNumber = 6; break;
        }

        //Create an adapter that will display the sorted dates.
        count = 0;
        while(count < 7)
        {
            ArrayList<String> Current = new ArrayList<String>();
            String day = "";
            switch (dayNumber)
            {
                case 0: Current = Monday; day = "Monday"; break;
                case 1: Current = Tuesday; day = "Tuesday"; break;
                case 2: Current = Wednesday; day = "Wednesday"; break;
                case 3: Current = Thursday; day = "Thursday"; break;
                case 4: Current = Friday; day = "Friday"; break;
                case 5: Current = Saturday; day = "Saturday"; break;
                case 6: Current = Sunday; day = "Sunday"; break;
            }
            if(today.equals(day.toLowerCase()))
                day = "today";
            for (String habit : Current) {
                Map<String, String> datum = new HashMap<String, String>(2);
                datum.put("title", habit);
                datum.put("date", day);
                data.add(datum);
            }
            count++;
            dayNumber++;
            if(dayNumber >= 7)
                dayNumber = 0;
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"title", "date"},
                new int[] {android.R.id.text1,
                        android.R.id.text2});
        return adapter;
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
        fillGridView(gv_GridItems);
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
        fillGridView(gv_GridItems);
    }

    /**
     * This method populates the gridview with a list of passed in strings.
     *
     * @param gridItems: A list of strings that will be used to populate the gridview.
     */
    private void fillGridView(ArrayList<String> gridItems) {
        gridViewArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, gridItems);
        habitViewer.setAdapter(gridViewArrayAdapter);
    }

    /**
     * Override the onStart method so that it will re-populate the gridview.
     */
    @Override
    public void onStart(){
        super.onStart();

        HabitHolder = ServerCommunicator9000.getHabit(new User("T-Rex Joe", "trexjoe@hotmail.com"));
        PopulateGridView_Habits(HabitHolder);
    }
}
