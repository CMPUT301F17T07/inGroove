package com.cmput301f17t07.ingroove.UserActivityPackage;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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

/**
 *  Displays the otherUser's profile.
 *
 *  @see EditUserActivity
 *  @see User
 */
public class ViewOtherUserActivity extends NavigationDrawerActivity {
    /* IMPORTANT
    This activity REQUIRES a valid serialized otherUser object be sent via intent
    to it. Otherwise it will simply exit
     */
    MockDataManager mData = MockDataManager.getInstance();
    DataManagerAPI data = DataManager.getInstance();

    // Account data to display
    public static String user_key = "USR_ACNT";
    public static String return_user_key = "USR_ACNT_EDITED";
    User otherUser;

    // List of people the otherUser follows.
    ArrayList<Habit> HabitList;

    // Layout items
    ImageView user_picture;
    TextView name;
    TextView username;
    TextView streak_txt;
    TextView start_date_txt;
    ListView Habits_list;
    ImageButton edit_button;
    Button unfollow_button;

    /**
     * Starts otherUser activity and displays the users information with the option to edit.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Get the otherUser to display
        otherUser = data.getPassedUser();

        // make sure that the otherUser is valid, else do activity
        if (otherUser == null){
            // We don't have a otherUser to display, just go back to the prior activity
            Log.w("Warning/User:", "Issue with initializing the otherUser.");
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
            edit_button = (ImageButton) findViewById(R.id.editUserButton);
            unfollow_button = (Button) findViewById(R.id.unfollow_user_button);

            // hide the edit otherUser button
            edit_button.setVisibility(View.INVISIBLE);

            // check to see if the unfollow button should be displayed or not
            data.getWhoThisUserFollows(otherUser, new AsyncResultHandler() {
                @Override
                public void handleResult(ArrayList result) {
                    result = (ArrayList<User>) result;
                    if (result.contains(data.getUser())){
                        unfollow_button.setVisibility(View.INVISIBLE);
                    }
                }
            });

            // Load the layout with the otherUser's data
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher_round);
            user_picture.setImageDrawable(drawable);

            // Load the ListView with the habits of the passed in otherUser
            // HabitList = mData.getHabit(otherUser);
            HabitList = data.getHabits();
            LoadListView(HabitList);

            name.setText(otherUser.getName());
            // THIS IS NOT THE USERNAME, the otherUser object does not have a username field yet
            // but for now we just put the email so it has something slightly different
            username.setText(otherUser.getEmail());
            streak_txt.setText("You've had " + Integer.valueOf(otherUser.getStreak()) + " perfect days!");
            start_date_txt.setText("You've been getting in groove since " + otherUser.getJoinDate().toString());

            // deal with click on habits list
            Habits_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    HabiListOnClick(position, v);
                }
            });

            // deal with click on unfollow button
            unfollow_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.unFollow(otherUser);
                    unfollow_button.setVisibility(View.INVISIBLE);
                }
            });

            super.onCreateDrawer();
        }
    }

    /**
     * Refreshes otherUser view.
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
            otherUser = this.data.getPassedUser();;

            // update view
            name.setText(otherUser.getName());
            username.setText(otherUser.getEmail());

            // update navigation menu
            super.updateHeader(otherUser.getName());
        }
    }

    /**
     * This method creates the elements that will populate a listview.
     * @param List: The list that will populate the listview
     */
    private void LoadListView(ArrayList<Habit> List){
        if(List == null || List.size() == 0) {
            Habits_list.setAdapter(null);
            return;
        }
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
