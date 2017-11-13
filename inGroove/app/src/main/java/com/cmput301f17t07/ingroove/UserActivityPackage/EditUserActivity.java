package com.cmput301f17t07.ingroove.UserActivityPackage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cmput301f17t07.ingroove.DataManagers.Command.DataManagerAPI;
import com.cmput301f17t07.ingroove.DataManagers.DataManager;
import com.cmput301f17t07.ingroove.Model.User;
import com.cmput301f17t07.ingroove.R;

/**
 * Created by Ashley on 2017-11-12.
 */

public class EditUserActivity extends AppCompatActivity {

    DataManagerAPI data = DataManager.getInstance();

    public static String user_key = "USR_ACNT";
    String nameText;
    String emailText;

    User user = null;

    EditText userName;
    EditText userEmail;

    Button saveUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        user = data.getUser();

        if (user != null) {

            userName = (EditText) findViewById(R.id.userName);
            userEmail = (EditText) findViewById(R.id.userEmail);
            saveUser = (Button) findViewById(R.id.saveUser);

            userName.setText(user.getName());
            userEmail.setText(user.getEmail());

            final Context context = this.getApplicationContext();
            saveUser.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    nameText = userName.getText().toString();
                    emailText = userEmail.getText().toString();

                    user.setName(nameText);
                    user.setEmail(emailText);

                    Log.w("TEST TEST TEST", user.getName() + "|" + user.getEmail());

                    data.editUser(user);

                    Intent returnIntent = new Intent(context, UserActivity.class);
                    returnIntent.putExtra(UserActivity.user_key, user);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            });

        }

    }

}
