package com.cmput301f17t07.ingroove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.HabitEvent;
import com.cmput301f17t07.ingroove.Model.User;

import java.util.ArrayList;

/**
 * This activity is used to test the data manager.
 */
public class DataManagerTestingActivity extends AppCompatActivity  {

    DataManagerAPI data = DataManager.getInstance();

    EditText topET;
    EditText addET;
    TextView resultOneTV;
    TextView resultTwoTV;
    Button searchBtn;
    Button addBtn;

    private ArrayList<User> res = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_manager_testing);

        topET = (EditText) findViewById(R.id.topET);
        addET = (EditText) findViewById(R.id.addET);

        resultOneTV = (TextView) findViewById(R.id.resultOneTV);
        resultTwoTV = (TextView) findViewById(R.id.resultTwoTV);
        searchBtn = (Button) findViewById(R.id.saveBtn);
        addBtn = (Button) findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = addET.getText().toString();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = topET.getText().toString();

            }
        });

        // Use anonymous classes to handle return data from ES queries like so:
        data.getFollowRequests(new AsyncResultHandler<User>() {
            @Override
            public void handleResult(ArrayList<User> result) {
                // When the Async. call finishes on the background thread
                // this method will be called, and "result" will contain
                // a list of the User objects that match the query.
                // Implement your logic to update the UI here.
            }
        });

        data.findHabits(null, new AsyncResultHandler<Habit>() {
            @Override
            public void handleResult(ArrayList<Habit> result) {
                // Another example, this time we are querying the server for Habits
            }
        });

        data.findHabitEvents((User) null, new AsyncResultHandler<HabitEvent>() {
            @Override
            public void handleResult(ArrayList<HabitEvent> result) {
                // And another, this time for HabitEvents
            }
        });

        // Note, you do not have to specify the data type in the anonymous class instantiation
        // but doing so will avoid having to check/cast the returned array items when updating
        // the UI--for example:
        data.findHabits(null, new AsyncResultHandler() {
            @Override
            public void handleResult(ArrayList result) {
                // But now you must check/cast the type of the objects being returned before you
                // can access their attributes--cleaner to do it like above, imo, since the return
                // type is guaranteed
            }
        });

    }




}
