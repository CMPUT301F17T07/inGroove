package com.cmput301f17t07.ingroove.UserActivityPackage;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.QueryTasks.AsyncResultHandler;
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.navDrawer.NavigationDrawerActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * [Boundary Class]
 *
 *  Activity displays the otherUser's profile.
 *
 *  @see EditUserActivity
 *  @see User
 *  @see DataManagerAPI
 */
public class UserActivity extends NavigationDrawerActivity  {
    /* IMPORTANT
    This activity REQUIRES a valid serialized otherUser object be sent via intent
    to it. Otherwise it will simply exit
     */
    DataManagerAPI data = DataManager.getInstance();

    // Account data to display
    public static String user_key = "USR_ACNT";
    public static String return_user_key = "USR_ACNT_EDITED";
    User user;

    // List of people the otherUser follows.
    ArrayList<User> FollowsList;
    SimpleAdapter adapter;

    // Layout items
    ImageView user_picture;
    TextView name;
    TextView email;
    TextView streak_txt;
    TextView max_streak_txt;
    TextView start_date_txt;
    TextView follow_list_text;
    ListView friends_list;
    ImageButton edit_user_button;
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
        user = data.getUser();

        // make sure that the otherUser is valid, else do activity
        if (user == null){
            // We don't have a otherUser to display, just go back to the prior activity
            Log.w("Warning/User:", "Issue with initializing the otherUser.");
            finish();
        } else {

            // Setup layout vars
            user_picture = (ImageView) findViewById(R.id.usr_act_picture);
            name = (TextView) findViewById(R.id.usr_act_real_name);
            email = (TextView) findViewById(R.id.usr_act_username);
            streak_txt = (TextView) findViewById(R.id.usr_act_streak_txt);
            max_streak_txt = (TextView) findViewById(R.id.usr_act_max_streak_txt);
            start_date_txt = (TextView) findViewById(R.id.usr_act_start_date);
            follow_list_text = (TextView) findViewById(R.id.usr_act_follow_txt);
            friends_list = (ListView) findViewById(R.id.usr_act_friends);
            edit_user_button = (ImageButton) findViewById(R.id.editUserButton);
            unfollow_button = (Button) findViewById(R.id.unfollow_user_button);

            // make the unfollow button invisable to otherUser for their own page
            unfollow_button.setVisibility(View.INVISIBLE);


            // Load the layout with the otherUser's data
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher_round);
            user_picture.setImageDrawable(drawable);

            // Load the ListView with the people who follow the user
            //FollowsList = mData.getWhoThisUserFollows(otherUser);
            data.getWhoFollows(user, new AsyncResultHandler<User>() {
                 @Override
                 public void handleResult(ArrayList<User> result) {
                     FollowsList = result;
                     LoadListView(FollowsList);
                 }
             });

            name.setText(user.getName());
            // but for now we just put the email so it has something slightly different
            email.setText("Email: " + user.getEmail());
            streak_txt.setText("You have a streak that is " + Integer.valueOf(user.getStreak()) + " day(s) long!");
            max_streak_txt.setText("Your max streak was " + Integer.valueOf(user.getMax_streak()) + " day(s) long!");

            SimpleDateFormat s_date_format = new SimpleDateFormat("dd MMM yyyy");
            start_date_txt.setText("You've been getting in groove since " + s_date_format.format(user.getJoinDate()));

            follow_list_text.setText("Here is a list of people that are currently following you!");

            // for when the edit button is clicked
            edit_user_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent upcomingIntent = new Intent(v.getContext(), EditUserActivity.class);
                    data.setPassedUser(user);
                    UserActivity.super.startActivityForResult(upcomingIntent, 1);
                }
            });

            // This gives the user the power to see the profile of users they have accepted follow
            // requests from, however this is not a requirement and we do not know if we should
            // allow it. @TODO determine if we should allow it

            friends_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    FollowerListOnClick(position, v);
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
            user = this.data.getPassedUser();

            // update view
            name.setText(user.getName());
            email.setText(user.getEmail());

            // update navigation menu
            super.updateHeader(user.getName());



            //Update followings list
            this.data.getWhoFollows(user, new AsyncResultHandler<User>() {
                @Override
                public void handleResult(ArrayList<User> result) {
                    FollowsList = result;
                    LoadListView(FollowsList);
                }
            });


        }
    }

    /**
     * This method creates the elements that will populate a listview.
     * @param List: The list that will populate the listview
     */
    private void LoadListView(ArrayList<User> List){
        if(List == null || List.size() == 0) {
            friends_list.setAdapter(null);
            return;
        }

        java.util.List<Map<String, String>> ListData = new ArrayList<Map<String, String>>();

        for (User l : List)
        {
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("title", l.getName());
            datum.put("date", "Longest Streak: " + l.getStreak());
            ListData.add(datum);
        }

        adapter = new SimpleAdapter(this, ListData,
                android.R.layout.simple_list_item_2,
                new String[] {"title", "date"},
                new int[] {android.R.id.text1,
                        android.R.id.text2});

        fillListView(adapter);
    }

    /**
     * This method populates a specific listview with a passed adapter.
     * @param adapter: An adapter with the elements that will populate the list.
     */
    private void fillListView(SimpleAdapter adapter)
    {
        friends_list.setAdapter(adapter);
    }

    private void FollowerListOnClick(int position, View v)
    {
        data.setPassedUser(FollowsList.get(position));
        Intent upcomingIntent = new Intent(v.getContext(), ViewOtherUserActivity.class);
        upcomingIntent.putExtra("isFollowing", false);
        startActivityForResult(upcomingIntent, 0);

    }

}
