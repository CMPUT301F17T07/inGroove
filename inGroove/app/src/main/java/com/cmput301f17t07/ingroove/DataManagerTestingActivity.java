package com.cmput301f17t07.ingroove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.HabitManager;
import com.cmput301f17t07.ingroove.Model.Habit;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * This acitivity is used to test the data manager.
 */
public class DataManagerTestingActivity extends AppCompatActivity implements Observer{

    DataManagerAPI data = DataManager.getInstance();
    HabitManager obsv = HabitManager.getInstance();

    EditText topET;
    TextView resultOneTV;
    TextView resultTwoET;
    Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_manager_testing);

        topET = (EditText) findViewById(R.id.topET);

        resultTwoET = (TextView) findViewById(R.id.resultOneTV);
        resultTwoET = (TextView) findViewById(R.id.resultTwoTV);
        searchBtn = (Button) findViewById(R.id.saveBtn);

        obsv.addObserver(this);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = topET.getText().toString();

                if (!search.equals("") && search != null) {
                    data.findHabits(search);
                }
            }
        });
    }


    @Override
    public void update(Observable observable, Object o) {
        // GRAB THE QUERIED DATA FROM THE APPROPRIATE MANAGER
        Log.d("---- OBSV TEST ----","Detected data change.");
        ArrayList<Habit> result = HabitManager.getInstance().getQueriedHabits();
        if (result.size() > 0) {
            resultTwoET.setText(result.get(0).getName() + " " + result.get(0).getComment());
        }
    }
}
