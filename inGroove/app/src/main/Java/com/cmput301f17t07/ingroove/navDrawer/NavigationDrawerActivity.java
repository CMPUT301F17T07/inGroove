package com.cmput301f17t07.ingroove.navDrawer;


import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cmput301f17t07.ingroove.CurrentHabitsActivity;
import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.MockDataManager;
import com.cmput301f17t07.ingroove.FollowOtherUsers.FollowActivity;
import com.cmput301f17t07.ingroove.MapsActivity;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.UserActivityPackage.UserActivity;
import com.cmput301f17t07.ingroove.ViewFollowRequests.FollowRequestsActivity;
import com.cmput301f17t07.ingroove.ViewFollowersActivityPackage.ViewFollowersActivity;
import com.cmput301f17t07.ingroove.ViewMapActivity;


/**
 * Created by corey on 2017-11-04.
 */

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    //TODO: change to real DataManager
    DataManagerAPI data = new MockDataManager().getInstance();


    protected void onCreateDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            getApplicationContext().startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
