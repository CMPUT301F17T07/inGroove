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
public class DataManagerTestingActivity extends AppCompatActivity implements AsyncResultHandler<User> {

    DataManagerAPI data = DataManager.getInstance();

    EditText topET;
    EditText addET;
    TextView resultOneTV;
    TextView resultTwoTV;
    Button searchBtn;
    Button addBtn;

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

        data.findUsers(this, "test", false, 0);
    }


    @Override
    public void handleResult(ArrayList<User> result) {

        if (result.size() > 0) {
            resultOneTV.setText(result.get(0).getName());
        }

        if (result.size() > 1) {
            resultTwoTV.setText(result.get(1).getName());
        }
    }
}
