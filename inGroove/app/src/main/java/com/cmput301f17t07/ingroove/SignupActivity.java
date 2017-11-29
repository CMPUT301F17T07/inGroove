package com.cmput301f17t07.ingroove;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.User;

public class SignupActivity extends AppCompatActivity {

    Button save_btn;
    EditText name;
    EditText email;
    ImageView welcome_image;

    User user;
    DataManagerAPI data = DataManager.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        user = data.getUser();

        save_btn = (Button) findViewById(R.id.signup_saveUser);
        name = (EditText) findViewById(R.id.signup_userName);
        email = (EditText) findViewById(R.id.signup_userEmail);
        welcome_image = (ImageView) findViewById(R.id.signup_welcomeImage);

        // Load the layout with the user's data
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher_round);
        welcome_image.setImageDrawable(drawable);

        save_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                user.setName(name.getText().toString());
                user.setEmail(email.getText().toString());
                data.editUser(user);

                Intent intent = new Intent(getApplicationContext(), CurrentHabitsActivity.class);
                getApplicationContext().startActivity(intent);
                finish();
            }
        });
    }
}
