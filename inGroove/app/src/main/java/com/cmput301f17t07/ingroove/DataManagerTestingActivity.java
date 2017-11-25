package com.cmput301f17t07.ingroove;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.Habit;

import java.util.ArrayList;
import android.arch.lifecycle.Observer;
/**
 * This activity is used to test the data manager.
 */
public class DataManagerTestingActivity extends AppCompatActivity {

    DataManagerAPI data = DataManager.getInstance();

    EditText topET;
    TextView resultOneTV;
    TextView resultTwoTV;
    Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_manager_testing);

        topET = (EditText) findViewById(R.id.topET);
        resultOneTV = (TextView) findViewById(R.id.resultOneTV);
        resultTwoTV = (TextView) findViewById(R.id.resultTwoTV);
        searchBtn = (Button) findViewById(R.id.saveBtn);

        /*
         *  TODO: ALL ACTIVITIES THAT QUERY THE SERVER FOR DATA MUST ADD THIS IN ONCREATE
         */

        // STEP 1 - Create the LiveData observer, specifying the exact data type to be observed
        final Observer<ArrayList<Habit>> queryObserver = new Observer<ArrayList<Habit>>() {

            // STEP 2 - Add the logic you want to trigger when the activity is notified of a change
            // to the data. NOTE, you should check to make sure that the activity is waiting
            // for query results.
            @Override
            public void onChanged(@Nullable ArrayList<Habit> result) {
                // IGNORE - this logic in particular was just for testing the LiveData was working as
                // intended, implement your own.
                Log.d("---- OBSV TEST ----","Detected data change.");

                resultOneTV.setText(result.size() + " matching habit(s).");
                resultTwoTV.setText("Matching name(s): ");
                for (Habit habit: result) {
                    String current = resultTwoTV.getText().toString();
                    resultTwoTV.setText(current + habit.getName() + ", ");
                }

            }
        };

        // STEP 3 - Point the Observer to the LiveData
        data.getFindHabitsQueryResults().observe(this, queryObserver);

        /*
         * TODO: END OF ALL STEPS FOR RETRIEVING QUERY RESULTS FROM ELASTIC SEARCH
         */

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = topET.getText().toString();

                if (!search.equals("")) {
                    data.findHabits(search);
                }
            }
        });
    }


}
