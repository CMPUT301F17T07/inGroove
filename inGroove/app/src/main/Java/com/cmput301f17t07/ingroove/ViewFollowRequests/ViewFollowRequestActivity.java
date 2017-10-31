package com.cmput301f17t07.ingroove.ViewFollowRequests;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cmput301f17t07.ingroove.R;

/**
 * Created by Ashley on 2017-10-27.
 */

public class ViewFollowRequestActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_activity_follow_requests);
    }
}
