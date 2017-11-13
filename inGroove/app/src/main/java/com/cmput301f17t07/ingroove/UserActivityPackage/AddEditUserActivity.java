package com.cmput301f17t07.ingroove.UserActivityPackage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class AddEditUserActivity extends AppCompatActivity {

    DataManagerAPI data = DataManager.getInstance();

    User user = null;

    EditText userName;
    EditText userEmail;

    Button saveUser;

    String nameText;
    String emailText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_edit);

        if (user != null) {

            userName = (EditText) findViewById(R.id.userName);
            userEmail = (EditText) findViewById(R.id.userEmail);

            userName.setText(user.getName());
            userEmail.setText(user.getEmail());

            final Context context = this.getApplicationContext();
            saveUser.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Intent intent = new Intent(getApplicationContext(), AddEditUserActivity.class);
                    //intent.putExtra("user_key", user);
                    //getApplicationContext().startActivity(intent);

                    nameText = userName.getText().toString();
                    emailText = userEmail.getText().toString();

                    Intent returnIntent = new Intent(context, UserActivity.class);
                    returnIntent.putExtra(UserActivity.user_key, user);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            });

        }

    }

}
