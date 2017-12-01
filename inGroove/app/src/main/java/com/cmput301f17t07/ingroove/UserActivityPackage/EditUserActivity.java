package com.cmput301f17t07.ingroove.UserActivityPackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;

/**
 * Allows the user to edit their profile.
 *
 * @see User
 * @see UserActivity
 *
 * Created by Ashley on 2017-11-11.
 */
public class EditUserActivity extends AppCompatActivity {

    DataManagerAPI data = DataManager.getInstance();

    // account related things
    User user;
    public static String user_key = "USR_ACNT";

    String nameText;
    String emailText;

    // elements on the page
    EditText userName;
    EditText userEmail;
    Button saveUser;
    Button goBack;
    //ImageButton editUserImage;
    ImageView userImage;

    /**
     * Allows the user to change their info or return to the user activity page
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        user = data.getUser();

        if (user != null) {

            // find the needed elements
            userName = (EditText) findViewById(R.id.userName);
            userEmail = (EditText) findViewById(R.id.userEmail);
            saveUser = (Button) findViewById(R.id.saveUser);
            goBack = (Button) findViewById(R.id.editUserBackButton);
            // editUserImage = (ImageButton) findViewById(R.id.editImageButton);
            userImage = (ImageView) findViewById(R.id.userImageView);

            // fill them in with the current info for user
            userName.setText(user.getName());
            userEmail.setText(user.getEmail());
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher_round);
            userImage.setImageDrawable(drawable);

            final Context context = this.getApplicationContext();

            // if save button clicked then save and return back to UserActivity
            saveUser.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // grab the text view elements
                    nameText = userName.getText().toString();
                    emailText = userEmail.getText().toString();

                    // update the user
                    user.setName(nameText);
                    user.setEmail(emailText);
                    data.editUser(user);

                    // return back to UserActivity
                    Intent returnIntent = new Intent(context, UserActivity.class);
                    data.setPassedUser(user);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            });

            // back button is pressed, do not save changes
            goBack.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // don't need to save anything, just return user as is

                    // return back to UserActivity
                    Intent returnIntent = new Intent(context, UserActivity.class);
                    setResult(RESULT_CANCELED, returnIntent);
                    finish();
                }
            });

            // change the image for the user
            // editUserImage.setOnClickListener(new View.OnClickListener() {
            //    public void onClick(View v) {
            //
            //    }
            // });

        }

    }

}
