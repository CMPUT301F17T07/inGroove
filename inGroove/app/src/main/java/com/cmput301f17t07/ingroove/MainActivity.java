package com.cmput301f17t07.ingroove;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getApplicationContext(), CurrentHabitsActivity.class);
        getApplicationContext().startActivity(intent);







    }

}
