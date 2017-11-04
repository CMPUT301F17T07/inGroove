package com.example.corey.newgroove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CameraActivity extends NavigationDrawer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        this.onCreateDrawer();
    }
}
