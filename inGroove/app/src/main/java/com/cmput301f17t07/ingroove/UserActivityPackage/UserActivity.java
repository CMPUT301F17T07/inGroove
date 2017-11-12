package com.cmput301f17t07.ingroove.UserActivityPackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.DataManagers.MockDataManager;
import com.cmput301f17t07.ingroove.HabitStats.HabitStatsActivity;
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;
import com.cmput301f17t07.ingroove.avehabit.EditHabitActivity;
import com.cmput301f17t07.ingroove.avehabit.ViewHabitActivity;
import com.cmput301f17t07.ingroove.navDrawer.NavigationDrawerActivity;

import org.w3c.dom.Text;

public class UserActivity extends NavigationDrawerActivity {
    /* IMPORTANT
    This activity REQUIRES a valid serialized user object be sent via intent
    to it. Otherwise it will simply exit
     */

    DataManagerAPI data = DataManager.getInstance();

    // Account data to display
    public static String user_key = "USR_ACNT";
    User user;

    // Layout items
    ImageView user_picture;
    TextView name;
    TextView username;
    TextView streak_txt;
    TextView start_date_txt;
    ListView friends_list;
    ImageButton edit_user_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Get the user to display
        //Bundle bundle = this.getIntent().getExtras();
        //user = (User) bundle.getSerializable(user_key);
        //for testing
        user = new User("test");


        if (user == null){
            // We don't have a user to display, just go back to the prior activity
            finish();
        } else {

            Log.w("TEST TEST TEST", user.toString());

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

            final Context context = this.getApplicationContext();
            edit_user_button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AddEditUserActivity.class);
                    intent.putExtra("user_key", user);
                    getApplicationContext().startActivity(intent);

                    //Intent returnIntent = new Intent(context, UserActivity.class);
                    //returnIntent.putExtra(UserActivity.user_key, user);
                    //setResult(RESULT_OK, returnIntent);
                }
            });

            super.onCreateDrawer();
        }
    }
}
