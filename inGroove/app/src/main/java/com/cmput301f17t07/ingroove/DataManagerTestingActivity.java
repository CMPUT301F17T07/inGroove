package com.cmput301f17t07.ingroove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;

public class DataManagerTestingActivity extends AppCompatActivity {

    DataManagerAPI dataManager = DataManager.getInstance();

    EditText topET;
    EditText bottomET;

    Button saveBtn;
    Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_manager_testing);

        topET = (EditText) findViewById(R.id.topET);
        bottomET = (EditText) findViewById(R.id.bottomET);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataManager.addUser(topET.getText().toString());
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something for testing here
            }
        });

    }
}
