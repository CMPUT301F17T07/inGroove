package com.cmput301f17t07.ingroove.UserActivityPackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.MockDataManager;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.Model.Habit;
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.navDrawer.NavigationDrawerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *  Displays the user's profile.
 *
 *  @see EditUserActivity
 *  @see User
 */
public class ViewOtherUserActivity extends NavigationDrawerActivity {
    /* IMPORTANT
    This activity REQUIRES a valid serialized user object be sent via intent
    to it. Otherwise it will simply exit
     */
    //MockDataManager mData = MockDataManager.getInstance();
    DataManagerAPI data = DataManager.getInstance();

    // Account data to display
    public static String user_key = "USR_ACNT";
    public static String return_user_key = "USR_ACNT_EDITED";
    User user;

    // List of people the user follows.
    ArrayList<Habit> HabitList;

    // Layout items
    ImageView user_picture;
    TextView name;
    TextView username;
    TextView streak_txt;
    TextView start_date_txt;
    ListView Habits_list;

    /**
     * Starts user activity and displays the users information with the option to edit.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Get the user to display
        user = data.getPassedUser();

        // make sure that the user is valid, else do activity
        if (user == null){
            // We don't have a user to display, just go back to the prior activity
            Log.w("Warning/User:", "Issue with initializing the user.");
            finish();
            //data.addUser("test");
        } else {

            // Setup layout vars
            user_picture = (ImageView) findViewById(R.id.usr_act_picture);
            name = (TextView) findViewById(R.id.usr_act_real_name);
            username = (TextView) findViewById(R.id.usr_act_username);
            streak_txt = (TextView) findViewById(R.id.usr_act_streak_txt);
            start_date_txt = (TextView) findViewById(R.id.usr_act_start_date);
            Habits_list = (ListView) findViewById(R.id.usr_act_friends);

            // Load the layout with the user's data
            Drawable drawable = getResources().getDrawable(R.drawable.austin);
            user_picture.setImageDrawable(drawable);

            // Load the ListView with the habits of the passed in user
            HabitList = data.getHabit(user);
            LoadListView(HabitList);

            name.setText(user.getName());
            // @TODO THIS IS NOT THE USERNAME, the user object does not have a username field yet
            // but for now we just put the email so it has something slightly different
            username.setText(user.getEmail());
            streak_txt.setText("You've had " + Integer.valueOf(user.getStreak()) + " perfect days!");
            start_date_txt.setText("You've been getting in groove since " + user.getJoinDate().toString());


            Habits_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    HabiListOnClick(position, v);
                }
            });

            super.onCreateDrawer();
        }
    }

    /**
     * Refreshes user view.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        // make sure return was good
        if (requestCode == 1 && resultCode == RESULT_OK){

            // get returned info
            user = this.data.getPassedUser();;

            // update view
            name.setText(user.getName());
            username.setText(user.getEmail());

            // update navigation menu
            super.updateHeader(user.getName());
        }
    }

    /**
     * This method creates the elements that will populate a listview.
     * @param List: The list that will populate the listview
     */
    private void LoadListView(ArrayList<Habit> List){
        if(List == null || List.size() == 0)
            return;

        ArrayList<String> gv_GridItems = new ArrayList<String>();
        for (int i = 0; i < List.size(); i++) {
            gv_GridItems.add(List.get(i).getName());
        }
        ArrayAdapter<String> gridViewArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, gv_GridItems);

        Habits_list.setAdapter(gridViewArrayAdapter);
    }
    
    private void HabiListOnClick(int position, View v)
    {
        //Nothing here for now.  Maybe one day...
    }

}
