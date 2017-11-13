package com.cmput301f17t07.ingroove.UserActivityPackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.navDrawer.NavigationDrawerActivity;

/**
 *  Displays the user's profile.
 *
 *  @see EditUserActivity
 *  @see User
 */
public class UserActivity extends NavigationDrawerActivity {
    /* IMPORTANT
    This activity REQUIRES a valid serialized user object be sent via intent
    to it. Otherwise it will simply exit
     */

    DataManagerAPI data = DataManager.getInstance();

    // Account data to display
    public static String user_key = "USR_ACNT";
    public static String return_user_key = "USR_ACNT_EDITED";
    User user;

    // Layout items
    ImageView user_picture;
    TextView name;
    TextView username;
    TextView streak_txt;
    TextView start_date_txt;
    ListView friends_list;
    ImageButton edit_user_button;

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
        user = data.getUser();

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
            friends_list = (ListView) findViewById(R.id.usr_act_friends);
            edit_user_button = (ImageButton) findViewById(R.id.editUserButton);

            // Load the layout with the user's data
            Drawable drawable = getResources().getDrawable(R.drawable.austin);
            user_picture.setImageDrawable(drawable);

            name.setText(user.getName());
            // @TODO THIS IS NOT THE USERNAME, the user object does not have a username field yet
            // but for now we just put the email so it has something slightly different
            username.setText(user.getEmail());
            streak_txt.setText("You've had " + Integer.valueOf(user.getStreak()) + " perfect days!");
            start_date_txt.setText("You've been getting in groove since " + user.getJoinDate().toString());

            // for when the edit button is clicked
            edit_user_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent upcomingIntent = new Intent(v.getContext(), EditUserActivity.class);
                    upcomingIntent.putExtra(EditUserActivity.user_key, user);
                    UserActivity.super.startActivityForResult(upcomingIntent, 1);
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
            User changedUser = (User) data.getSerializableExtra(return_user_key);
            user = changedUser;

            // update view
            name.setText(user.getName());
            username.setText(user.getEmail());
        }
    }
}
