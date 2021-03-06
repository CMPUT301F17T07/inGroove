package com.cmput301f17t07.ingroove.navDrawer;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cmput301f17t07.ingroove.CurrentHabitsActivity;
import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.FollowOtherUsers.FollowActivity;
import com.cmput301f17t07.ingroove.MapActivities.MapOptionsActivity;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.UserActivityPackage.UserActivity;
import com.cmput301f17t07.ingroove.ViewFollowRequests.FollowRequestsActivity;
import com.cmput301f17t07.ingroove.ViewFollowersActivityPackage.ViewFollowersActivity;


/**
 * Created by corey on 2017-11-04.
 */

/**
 * [Boundary Class]
 *
 * Activity that provides the navigation drawer
 *
 * @see DataManagerAPI
 */
@SuppressLint("Registered")
public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    // Data Manager
    DataManagerAPI data = DataManager.getInstance();

    // Interface variables
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;

    protected void onCreateDrawer() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updateHeader(data.getUser().getName());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Creates a new intent based on what the user selected in the navigation drawer.
     * Can be used to quickly navigate through the app, instead using buttons or the back button.
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu_account) {
            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
            intent.putExtra(UserActivity.user_key, data.getUser());
            getApplicationContext().startActivity(intent);
        } else if (id == R.id.nav_menu_current_habits) {
            Intent intent = new Intent(getApplicationContext(), CurrentHabitsActivity.class);
            getApplicationContext().startActivity(intent);
        } else if (id == R.id.nav_menu_view_followers) {
            Intent intent = new Intent(getApplicationContext(), ViewFollowersActivity.class);
            getApplicationContext().startActivity(intent);
        } else if (id == R.id.nav_menu_find_people) {
            Intent intent = new Intent(getApplicationContext(), FollowActivity.class);
            getApplicationContext().startActivity(intent);
        } else if (id == R.id.nav_menu_follow_requests) {
            Intent intent = new Intent(getApplicationContext(), FollowRequestsActivity.class);
            getApplicationContext().startActivity(intent);
        } else if (id == R.id.nav_menu_map) {
            Intent intent = new Intent(getApplicationContext(), MapOptionsActivity.class);
            getApplicationContext().startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateHeader(String name){
        // @TODO update the user image as well
        View header = navigationView.getHeaderView(0);

        TextView textView = (TextView) header.findViewById(R.id.nav_header_name);
        textView.setText(name);
    }
}
